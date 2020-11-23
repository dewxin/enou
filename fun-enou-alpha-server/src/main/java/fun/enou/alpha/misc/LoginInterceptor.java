package fun.enou.alpha.misc;

import fun.enou.alpha.config.property.TokenProperty;
import fun.enou.alpha.msg.MsgEnum;
import fun.enou.core.msg.EnouMessageException;
import fun.enou.core.redis.RedisManager;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor  {

    @Autowired
    TokenProperty tokenProperty;

    @Autowired
    SessionHolder sessionHolder;

    @Autowired
    LoginTokenManager tokenManager;
    
    @Autowired
    RedisManager redisManager;

    public LoginInterceptor() {
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
    	
    	String remoteIp = IpUtil.getIpAddr(request);
    	sessionHolder.setRemoteAddress(remoteIp);
    	
    	String uri =request.getRequestURI();
    	
        final String headerToken=request.getHeader(tokenProperty.getHeaderName());
        if(null==headerToken||headerToken.trim().equals("")){
            log.warn("no token header, remoteIp:{} uri:{} ", remoteIp, uri);
            MsgEnum.HEADER_NOT_CONTAIN_TOKEN.ThrowException();
        }
        try {
            Claims claims = Jwts.parser()
            		.setSigningKey(tokenManager.SECRET_KEY).parseClaimsJws(headerToken).getBody();
            String userIdStr=(String)claims.get(tokenManager.USER_ID);
            long userId = Long.parseLong(userIdStr);

            String token = redisManager.getToken(userId);
            
            if(token == null) {
                log.warn("cannot find token in redis, userId:{}", userId);
                MsgEnum.TOKEN_NOT_PUT_IN_REDIS.ThrowException();
            }

            if(!headerToken.contains(token)){
            	log.warn("client token not match redis token");
            	MsgEnum.TOKEN_NOT_MATCH_REDIS.ThrowException();
            }

            sessionHolder.setUserId(userId);
        }
        catch (EnouMessageException e) {
            throw  e;
        }
        catch (ExpiredJwtException e) {
        	log.warn("token expired, message:{} remoteIp:{} token:{}", e.getMessage(), remoteIp, headerToken);
        	MsgEnum.TOKEN_EXPIRED.ThrowException();
		}
        catch (JwtException e) {
        	log.warn("parse token fail, message:{} remoteIp:{} token:{}", e.getMessage(), remoteIp, headerToken);
        	MsgEnum.PARSE_TOKEN_FAIL.ThrowException();
		}
        catch (Exception e) {
        	log.warn(e.getMessage());
        	log.warn(e.getStackTrace().toString());
        	MsgEnum.OTHER_ERROR_PARSING_TOKEN.ThrowException();
        }

        log.debug("id {} user parse token succeed", sessionHolder.getUserId());

        return true;

    }


    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                            @Nullable ModelAndView modelAndView) throws Exception {
    	
    }


    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                 @Nullable Exception ex) throws Exception {
    }
}
