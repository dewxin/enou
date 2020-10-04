package samuragi.enou.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import samuragi.enou.dto.dtoweb.DtoWebUserWord;
import samuragi.enou.misc.SessionHolder;
import samuragi.enou.service.IUserWordService;

import javax.validation.Valid;
import java.util.List;

/**
 * @Author: nagi
 * @Modified By:
 * @Date Created in 2020-09-22 20:00
 * @Description:
 * @Attention:
 */

@RestController
@RequestMapping("/word")
public class WordController {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ArticleController.class);

    @Autowired
    IUserWordService userWordService;

    @Autowired
    SessionHolder sessionHolder;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public DtoWebUserWord saveWord(@RequestBody @Valid DtoWebUserWord webUserWord) {
        String word = webUserWord.getWord().toLowerCase();
        webUserWord.setWord(word);
        return userWordService.saveWord(webUserWord);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<DtoWebUserWord> getWordAfter(@Param("time") Long time) {
        if(time == null) {
            time = 0L;
            log.info("userId {} getWordAfter timeStamp is null", sessionHolder.getUserId());
        }
        return userWordService.getAllWordsAfter(time);
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public void modifyWord(@RequestBody @Valid DtoWebUserWord webUserWord) {
        userWordService.updateWord(webUserWord);
    }
}
