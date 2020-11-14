package fun.enou.alpha.misc;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import fun.enou.core.tool.IpUtil;

public class ServletExceptionHandler implements HandlerExceptionResolver {
 
    private static final Logger log = LoggerFactory.getLogger(ServletExceptionHandler.class);
 
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        
    	if(ex instanceof ServletException) {
    		String remoteAddress = IpUtil.getIpAddr(request);
    		log.warn("ip {} cause {}", remoteAddress, ex.getMessage());
    		ModelAndView model = new ModelAndView();
    		MappingJackson2JsonView view = new MappingJackson2JsonView();
    		model.setView(view);
    		model.setStatus(HttpStatus.BAD_REQUEST);
    		model.addObject("msg", ex.getMessage());
    		return model;
    	}
    	
        return null;
    }
}
 
