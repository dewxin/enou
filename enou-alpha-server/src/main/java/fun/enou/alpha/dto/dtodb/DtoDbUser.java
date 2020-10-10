package fun.enou.alpha.dto.dtodb;

import fun.enou.alpha.dto.dtoweb.DtoWebUser;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;


@Table("euser")
public class DtoDbUser {
    @Id
    private Long id;
    private String account;
    private String password;

    public DtoDbUser(Long id, String account, String password) {
        this.id = id;
        this.account = account;
        this.password = password;
    }

    public DtoWebUser toDtoWeb() {
        return new DtoWebUser(id, account, password);
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
}
