package fun.enou.alpha.controller;

import fun.enou.alpha.dto.dtoweb.DtoWebUserWord;
import fun.enou.alpha.misc.SessionHolder;
import fun.enou.alpha.service.IUserWordService;
import fun.enou.core.msg.AutoResponseMsg;
import fun.enou.core.msg.EnouMsgJson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@AutoResponseMsg
public class WordController {

    @Autowired
    IUserWordService userWordService;

    @Autowired
    SessionHolder sessionHolder;

    @PostMapping
    public Object saveWord(@RequestBody @Valid DtoWebUserWord webUserWord) {
        String word = webUserWord.getWord().toLowerCase();
        webUserWord.setWord(word);
        
        DtoWebUserWord retWord = userWordService.saveWord(webUserWord);
        return retWord;
    }

    @GetMapping
    public Object getWordAfter(
    		@RequestParam(value="time", defaultValue = "0") Long time) {
        return userWordService.getAllWordsAfter(time);
    }

    @PutMapping
    public void modifyWord(@RequestBody @Valid DtoWebUserWord webUserWord) {
        userWordService.updateWord(webUserWord);
    }
}
