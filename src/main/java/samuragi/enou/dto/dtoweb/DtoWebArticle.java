package samuragi.enou.dto.dtoweb;


import samuragi.enou.dto.dtodb.DtoDbArticle;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class DtoWebArticle {

    @NotNull(message = "title cannot be null")
    @Size(min = 5, max = 100, message = "title length cannot be lesser than 5 or greater than 100")
    private String title;

    @NotNull(message = "content cannot be null")
    @Size(min = 10, max = 250000, message = "size not in the range(10,250000)")
    private String content;


    public DtoWebArticle() {
    }

    public DtoWebArticle(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public DtoDbArticle toDtoDb() {
        return new DtoDbArticle(null, title, content, 0);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
