package fun.enou.alpha.service;

import fun.enou.alpha.config.property.TokenProperty;
import fun.enou.alpha.dto.dtodb.DtoDbUser;
import fun.enou.alpha.dto.dtoweb.DtoWebUser;
import fun.enou.alpha.misc.SessionHolder;
import fun.enou.alpha.misc.TokenManager;
import fun.enou.alpha.msg.MsgEnum;
import fun.enou.alpha.repository.UserRepository;
import fun.enou.core.encoder.EncodeUserPwd;
import fun.enou.core.encoder.EncodeUserPwdAspect;
import fun.enou.core.msg.EnouMessageException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.Set;


/**
 * @Author: nagi
 * @Modified By:
 * @Date Created in 2020-09-20 17:29
 * @Description:
 *
 * @Attention:  function with parameter named webUser will be strengthened by UserServiceAspect, the password will be encoded before entering the function
 * @see EncodeUserPwdAspect
 */
@Service
public class TUserService implements IUserService{

	@Autowired
	private SessionHolder sessionHolder;
    @Autowired
    private TokenProperty tokenProperty;
    @Autowired
    private TokenManager tokenGenerator;
    @Autowired
    private UserRepository userRepository;


    public TUserService() {
    }

    @EncodeUserPwd
    @Override
    public DtoWebUser saveUser(DtoWebUser webUser) throws EnouMessageException {
        DtoDbUser dbUser = webUser.toDtoDb();
        if(userRepository.existsByAccount(webUser.getAccount()))
        	throw MsgEnum.ACCOUNT_EXIST.Exception();

        DtoDbUser savedUser = userRepository.save(dbUser);
        return savedUser.toDtoWeb();
    }

    @EncodeUserPwd
    @Override
    public boolean loginInfoIsCorrect(DtoWebUser webUser) {

        String account = webUser.getAccount();
        String password = webUser.getPassword();

        return userRepository.existsByAccountAndPassword(account, password);
    }

    @EncodeUserPwd
    @Override
    public DtoWebUser findByAccountAndPassword(DtoWebUser webUser) {

        String account = webUser.getAccount();
        String password = webUser.getPassword();
        Optional<DtoDbUser> optionalDtoDbUser = userRepository.findByAccountAndPassword(account, password);

        if(optionalDtoDbUser.isPresent()) {
            return optionalDtoDbUser.get().toDtoWeb();
        }
        return null;
    }

    @Override
    public String loginGetToken(DtoWebUser webUser) {
        Set<String> set = sessionHolder.getJedisLocal().zrangeByScore(tokenProperty.getRedisKey(), webUser.getId(), webUser.getId());

        if(set.size() >= 5) {
            String[] tokenArray = set.toArray(new String[]{});
            return tokenArray[0];
        }

        String token = tokenGenerator.generateToken(webUser.getId(), 24 * 7);
        sessionHolder.getJedisLocal().zadd(tokenProperty.getRedisKey(), webUser.getId(), token);

        return token;
    }

	@Override
	public void logout(Long userId, String userToken) {
		sessionHolder.getJedisLocal().zrem(tokenProperty.getRedisKey(), userToken);
	}


}
