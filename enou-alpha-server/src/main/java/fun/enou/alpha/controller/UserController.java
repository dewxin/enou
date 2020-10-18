package fun.enou.alpha.controller;

import fun.enou.alpha.dto.dtoweb.DtoWebUser;
import fun.enou.alpha.misc.SessionHolder;
import fun.enou.alpha.msg.MsgEnum;
import fun.enou.alpha.service.IUserService;
import fun.enou.core.msg.AutoResponseMsg;
import fun.enou.core.msg.EnouMessageException;
import fun.enou.core.msg.EnouMsgJson;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping
@AutoResponseMsg
public class UserController {

	@Autowired
	IUserService userService;
	
    @Autowired
    SessionHolder sessionHolder;

	@PostMapping("/login")
	public Object getToken(@RequestBody @Valid DtoWebUser user) throws EnouMessageException {
		DtoWebUser webUser = userService.findByAccountAndPassword(user);
		if (webUser == null) {
			log.warn("account or pwd is wrong, user:{}", user.getAccount());
			throw MsgEnum.ACCOUNT_OR_PWD_WRONG.Exception();
		}

		return userService.loginGetToken(webUser);
	}
	
	@PostMapping("/logout")
	public void logout(){
		
		Long userId = sessionHolder.getUserId();
		String userToken  = sessionHolder.getUserToken();
		
		userService.logout(userId, userToken);
	}
	
    @PostMapping("/register")
    public void registerUser(@RequestBody @Valid DtoWebUser user) throws EnouMessageException {
        userService.saveUser(user);
    }
    
    @GetMapping("/token/check")
    public void checkToken(){
    	// It's done in the intercepter.
    }
    
}
