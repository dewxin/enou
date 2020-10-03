package samuragi.enou.dto.dtodb;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import samuragi.enou.dto.dtoweb.DtoWebUser;



// todo build an annotation could be used to automatically generate copy function
// in this case,  if I annotate @EnouCopy("DtoWebUserWord"), it should generate a function
// named copyToDtoWebUserWord()
// that could produce a DtoWebUserWord object.
//  https://www.baeldung.com/lombok-custom-annotation
// https://github.com/mplushnikov/lombok-intellij-plugin

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
