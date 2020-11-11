package fun.enou.alpha.repository.ext;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fun.enou.alpha.dto.dtodb.DtoDbUserThirdInfo;
import fun.enou.alpha.repository.UserThirdInfoRepository;

@Component
public class UserThirdInfoExtImpl implements UserThirdInfoRepository {

	@Autowired
	SqlSession sqlSession;

	@Override
	public void save(DtoDbUserThirdInfo dbUserThirdInfo) {
		sqlSession.getMapper(UserThirdInfoExt.class).save(dbUserThirdInfo);
	}

	@Override
	public DtoDbUserThirdInfo find(DtoDbUserThirdInfo dbUserThirdInfo) {
		return sqlSession.getMapper(UserThirdInfoExt.class).find(dbUserThirdInfo);
	}

}
