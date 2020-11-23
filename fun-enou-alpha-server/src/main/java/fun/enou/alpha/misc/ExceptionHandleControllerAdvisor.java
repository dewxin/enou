package fun.enou.alpha.misc;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.core.JsonProcessingException;

import fun.enou.core.msg.EnouMessageException;
import fun.enou.core.msg.EnouMsgJson;


/**
 * @Author: nagi
 * @Modified By:
 * @Date Created in 2020-09-24 22:05
 * @Description: controller advisor
 * @Attention:
 */
@ControllerAdvice
public class ExceptionHandleControllerAdvisor {

	@ResponseBody
	@ExceptionHandler(value = EnouMessageException.class)
	public ResponseEntity<EnouMsgJson<?>> AccountExceptionHandler(EnouMessageException ex) throws JsonProcessingException {
		
		int code = ex.getCode();
		String msg =ex.getMessage();
		
		EnouMsgJson<?> enouMsgJson = EnouMsgJson.createErrorMsg(code, msg);

		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(enouMsgJson);
	}

}
