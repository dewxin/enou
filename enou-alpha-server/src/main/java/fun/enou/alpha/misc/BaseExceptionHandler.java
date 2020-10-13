package fun.enou.alpha.misc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import fun.enou.core.IpUtils;

@Component
public class BaseExceptionHandler implements HandlerExceptionResolver {
 
    private static final Logger log = LoggerFactory.getLogger(BaseExceptionHandler.class);
 
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        
    	String remoteAddress = IpUtils.getIpAddr(request);
    	log.warn("ip {} cause {}", remoteAddress, ex.getMessage());
    	
        return null;
    }
}
 
