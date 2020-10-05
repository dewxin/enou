package samuragi.enou.interceptor;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import redis.clients.jedis.Jedis;
import samuragi.enou._enoucore.exception.account.AccountTokenException;
import samuragi.enou.config.property.RedisProperty;
import samuragi.enou.config.property.TokenProperty;
import samuragi.enou.misc.SessionHolder;
import samuragi.enou.misc.TokenManager;

import javax.security.auth.login.AccountException;
import javax.security.auth.login.AccountExpiredException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Set;

@Component
public class LoginInterceptor implements HandlerInterceptor  {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(LoginInterceptor.class);

    Jedis jedis;

    @Autowired
    TokenProperty tokenProperty;

    @Autowired
    SessionHolder sessionHolder;

    @Autowired
    TokenManager tokenManager;

    @Autowired
    public LoginInterceptor(RedisProperty redisProperty) {
        jedis = new Jedis(redisProperty.getHost());
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (request.getRequestURI().contains("/login") || request.getRequestURI().contains("/register") || request.getRequestURI().contains("/error") || request.getRequestURI().contains("/static")) {
            return true;
        }

        final String headerToken=request.getHeader(tokenProperty.getHeaderName());
        //判断请求信息
        if(null==headerToken||headerToken.trim().equals("")){
            log.info("there is no token header");
            throw new AccountTokenException("user has not log in");
        }
        //解析Token信息
        try {
            Claims claims = Jwts.parser().setSigningKey(tokenManager.SECRET_KEY).parseClaimsJws(headerToken).getBody();
            String userIdStr=(String)claims.get(tokenManager.USER_ID);
            long userId = Long.parseLong(userIdStr);

            Set<String> tokenSet = jedis.zrangeByScore(tokenProperty.getRedisKey(), userIdStr,userIdStr);

            if(!tokenSet.contains(headerToken)) {
                log.info("{} userId not put in redis", userId);
                throw new AccountTokenException("parse token fail");
            }

            sessionHolder.setUserIdLocal(userId);
        }
        catch (AccountTokenException e) {
            throw  e;
        }
        catch (Exception e) {
            log.info("id {} user parse token fail:{}", headerToken, e.getMessage());
            throw new AccountTokenException("parse token fail");
            //return false;
        }

        log.info("id {} user parse token succeed", sessionHolder.getUserId());

        return true;

    }


    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                            @Nullable ModelAndView modelAndView) throws Exception {
    }


    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                 @Nullable Exception ex) throws Exception {
    }
}
