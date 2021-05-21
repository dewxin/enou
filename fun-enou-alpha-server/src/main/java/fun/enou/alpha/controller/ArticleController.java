package fun.enou.alpha.controller;

import fun.enou.alpha.dto.dtoweb.DtoWebArticle;
import fun.enou.alpha.service.IGetUnknownWordService;
import fun.enou.core.msg.AutoWrapMsg;
import fun.enou.core.msg.EnouMessageException;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/article")
@AutoWrapMsg
public class ArticleController {
    @Autowired
    IGetUnknownWordService articleService;

    /// todo token logic multi database, put the user logic in a single database.
    // https://blog.csdn.net/qq_37345604/article/details/89377105#comments

    /**
     * 
     * @param article
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void uploadArticle(@RequestBody @Valid DtoWebArticle article) {
        articleService.saveArticle(article);
    }

    // todo return value too huge, need to reduce
    @GetMapping
    public ResponseEntity<List<DtoWebArticle>> getArticle(@RequestParam("pageIndex") int pageIndex) {

        List<DtoWebArticle> articles = articleService.getArticles(pageIndex, 10);
        return ResponseEntity.ok(articles);
    }

    @GetMapping("/{articleId}/unkownword")
    public ResponseEntity<List<String>> parseUnknownWords(@PathVariable("articleId") Long id) throws EnouMessageException {
        List<String> resultList = articleService.getUnknownWords(id);
        return ResponseEntity.ok(resultList);
    }
}
