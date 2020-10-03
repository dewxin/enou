package samuragi.enou.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import samuragi.enou.dto.dtoweb.DtoWebArticle;
import samuragi.enou.service.IArticleService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ArticleController.class);
    @Autowired
    IArticleService articleService;

    ///  todo token logic  multi database, put the user logic in a single database.
    // https://blog.csdn.net/qq_37345604/article/details/89377105#comments

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void uploadArticle(@RequestBody @Valid DtoWebArticle article) {
        articleService.saveArticle(article);
    }


    //todo use git
    //todo return value too huge, need to reduce
    @GetMapping
    public List<DtoWebArticle> getArticle() {

        List<DtoWebArticle> articles = articleService.getArticles(0, 10);
        return articles;
    }

    //todo analyze which words user have not learned,
    // and show them the meaning.
    @GetMapping("/{articleId}/unkownword")
    public void getArticleUnknownWords(@PathVariable("articleId") Long id) {

    }
}
