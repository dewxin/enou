package fun.enou.alpha.misc;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.security.auth.login.AccountException;

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
//        HttpHeaders responseHeaders = new HttpHeaders();
//        responseHeaders.set("Enou-Result-Code",
//                "Client-Authorization-Error");
//        responseHeaders.set("Access-Control-Expose-Headers" ,//axios跨域请求需要设置 额外的header
//                "Enou-Result-Code");


        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                .headers(responseHeaders)
                .body(ex.getMessage());
    }


}
