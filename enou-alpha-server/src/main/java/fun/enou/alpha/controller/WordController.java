package fun.enou.alpha.controller;

import fun.enou.alpha.dto.dtoweb.DtoWebUserWord;
import fun.enou.alpha.misc.SessionHolder;
import fun.enou.alpha.service.IUserWordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;

import javax.validation.Valid;
import java.util.List;

/**
 * @author: nagi
 * @Date Created in 2020-09-22 20:00
 * @Description:
 * @Attention:
 */

@RestController
@RequestMapping("/word")
@Slf4j
public class WordController {

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
