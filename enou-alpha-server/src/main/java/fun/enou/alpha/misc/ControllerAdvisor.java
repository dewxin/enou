package fun.enou.alpha.misc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import fun.enou.core.msg.EnouMessageException;
import fun.enou.core.msg.EnouMsgJson;
import lombok.extern.slf4j.Slf4j;

import javax.security.auth.login.AccountException;

/**
 * @Author: nagi
 * @Modified By:
 * @Date Created in 2020-09-24 22:05
 * @Description: controller advisor
 * @Attention:
 */
//todo https://cn.bing.com/search?q=ControllerAdvice&PC=U316&FORM=CHROMN
@ControllerAdvice
@Slf4j
public class ControllerAdvisor {

	@Autowired
	private SessionHolder sessionHolder;

	@ResponseBody
	@ExceptionHandler(value = EnouMessageException.class)
	public ResponseEntity<String> AccountExceptionHandler(EnouMessageException ex) throws JsonProcessingException {

		
		int code = ex.getCode();
		String msg =ex.getMessage();
		
		EnouMsgJson emouMsgJson = new EnouMsgJson(code, msg);
		
		ObjectMapper mapper=new ObjectMapper();
		String enouMsgJsonStr=mapper.writeValueAsString(emouMsgJson);

		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(enouMsgJsonStr);
	}

	@ResponseBody
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<String> ServletExceptionHandler(Exception ex) {
//        HttpHeaders responseHeaders = new HttpHeaders();
//        responseHeaders.set("Enou-Result-Code",
//                "Client-Authorization-Error");
//        responseHeaders.set("Access-Control-Expose-Headers" ,//axios跨域请求需要设置 额外的header
//                "Enou-Result-Code");

		log.warn("ip {} useId {} cause {}", sessionHolder.getRemoteAddress(), sessionHolder.getUserId(),
				ex.getMessage());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                .headers(responseHeaders)
				.body("some thing went wrong");
	}

}
