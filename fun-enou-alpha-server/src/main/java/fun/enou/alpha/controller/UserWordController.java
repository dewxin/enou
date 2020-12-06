package fun.enou.alpha.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fun.enou.alpha.service.IUserWordService;
import fun.enou.core.msg.AutoWrapMsg;
import fun.enou.core.msg.EnouMessageException;

@RequestMapping("/word")
@RestController
@AutoWrapMsg
public class UserWordController {

    @Autowired
    IUserWordService userWordService;

    @PostMapping("/learn")
    public void learnWord(@RequestParam(value = "spell") String spell) throws EnouMessageException {
        userWordService.learnWord(spell);
    }

}
