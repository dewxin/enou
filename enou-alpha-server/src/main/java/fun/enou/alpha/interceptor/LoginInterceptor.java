package fun.enou.alpha.interceptor;

import fun.enou.alpha.config.property.RedisProperty;
import fun.enou.alpha.config.property.TokenProperty;
import fun.enou.alpha.misc.SessionHolder;
import fun.enou.alpha.misc.TokenManager;
import fun.enou.alpha.runner.MsgEnum;
import fun.enou.core.msg.EnouMessageException;
import fun.enou.core.msg.EnouMsgManager;
import fun.enou.core.tool.IpUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Set;

@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor  {

	//todo substitute with threadlocal variable
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
    	
    	String remoteIp = IpUtil.getIpAddr(request);
    	sessionHolder.setRemoteAddress(remoteIp);
    	
    	String uri =request.getRequestURI();
    	
        final String headerToken=request.getHeader(tokenProperty.getHeaderName());
        //判断请求信息
        if(null==headerToken||headerToken.trim().equals("")){
            log.warn("no token header, remoteIp:{} uri:{} ", remoteIp, uri);
            throw EnouMsgManager.getMsg(MsgEnum.HEADER_NOT_CONTAIN_TOKEN);
        }
        //解析Token信息
        try {
            Claims claims = Jwts.parser().setSigningKey(tokenManager.SECRET_KEY).parseClaimsJws(headerToken).getBody();
            String userIdStr=(String)claims.get(tokenManager.USER_ID);
            long userId = Long.parseLong(userIdStr);

            Set<String> tokenSet = jedis.zrangeByScore(tokenProperty.getRedisKey(), userIdStr, userIdStr);

            if(!tokenSet.contains(headerToken)) {
                log.warn("cannot find token in redis, userId:{}", userId);
                throw EnouMsgManager.getMsg(MsgEnum.TOKEN_NOT_PUT_IN_REDIS);
            }

            sessionHolder.setUserIdLocal(userId);
            sessionHolder.setUserToken(headerToken);
        }
        catch (EnouMessageException e) {
            throw  e;
        }
        catch (ExpiredJwtException e) {
        	log.warn("token expired, message:{} remoteIp:{} token:{}", e.getMessage(), remoteIp, headerToken);
        	throw EnouMsgManager.getMsg(MsgEnum.TOKEN_EXPIRED);
		}
        catch (JwtException e) {
        	log.warn("parse token fail, message:{} remoteIp:{} token:{}", e.getMessage(), remoteIp, headerToken);
        	throw EnouMsgManager.getMsg(MsgEnum.PARSE_TOKEN_FAIL);
		}
        catch (Exception e) {
        	log.warn(e.getMessage());
        	log.warn(e.getStackTrace().toString());
        	throw EnouMsgManager.getMsg(MsgEnum.UNKOWN_ERROR_PARSING_TOKEN);
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
