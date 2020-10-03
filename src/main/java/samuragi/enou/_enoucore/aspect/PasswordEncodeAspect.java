package samuragi.enou._enoucore.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import samuragi.enou._enoucore.IHasPassword;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Aspect
@Component
public class PasswordEncodeAspect {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(PasswordEncodeAspect.class);

    @Autowired
    PasswordEncoder passwordEncoder;

    @Around(value = "execution(* *.*(..)) && @annotation(samuragi.enou._enoucore.annotation.EncodeUserPwd)")
    public Object encodePassword(ProceedingJoinPoint joinPoint) throws Throwable {

        List oldArgsList = Arrays.asList(joinPoint.getArgs());
        List newArgList = new ArrayList();
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
