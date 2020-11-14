package fun.enou.alpha.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: nagi
 * @Modified By:
 * @Date Created in 2020-09-21 11:19
 * @Description:
 * @Attention:
 */
@Configuration
@ConfigurationProperties("enou.token")
public class TokenProperty {

    private String headerName = "token";
    private String secretKey = "enou$Token$";


    public String getHeaderName() {
        return headerName;
    }

    public void setHeaderName(String headerName) {
        this.headerName = headerName;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }


}
