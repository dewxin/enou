package fun.enou.alpha.dto.dtoweb;

import fun.enou.alpha.dto.dtodb.DtoDbArticle;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class DtoWebArticle {

    private Long id;

    @NotNull(message = "title cannot be null")
    @Size(min = 5, max = 100, message = "title length cannot be lesser than 5 or greater than 100")
    private String title;

    @NotNull(message = "content cannot be null")
    @Size(min = 10, max = 250000, message = "size not in the range(10,250000)")
    private String content;


    public DtoWebArticle() {
    }

    public DtoWebArticle(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public DtoDbArticle toDtoDb() {
        return new DtoDbArticle(id, title, content, 0);
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
