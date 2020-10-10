package fun.enou.alpha.controller.user;

import fun.enou.alpha.dto.dtoweb.DtoWebUser;
import fun.enou.alpha.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountException;
import javax.validation.Valid;

@RestController
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private IUserService userService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void registerUser(@RequestBody @Valid DtoWebUser user) throws AccountException {
        userService.saveUser(user);
    }
}
