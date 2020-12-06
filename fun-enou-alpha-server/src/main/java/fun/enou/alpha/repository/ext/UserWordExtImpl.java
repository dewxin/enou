package fun.enou.alpha.repository.ext;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fun.enou.alpha.dto.dtodb.ext.DtoDbUserWord;
import fun.enou.alpha.repository.UserWordRepository;

@Component
public class UserWordExtImpl implements UserWordRepository {

    @Autowired
    private SqlSession sqlSession;

    @Override
    public void save(DtoDbUserWord dbUserWord) {
        sqlSession.getMapper(UserWordExt.class).save(dbUserWord);
    }

    @Override
    public List<Integer> getAllWordList(Long userId) {
        return sqlSession.getMapper(UserWordExt.class).getAllWordList(userId);
    }
    
}
