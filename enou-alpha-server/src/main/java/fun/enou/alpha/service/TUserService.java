package fun.enou.alpha.service;

import fun.enou.alpha.config.property.RedisProperty;
import fun.enou.alpha.config.property.TokenProperty;
import fun.enou.alpha.dto.dtodb.DtoDbUser;
import fun.enou.alpha.dto.dtoweb.DtoWebUser;
import fun.enou.alpha.misc.TokenManager;
import fun.enou.alpha.repository.UserRepository;
import fun.enou.alpha.runner.MsgEnum;
import fun.enou.core.encoder.EncodeUserPwd;
import fun.enou.core.encoder.PasswordEncodeAspect;
import fun.enou.core.msg.EnouMessageException;
import fun.enou.core.msg.EnouMsgManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import java.util.Optional;
import java.util.Set;


/**
 * @Author: nagi
 * @Modified By:
 * @Date Created in 2020-09-20 17:29
 * @Description:
 *
 * @Attention:  function with parameter named webUser will be strengthened by UserServiceAspect, the password will be encoded before entering the function
 * @see PasswordEncodeAspect
 */
@Service
public class TUserService implements IUserService{

    private Jedis jedis;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenProperty tokenProperty;
    @Autowired
    private TokenManager tokenGenerator;

    @Autowired
    public TUserService(RedisProperty redisProperty) {
        this.jedis = new Jedis(redisProperty.getHost());
    }

    @EncodeUserPwd
    @Override
    public DtoWebUser saveUser(DtoWebUser webUser) throws EnouMessageException {
        DtoDbUser dbUser = webUser.toDtoDb();
        if(userRepository.existsByAccount(webUser.getAccount()))
            throw EnouMsgManager.getMsg(MsgEnum.ACCOUNT_EXIST);

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
        Set<String> set = jedis.zrangeByScore(tokenProperty.getRedisKey(), webUser.getId(), webUser.getId());

        if(set.size() > 5) {
            String[] tokenArray = set.toArray(new String[]{});
            return tokenArray[0];
        }


        String token = tokenGenerator.generateToken(webUser.getId(), 24 * 7);
        jedis.zadd(tokenProperty.getRedisKey(), webUser.getId(), token);

        return token;
    }

	@Override
	public void logout(Long userId, String userToken) {
		jedis.zrem(tokenProperty.getRedisKey(), userToken);
	}


}
