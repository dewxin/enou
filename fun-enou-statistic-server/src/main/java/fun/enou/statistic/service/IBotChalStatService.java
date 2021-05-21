package fun.enou.statistic.service;

import fun.enou.statistic.dto.dtoweb.DtoWebChalAnswerCount;

/**
 * qq bot statistic service
 */
public interface IBotChalStatService {
    void pileUpChalAnswerCount(DtoWebChalAnswerCount answer);
}
