package fun.enou.alpha.mapper;

import fun.enou.alpha.dto.dtodb.DtoDbUserThirdInfo;

public interface UserThirdInfoMapper {
	
	void save(DtoDbUserThirdInfo dbUserThirdInfo);
	
	DtoDbUserThirdInfo find(DtoDbUserThirdInfo dbUserThirdInfo);
}
