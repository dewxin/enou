package fun.enou.statistic.repository.ext;

import fun.enou.statistic.dto.dtodb.DtoDbChalAnswerCount;

public interface WordChalAnswerExt {

    void pileUp(DtoDbChalAnswerCount answerCount);
}
