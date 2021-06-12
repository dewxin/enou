package fun.enou.alpha.controller;

import java.util.List;
import java.util.stream.Collectors;

import fun.enou.core.msg.EnouMsgJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fun.enou.alpha.dto.dtoweb.DtoWebUserWord;
import fun.enou.alpha.service.UserWordService;
import fun.enou.core.msg.AutoWrapMsg;
import fun.enou.core.msg.EnouMessageException;

@RequestMapping("/word")
@RestController
@AutoWrapMsg
public class UserWordController {

    @Autowired
    UserWordService userWordService;

    @PostMapping("/learn")
    public void learnWord(@RequestParam(value = "spell") String spell) throws EnouMessageException {
        userWordService.learnWord(spell);
    }

    @GetMapping("/getKnownWord")
    public ResponseEntity<List<String>> getKnownWords(@RequestParam int offset, @RequestParam int count) {
        List<String> wordList = userWordService.getKnownWords(offset, count);

        return ResponseEntity.ok(wordList);
    }

    @GetMapping
    public ResponseEntity<List<DtoWebUserWord>> getWordAfter(@RequestParam Long time, @RequestParam int offset, @RequestParam int count) {
        //todo
    	List<DtoWebUserWord> result = userWordService.getAllWordsAfter(time,offset,count).stream().map( wordSpell-> new DtoWebUserWord(0l, wordSpell)).collect(Collectors.toList());
    	return ResponseEntity.ok(result);
    }

    @GetMapping("/random")
    ResponseEntity<String> getOneRandomWord() {
        //todo
        return ResponseEntity.ok("");
    }

}
