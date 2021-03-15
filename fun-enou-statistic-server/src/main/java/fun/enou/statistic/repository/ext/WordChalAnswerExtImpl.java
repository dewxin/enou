package fun.enou.statistic.repository.ext;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fun.enou.statistic.dto.dtodb.DtoDbChalAnswerCount;

@Component
public class WordChalAnswerExtImpl implements WordChalAnswerExt{

    @Autowired
    private SqlSession sqlSession;

    @Override
    public void pileUp(DtoDbChalAnswerCount answerCount) {
        sqlSession.getMapper(WordChalAnswerExt.class).pileUp(answerCount);
    }
    
}
