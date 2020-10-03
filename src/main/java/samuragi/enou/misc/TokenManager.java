package samuragi.enou.misc;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import samuragi.enou._enoucore.TimeUtil;
import samuragi.enou.config.property.TokenProperty;

import java.util.Date;

/**
 * @Author: nagi
 * @Modified By:
 * @Date Created in 2020-09-20 21:57
 * @Description:
 * @Attention:
 */

@DependsOn("tokenProperty")
@Component
public class TokenManager {

    public final String SECRET_KEY ;
    public final String USER_ID = "userId";

    @Autowired
    public TokenManager(TokenProperty tokenProperty) {
        SECRET_KEY = tokenProperty.getSecretKey();
    }

    public String generateToken(Long userId, int durationHour) {
        Date date = new Date();

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT") // 设置header
                .setHeaderParam("alg", "HS256")
                .setExpiration(TimeUtil.dateAddHour(date, durationHour))
                .claim(USER_ID,String.valueOf(userId) ) // 设置内容
                .setIssuer("enou")// 设置签发人
                .signWith(signatureAlgorithm, SECRET_KEY);

        return builder.compact();
    }


}
