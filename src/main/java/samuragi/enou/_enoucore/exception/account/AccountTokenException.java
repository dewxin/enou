package samuragi.enou._enoucore.exception.account;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.security.auth.login.AccountException;

/**
 * @Author: nagi
 * @Modified By:
 * @Date Created in 2020-09-24 21:47
 * @Description: generic token exception
 * @Attention:
 */

@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "Token Parsing Failed")
public class AccountTokenException extends AccountException {

    public AccountTokenException(String msg) {
        super(msg);
    }
}
