package fun.enou.alpha.repository.ext;

import fun.enou.alpha.dto.dtodb.DtoDbUserThirdInfo;

public interface UserThirdInfoExt {
	
	void save(DtoDbUserThirdInfo dbUserThirdInfo);
	
	DtoDbUserThirdInfo find(DtoDbUserThirdInfo dbUserThirdInfo);
}
