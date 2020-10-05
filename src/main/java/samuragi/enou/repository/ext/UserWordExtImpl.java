package samuragi.enou.repository.ext;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import samuragi.enou.dto.dtodb.DtoDbUserWord;

/**
 * @Author: nagi
 * @Modified By:
 * @Date Created in 2020-10-05 18:40
 * @Description:
 * @Attention:
 */
@Component
public class UserWordExtImpl implements UserWordExt {

    @Autowired
    private SqlSession sqlSession;

    @Override
    public void update(DtoDbUserWord dbUserWord) {
        sqlSession.getMapper(UserWordExt.class).update(dbUserWord);
    }
}
