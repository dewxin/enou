package fun.enou.alpha.repository;

import fun.enou.alpha.dto.dtodb.DtoDbOldUserWord;
import fun.enou.alpha.repository.ext.OldUserWordExt;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

/**
 * @Author: nagi
 * @Modified By:
 * @Date Created in 2020-09-22 18:09
 * @Description:
 * @Attention:
 */
public interface OldUserWordRepository extends CrudRepository<DtoDbOldUserWord, Long> , OldUserWordExt {

    @Query("INSERT INTO user_word(user_id, word) VALUES(:userId, :word) ON DUPLICATE KEY UPDATE query_time=query_time+1")
    @Modifying()
    int saveOrPlusQueryTime(@Param("userId")Long userId, @Param("word") String word);


    List<DtoDbOldUserWord> getAllByUserIdAndCreatedAtAfter(Long userId, Timestamp time);

    DtoDbOldUserWord getByUserIdAndWord(Long userId, String word);
}
