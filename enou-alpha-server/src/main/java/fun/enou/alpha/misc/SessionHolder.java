package fun.enou.alpha.misc;

import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fun.enou.alpha.config.property.RedisProperty;
import lombok.extern.slf4j.Slf4j;
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
	
    ThreadLocal<Long> userIdLocal = new ThreadLocal<>();
    
    ThreadLocal<String> remoteAddressLocal = new ThreadLocal<>();

    public SessionHolder() {
    }

    public Long getUserId() {
        return userIdLocal.get();
    }

    public void setUserId(Long userIdLocal) {
        this.userIdLocal.set(userIdLocal);
    }

	
	public String getRemoteAddress() {
		return remoteAddressLocal.get();
	}

	public void setRemoteAddress(String userTokenLocal) {
		this.remoteAddressLocal.set(userTokenLocal);
	}
    
}
