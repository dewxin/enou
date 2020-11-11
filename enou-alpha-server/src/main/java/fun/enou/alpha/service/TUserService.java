package fun.enou.alpha.service;

import fun.enou.alpha.dto.dtodb.DtoDbUser;
import fun.enou.alpha.dto.dtodb.DtoDbUserThirdInfo;
import fun.enou.alpha.dto.dtoweb.DtoWebUser;
import fun.enou.alpha.dto.dtoweb.DtoWebUserThirdInfo;
import fun.enou.alpha.misc.SessionHolder;
import fun.enou.alpha.misc.LoginTokenManager;
import fun.enou.alpha.msg.MsgEnum;
import fun.enou.alpha.repository.UserRepository;
import fun.enou.alpha.repository.UserThirdInfoRepository;
import fun.enou.core.encoder.EncodeUserPwd;
import fun.enou.core.encoder.EncodeUserPwdAspect;
import fun.enou.core.msg.EnouMessageException;
import fun.enou.core.redis.RedisManager;
import redis.clients.jedis.Jedis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;


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
    private LoginTokenManager tokenGenerator;
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserThirdInfoRepository userThirdInfoRepository;

    @Autowired
    private RedisManager redisManager;

    public TUserService() {
    }

    @EncodeUserPwd
    @Override
    public DtoWebUser saveUser(DtoWebUser webUser) throws EnouMessageException {
        DtoDbUser dbUser = webUser.toDtoDb();
        if(userRepository.existsByAccount(webUser.getAccount()))
        	MsgEnum.ACCOUNT_EXIST.ThrowException();

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

    	String userTokenKey = redisManager.getUserTokenKey(webUser.getId());
    	int timeOutSecond = 3600 * 24 * 30;
    	
    	try (Jedis jedis = redisManager.getJedis()) {
        	String token = jedis.get(userTokenKey);
        	if(token != null) {
        		jedis.expire(userTokenKey, timeOutSecond);
        	} else {
        		token = tokenGenerator.generateToken(webUser.getId());
        		jedis.setex(userTokenKey, timeOutSecond,token);
    		}
        	
        	return token;
    	}
    }

	@Override
	public void logout() {
//		Long userId = sessionHolder.getUserId();
//		try(Jedis jedis = redisManager.getJedis()) {
//			jedis.del("token:uid:"+userId);
//		}
	}

	@Override
	public DtoWebUserThirdInfo saveUserThirdInfo(DtoWebUserThirdInfo webThirdInfo) throws EnouMessageException {
		webThirdInfo.setUserId(sessionHolder.getUserId());
		DtoDbUserThirdInfo dbUserThirdInfo = webThirdInfo.toDtoDb();
		
		userThirdInfoRepository.save(dbUserThirdInfo);
		return webThirdInfo;
		
	}
	
	@Override
	public DtoWebUserThirdInfo getUserThirdInfo(String thirdParty) {
		Long userId = sessionHolder.getUserId();
		
		DtoDbUserThirdInfo dbUserThirdInfo = new DtoDbUserThirdInfo(userId, thirdParty, null);
		dbUserThirdInfo = userThirdInfoRepository.find(dbUserThirdInfo);
		return dbUserThirdInfo.ToDtoWeb();
	}


}
