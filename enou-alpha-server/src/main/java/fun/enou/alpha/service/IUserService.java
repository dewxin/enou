package fun.enou.alpha.service;


import fun.enou.alpha.dto.dtoweb.DtoWebUser;
import fun.enou.core.msg.EnouMessageException;


public interface IUserService {
    DtoWebUser saveUser(DtoWebUser webUser) throws EnouMessageException;

    boolean loginInfoIsCorrect(DtoWebUser webUser);

    DtoWebUser findByAccountAndPassword(DtoWebUser webUser);

    String loginGetToken(DtoWebUser webUser);
    
    void logout(Long userId, String userToken);
}
