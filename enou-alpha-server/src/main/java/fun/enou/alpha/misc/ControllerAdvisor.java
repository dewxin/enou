package fun.enou.alpha.misc;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;

import javax.security.auth.login.AccountException;
import javax.servlet.ServletException;

/**
 * @Author: nagi
 * @Modified By:
 * @Date Created in 2020-09-24 22:05
 * @Description:
 * @Attention:
 */
//todo https://cn.bing.com/search?q=ControllerAdvice&PC=U316&FORM=CHROMN
@ControllerAdvice
@Slf4j
public class ControllerAdvisor {

	@Autowired
	private SessionHolder sessionHolder;
//    @ExceptionHandler()
//    public

    @ResponseBody
    @ExceptionHandler(value = AccountException.class)
    public ResponseEntity<String> AccountExceptionHandler(AccountException ex) {
//        HttpHeaders responseHeaders = new HttpHeaders();
//        responseHeaders.set("Enou-Result-Code",
//                "Client-Authorization-Error");
//        responseHeaders.set("Access-Control-Expose-Headers" ,//axios跨域请求需要设置 额外的header
//                "Enou-Result-Code");


        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                .headers(responseHeaders)
                .body(ex.getMessage());
    }
    
    @ResponseBody
    @ExceptionHandler(value = ServletException.class)
    public ResponseEntity<String> ServletExceptionHandler(ServletException ex) {
//        HttpHeaders responseHeaders = new HttpHeaders();
//        responseHeaders.set("Enou-Result-Code",
//                "Client-Authorization-Error");
//        responseHeaders.set("Access-Control-Expose-Headers" ,//axios跨域请求需要设置 额外的header
//                "Enou-Result-Code");

    	log.warn("ip {} useId {} cause {}", sessionHolder.getRemoteAddress(), sessionHolder.getUserId(), ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                .headers(responseHeaders)
                .body("some thing went wrong");
    }
    
    


}
