package samuragi.enou.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import samuragi.enou._enoucore.annotation.EncodeUserPwd;
import samuragi.enou._enoucore.exception.account.AccountExistedException;
import samuragi.enou.config.property.RedisProperty;
import samuragi.enou.config.property.TokenProperty;
import samuragi.enou.dto.dtodb.DtoDbUser;
import samuragi.enou.dto.dtoweb.DtoWebUser;
import samuragi.enou.misc.TokenManager;
import samuragi.enou.repository.UserRepository;
import samuragi.enou._enoucore.aspect.PasswordEncodeAspect;
import javax.security.auth.login.AccountException;
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
    public DtoWebUser saveUser(DtoWebUser webUser) throws AccountException {
        DtoDbUser dbUser = webUser.toDtoDb();
        if(userRepository.existsByAccount(webUser.getAccount()))
            throw new AccountExistedException(webUser.getAccount());

        DtoDbUser savedUser = userRepository.save(dbUser);
        return savedUser.toDtoWeb();
    }

    @EncodeUserPwd
    @Override
    public boolean loginInfoCorrect(DtoWebUser webUser) {

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
    public String generateAndAddToken(DtoWebUser webUser) {
        Set<String> set = jedis.zrangeByScore(tokenProperty.getRedisKey(), webUser.getId(), webUser.getId());

        if(set.size() > 5) {
            String[] tokenArray = set.toArray(new String[]{});
            return tokenArray[0];
        }


        String token = tokenGenerator.generateToken(webUser.getId(), 24 * 7);
        jedis.zadd(tokenProperty.getRedisKey(), webUser.getId(), token);

        return token;
    }


}