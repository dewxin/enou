package samuragi.enou.misc;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.security.auth.login.AccountException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.ResponseWrapper;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: nagi
 * @Modified By:
 * @Date Created in 2020-09-24 22:05
 * @Description:
 * @Attention:
 */
//todo https://cn.bing.com/search?q=ControllerAdvice&PC=U316&FORM=CHROMN
@ControllerAdvice
public class ControllerAdvisor {

//    @ExceptionHandler()
//    public

    @ResponseBody
    @ExceptionHandler(value = AccountException.class)
    public ResponseEntity<String> AccountExceptionHandler(AccountException ex) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Enou-Result-Code",
                "Client-Authorization-Error");
        responseHeaders.set("Access-Control-Expose-Headers" ,//axios跨域请求需要设置 额外的header
                "Enou-Result-Code");


        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .headers(responseHeaders)
                .body(ex.getMessage());
    }


}
