package fun.enou.statistic.service;

import fun.enou.statistic.dto.dtodb.DtoDbChalAnswerCount;
import fun.enou.statistic.dto.dtoweb.DtoWebChalAnswerCount;
import fun.enou.statistic.repository.WordChalAnswerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

/**
 * qq bot statistic service
 */
@Service
@Slf4j
public class TBotChalStatService implements IBotChalStatService {

    @Autowired
    private WordChalAnswerRepository wordChalAnswerRepository;

    @Override
    public void pileUpChalAnswerCount(DtoWebChalAnswerCount answer) {

        DtoDbChalAnswerCount dbAnswer = answer.toDb();
        wordChalAnswerRepository.pileUp(dbAnswer);
    }
}
