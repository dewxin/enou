package fun.enou.alpha.misc;

import fun.enou.alpha.config.property.RedisProperty;
import fun.enou.alpha.config.property.TokenProperty;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.time.LocalTime;
import java.util.Set;

/**
 * @Author: nagi
 * @Modified By:
 * @Date Created in 2020-09-23 15:49
 * @Description:
 * @Attention:
 */
@Component
@Slf4j
public class ScheduledTask {

    Jedis jedis;

    @Autowired
    TokenProperty tokenProperty;

    @Autowired
    TokenManager tokenManager;

    @Autowired
    public ScheduledTask(RedisProperty redisProperty) {
        this.jedis = new Jedis(redisProperty.getHost());
    }

    //@Scheduled(cron = "* * * * * ?")
    public void insertExpiredToken() {
        long userPerHour = 100 * 3600;
        long userPerMinute = 100 * 60;

        LocalTime now = LocalTime.now();
        log.info("enter insertExpired");

        long start = now.getHour()* userPerHour+ now.getMinute() * userPerMinute;
        long end = start + userPerMinute;

        for(long i = start; i < end; ++i) {
            String token = tokenManager.generateToken(i, 0);
            jedis.zadd(tokenProperty.getRedisKey(), i, token);
        }

    }


    // scheduling thread will block until
    @Scheduled(cron = "0 0 0 * * ?")
    public void cleanExpiredToken() {
        Set<String> tokenSet = jedis.zrangeByScore(tokenProperty.getRedisKey(), Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);

        log.info("enter cleanExpire");

        for (String token : tokenSet) {
            try{
                Jws<Claims> jws = Jwts.parser().setSigningKey(tokenProperty.getSecretKey()).parseClaimsJws(token);
            }
            catch (ExpiredJwtException e) {
                jedis.zrem(tokenProperty.getRedisKey(), token);
            }

        }

    }
}
