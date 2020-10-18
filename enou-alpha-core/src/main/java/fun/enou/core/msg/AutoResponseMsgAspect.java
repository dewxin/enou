package fun.enou.core.msg;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;

@Aspect
public class AutoResponseMsgAspect {

    @Around("@within(AutoResponseMsg)")
    public Object wrapResponseMsg(ProceedingJoinPoint joinPoint) throws Throwable {

        Object result = joinPoint.proceed();
        if(result == null)
        	return ResponseEntity.ok();
        
		EnouMsgJson msgJson = EnouMsgJson.createDataMsg(result);
		return ResponseEntity.ok(msgJson);
    }
}
