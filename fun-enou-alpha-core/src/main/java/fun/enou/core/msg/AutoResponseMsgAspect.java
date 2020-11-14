package fun.enou.core.msg;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;

@Aspect
public class AutoResponseMsgAspect {

    @Around("@within(AutoResponseMsg)")
    public ResponseEntity<?> wrapResponseMsg(ProceedingJoinPoint joinPoint) throws Throwable {

        Object result = joinPoint.proceed();
        if(result == null)
        	return ResponseEntity.ok().build();
        
        if(result instanceof ResponseEntity) {
        	ResponseEntity<?> entity = (ResponseEntity<?>) result;
        	result = entity.getBody();
        }
        
		EnouMsgJson msgJson = EnouMsgJson.createDataMsg(result);
		return ResponseEntity.ok(msgJson);
    }
}
