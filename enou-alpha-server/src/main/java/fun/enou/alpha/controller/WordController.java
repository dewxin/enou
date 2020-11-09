package fun.enou.alpha.controller;

import fun.enou.alpha.dto.dtoweb.DtoWebUserWord;
import fun.enou.alpha.misc.SessionHolder;
import fun.enou.alpha.service.IUserWordService;
import fun.enou.core.msg.AutoResponseMsg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    /**
     * 上传单词
     * @param webUserWord
     * @return 
     */
    @PostMapping
    public Object saveWord(@RequestBody @Valid DtoWebUserWord webUserWord) {
        String word = webUserWord.getWord().toLowerCase();
        webUserWord.setWord(word);
        
        DtoWebUserWord retWord = userWordService.saveWord(webUserWord);
        return retWord;
    }

    /**
     * 获取 unix时间戳之后 学习的所有单词
     * @param time unix时间戳，不传默认为0
     * @return 返回单词
     */
    @GetMapping
    public Object getWordAfter(
    		@RequestParam(value="time", defaultValue = "0") Long time) {
        return userWordService.getAllWordsAfter(time);
    }

    /**
     * 更新单词, 因为pc端识图不一定准确
     * @param webUserWord
     */
    @PutMapping
    public void modifyWord(@RequestBody @Valid DtoWebUserWord webUserWord) {
        userWordService.updateWord(webUserWord);
    }
}
