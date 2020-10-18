package fun.enou.alpha.misc;

import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fun.enou.alpha.config.property.RedisProperty;
import redis.clients.jedis.Jedis;

/**
 * @Author: nagi
 * @Modified By:
 * @Date Created in 2020-09-22 19:50
 * @Description:
 * @Attention:
 */
@Component
public class SessionHolder {
	
	ThreadLocal<Jedis> jedisLocal;

    ThreadLocal<Long> userIdLocal = new ThreadLocal<>();
    
    ThreadLocal<String> userTokenLocal = new ThreadLocal<>();
    
    ThreadLocal<String> remoteAddressLocal = new ThreadLocal<>();

    @Autowired
    public SessionHolder(RedisProperty redisProperty) {
    	jedisLocal = ThreadLocal.withInitial(new Supplier<Jedis>() {
    		@Override
    		public Jedis get() {
    			return new Jedis(redisProperty.getHost());
    		}
    	});
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

	public Jedis getJedisLocal() {
		return jedisLocal.get();
	}
    
}
