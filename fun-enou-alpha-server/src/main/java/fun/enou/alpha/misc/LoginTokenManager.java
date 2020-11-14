package fun.enou.alpha.misc;

import fun.enou.alpha.config.property.TokenProperty;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;


/**
 * @Author: nagi
 * @Modified By:
 * @Date Created in 2020-09-20 21:57
 * @Description:
 * @Attention:
 */

@DependsOn("tokenProperty")
@Component
public class LoginTokenManager {

    public final String SECRET_KEY ;
    public final String USER_ID = "userId";

    @Autowired
    public LoginTokenManager(TokenProperty tokenProperty) {
        SECRET_KEY = tokenProperty.getSecretKey();
    }

    public String generateToken(Long userId) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        JwtBuilder builder = Jwts.builder()
                .claim(USER_ID,String.valueOf(userId) ) // 设置内容
                .signWith(signatureAlgorithm, SECRET_KEY);

        return builder.compact();
    }


}
