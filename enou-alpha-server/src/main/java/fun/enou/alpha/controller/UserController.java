package fun.enou.alpha.controller;

import fun.enou.alpha.dto.dtoweb.DtoWebUser;
import fun.enou.alpha.misc.SessionHolder;
import fun.enou.alpha.service.IUserService;
import fun.enou.core.msg.EnouMessageException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountException;
import javax.security.auth.login.AccountNotFoundException;
import javax.validation.Valid;

@RestController
@RequestMapping
public class UserController {

	@Autowired
	IUserService userService;
	
    @Autowired
    SessionHolder sessionHolder;

	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/login")
	public String getToken(@RequestBody @Valid DtoWebUser user) throws AccountException {
		DtoWebUser webUser = userService.findByAccountAndPassword(user);
		if (webUser == null) {
			String exceptionString = String.format("cannot find user {}", user.getAccount());
			throw new AccountNotFoundException(exceptionString);
		}

		String token = userService.loginGetToken(webUser);

		return token;
	}
	
	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/logout")
	public void logout() throws AccountException {
		
		Long userId = sessionHolder.getUserId();
		String userToken  = sessionHolder.getUserToken();
		
		userService.logout(userId, userToken);
	}
	
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/register")
    public void registerUser(@RequestBody @Valid DtoWebUser user) throws EnouMessageException {
        userService.saveUser(user);
    }
    
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/token/check")
    public void checkToken() throws AccountException {
    	// It's done in the intercepter.
    }
    
}
