package fun.enou.core.redis;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: nagi
 * @Modified By:
 * @Date Created in 2020-09-21 10:48
 * @Description:
 * @Attention:
 */
@Configuration
@ConfigurationProperties(prefix = "enou.redis")
public class RedisProperty {

    private String host;
    private String authPass;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

	public String getAuthPass() {
		return authPass;
	}

	public void setAuthPass(String authPass) {
		this.authPass = authPass;
	}
    
}
