package fun.enou.statistic.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fun.enou.statistic.dto.dtoweb.DtoWebChalAnswerCount;
import fun.enou.statistic.service.IBotChalStatService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/bot/challenge")
public class BotChallengeController {

    @Autowired
    private IBotChalStatService botChalStatService;
    
    @PostMapping(value="/answer")
    public void pileUpChalAnswerCount(@RequestBody DtoWebChalAnswerCount answer) {
        botChalStatService.pileUpChalAnswerCount(answer);
    }
    
}
