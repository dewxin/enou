package fun.enou.core.exception.account;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.security.auth.login.AccountException;

/**
 * @Author: nagi
 * @Modified By:
 * @Date Created in 2020-09-24 21:55
 * @Description:
 * @Attention:
 */

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Account Already Existed")
public class AccountExistedException extends AccountException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AccountExistedException(String msg) {
        super(msg);
    }
}
