package fun.enou.alpha.repository.ext;

import fun.enou.alpha.dto.dtodb.DtoDbUserWord;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

	@Override
	public DtoDbUserWord findOneRandom() {
		return sqlSession.getMapper(UserWordExt.class).findOneRandom();
	}
}
