package fun.enou.alpha.repository.ext;


import fun.enou.alpha.dto.dtodb.DtoDbOldUserWord;

/**
 * @Author: nagi
 * @Modified By:
 * @Date Created in 2020-10-05 16:26
 * @Description:
 * @Attention:
 */

public interface OldUserWordExt {
    void update(DtoDbOldUserWord dbUserWord);
    
    DtoDbOldUserWord findOneRandom();
}
