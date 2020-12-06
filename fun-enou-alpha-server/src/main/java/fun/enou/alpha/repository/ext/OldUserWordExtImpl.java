package fun.enou.alpha.repository.ext;

import fun.enou.alpha.dto.dtodb.DtoDbOldUserWord;
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
public class OldUserWordExtImpl implements OldUserWordExt {

    @Autowired
    private SqlSession sqlSession;

    @Override
    public void update(DtoDbOldUserWord dbUserWord) {
        sqlSession.getMapper(OldUserWordExt.class).update(dbUserWord);
    }

	@Override
	public DtoDbOldUserWord findOneRandom() {
		return sqlSession.getMapper(OldUserWordExt.class).findOneRandom();
	}
}
