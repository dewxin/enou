package fun.enou.alpha.dto.dtoweb;

import fun.enou.alpha.dto.dtodb.DtoDbUser;
import fun.enou.core.IHasPassword;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public class DtoWebUser implements Cloneable, IHasPassword {

    private Long id;

    @NotNull
    @Length(min = 5, max = 20, message = "account length out of range")
    private String account;

    @NotNull
    @Length(min = 6, max = 45, message = "password length out of range")
    private String password;

    public DtoWebUser() {

    }

    public DtoWebUser(Long id, String account, String password) {
        this.id = id;
        this.account = account;
        this.password = password;
    }

    public DtoWebUser clone() {
        return new DtoWebUser(id, account, password);
    }

    public DtoDbUser toDtoDb() {
        return new DtoDbUser(id, account, password);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public DtoWebUser withPassword(String password) {
        setPassword(password);
        return this;
    }
}
