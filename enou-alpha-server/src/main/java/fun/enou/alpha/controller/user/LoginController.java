package fun.enou.alpha.controller.user;

import fun.enou.alpha.dto.dtoweb.DtoWebUser;
import fun.enou.alpha.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountException;
import javax.security.auth.login.AccountNotFoundException;
import javax.validation.Valid;

@RestController
@RequestMapping("/login")
public class LoginController {

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	IUserService userService;

	@ResponseStatus(HttpStatus.OK)
	@PostMapping
	public String getToken(@RequestBody @Valid DtoWebUser user) throws AccountException {
		DtoWebUser webUser = userService.findByAccountAndPassword(user);
		if (webUser == null) {
			throw new AccountNotFoundException("user info error");
		}

		String token = userService.generateAndAddToken(webUser);

		return token;
	}

	// todo log out logic
}
