package fun.enou.core.encoder;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Aspect
public class EncodeUserPwdAspect {

    private PasswordEncoder passwordEncoder;
    
    public EncodeUserPwdAspect(String salt) {
    	passwordEncoder = new EnouPwdEncoder(salt);
	}

    @Around(value = "execution(* *.*(..)) && @annotation(EncodeUserPwd)")
    public Object encodePassword(ProceedingJoinPoint joinPoint) throws Throwable {

        List<Object> oldArgsList = Arrays.asList(joinPoint.getArgs());
        List<Object> newArgList = new ArrayList<Object>();
        for(Object arg : oldArgsList) {

            if(IHasPassword.class.isAssignableFrom(arg.getClass())) {
                IHasPassword iHasPasswordArg = (IHasPassword)arg;
                String encodedPwd = passwordEncoder.encode(iHasPasswordArg.getPassword());
                iHasPasswordArg.setPassword(encodedPwd);
                arg = iHasPasswordArg;
            }
            newArgList.add(arg);
        }

        Object result = joinPoint.proceed(newArgList.toArray());
        return result;
    }
}
