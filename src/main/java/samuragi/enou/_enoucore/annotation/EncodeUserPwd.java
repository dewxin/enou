package samuragi.enou._enoucore.annotation;

import samuragi.enou._enoucore.IHasPassword;

import java.lang.annotation.*;

/**
 * @Author: nagi
 * @Modified By:
 * @Date Created in 2020-09-20 18:30
 * @Description:
 * @Attention: the annotated method must have a parameter whose type must implement the IHasPassword interface
 * @see IHasPassword
 */

//todo move to  lower library
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EncodeUserPwd {
}
