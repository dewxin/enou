package fun.enou.alpha.config.property;

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
    //todo enable protect mode on the remote server

    private String host;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
