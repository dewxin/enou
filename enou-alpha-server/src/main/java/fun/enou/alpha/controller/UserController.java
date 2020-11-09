package fun.enou.alpha.controller;

import fun.enou.alpha.dto.dtoweb.DtoWebUser;
import fun.enou.alpha.misc.SessionHolder;
import fun.enou.alpha.msg.MsgEnum;
import fun.enou.alpha.service.IUserService;
import fun.enou.core.msg.AutoResponseMsg;
import fun.enou.core.msg.EnouMessageException;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
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

    /**
     * 用户登录获取token
     * @param user 账号密码
     * @return 返回封装后的token
     * @throws EnouMessageException
     */
	@PostMapping("/login")
	public Object getToken(@RequestBody @Valid DtoWebUser user) throws EnouMessageException {
		DtoWebUser webUser = userService.findByAccountAndPassword(user);
		if (webUser == null) {
			log.warn("account or pwd is wrong, user:{}", user.getAccount());
			MsgEnum.ACCOUNT_OR_PWD_WRONG.ThrowException();
		}

		return userService.loginGetToken(webUser);
	}
	
	/**
	 * 用户登出，清理token
	 */
	@PostMapping("/logout")
	public void logout(){
		
		userService.logout();
	}
	
	/**
	 * 用户注册
	 * @param user 账号密码
	 * @throws EnouMessageException
	 */
    @PostMapping("/register")
    public void registerUser(@RequestBody @Valid DtoWebUser user) throws EnouMessageException {
        userService.saveUser(user);
    }
    
    /**
     * 检测token是否有效， 实际代码在intercepter中实现
     */
    @GetMapping("/token/check")
    public void checkToken(){
    	// It's done in the intercepter.
    }
    
}
