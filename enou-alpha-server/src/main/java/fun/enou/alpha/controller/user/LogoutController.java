package fun.enou.alpha.controller.user;

import javax.security.auth.login.AccountException;
import javax.security.auth.login.AccountNotFoundException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import fun.enou.alpha.dto.dtoweb.DtoWebUser;
import fun.enou.alpha.misc.SessionHolder;
import fun.enou.alpha.service.IUserService;

@RestController
@RequestMapping("/logout")
public class LogoutController {
	
	@Autowired
	IUserService userService;
	
    @Autowired
    SessionHolder sessionHolder;

	@ResponseStatus(HttpStatus.OK)
	@PostMapping
	public void logout() throws AccountException {
		
		Long userId = sessionHolder.getUserId();
		String userToken  = sessionHolder.getUserToken();
		
		userService.logout(userId, userToken);
	}

}
