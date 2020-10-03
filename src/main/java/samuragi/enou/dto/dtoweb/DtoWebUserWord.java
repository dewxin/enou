package samuragi.enou.dto.dtoweb;

import org.hibernate.validator.constraints.Length;
import samuragi.enou.dto.dtodb.DtoDbUserWord;

import javax.annotation.RegEx;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @Author: nagi
 * @Modified By:
 * @Date Created in 2020-09-22 18:38
 * @Description:
 * @Attention:
 */
public class DtoWebUserWord {

    private Long id;

    //todo it may be a phrase, so whitespace should be allowed
    @NotNull
    @Length(max = 60, message = "word is too long")
    @Pattern(regexp ="[a-zA-Z]+")
    private String word;

    public DtoWebUserWord() {
    }

    public DtoWebUserWord(Long id, String word) {
        this.id = id;
        this.word = word;
    }

    public DtoDbUserWord toDtoDb() {
        return new DtoDbUserWord(id, null,word,1,null);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
