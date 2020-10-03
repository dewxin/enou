package samuragi.enou.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import samuragi.enou.dto.dtoweb.DtoWebUser;
import samuragi.enou.service.IUserService;

import javax.security.auth.login.AccountException;
import javax.validation.Valid;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    IUserService userService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public String getToken(@RequestBody @Valid DtoWebUser user) throws AccountException {
        DtoWebUser webUser = userService.findByAccountAndPassword(user);
        if(webUser == null) {
            throw new AccountException("user info error"); //todo handle the exception
        }

        String token = userService.generateAndAddToken(webUser);

        return token;
    }

    //todo log out logic
}
