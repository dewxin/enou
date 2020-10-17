package fun.enou.alpha.misc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties.Jedis;
import org.springframework.stereotype.Component;

import fun.enou.alpha.config.property.RedisProperty;

/**
 * @Author: nagi
 * @Modified By:
 * @Date Created in 2020-09-22 19:50
 * @Description:
 * @Attention:
 */
@Component
public class SessionHolder {
	
	@Autowired
	RedisProperty redisProperty;
	
	ThreadLocal<Jedis> jedisLocal = new ThreadLocal<>();

    ThreadLocal<Long> userIdLocal = new ThreadLocal<>();
    
    ThreadLocal<String> userTokenLocal = new ThreadLocal<>();
    
    ThreadLocal<String> remoteAddressLocal = new ThreadLocal<>();

    public SessionHolder() {
    }

    public Long getUserId() {
        return userIdLocal.get();
    }

    public void setUserIdLocal(Long userIdLocal) {
        this.userIdLocal.set(userIdLocal);
    }


	public String getUserToken() {
		return userTokenLocal.get();
	}

	public void setUserToken(String userTokenLocal) {
		this.userTokenLocal.set(userTokenLocal);
	}
	
	public String getRemoteAddress() {
		return remoteAddressLocal.get();
	}

	public void setRemoteAddress(String userTokenLocal) {
		this.remoteAddressLocal.set(userTokenLocal);
	}
    
}
