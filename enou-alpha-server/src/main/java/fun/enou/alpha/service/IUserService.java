package fun.enou.alpha.service;


import fun.enou.alpha.dto.dtoweb.DtoWebUser;

import javax.security.auth.login.AccountException;

public interface IUserService {
    DtoWebUser saveUser(DtoWebUser webUser) throws AccountException;

    boolean loginInfoIsCorrect(DtoWebUser webUser);

    DtoWebUser findByAccountAndPassword(DtoWebUser webUser);

    String loginGetToken(DtoWebUser webUser);
    
    void logout(Long userId, String userToken);
}
