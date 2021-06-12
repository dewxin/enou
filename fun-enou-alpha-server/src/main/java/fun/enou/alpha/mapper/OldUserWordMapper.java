package fun.enou.alpha.mapper;


import fun.enou.alpha.dto.dtodb.DtoDbOldUserWord;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

/**
 * @Author: nagi
 * @Modified By:
 * @Date Created in 2020-10-05 16:26
 * @Description:
 * @Attention:
 */

public interface OldUserWordMapper {
    void update(DtoDbOldUserWord dbUserWord);
    
    DtoDbOldUserWord findOneRandom();

    @Insert("INSERT INTO user_word(user_id, word) VALUES(#{userId}, #{word}) ON DUPLICATE KEY UPDATE query_time=query_time+1")
    int saveOrPlusQueryTime(@Param("userId")Long userId, @Param("word") String word);
}
