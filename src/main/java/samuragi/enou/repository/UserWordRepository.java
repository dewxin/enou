package samuragi.enou.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import samuragi.enou.dto.dtodb.DtoDbUserWord;

import java.sql.Timestamp;
import java.util.List;

/**
 * @Author: nagi
 * @Modified By:
 * @Date Created in 2020-09-22 18:09
 * @Description:
 * @Attention:
 */
public interface UserWordRepository extends CrudRepository<DtoDbUserWord, Long> {

    @Query("INSERT INTO user_word(user_id, word) VALUES(:userId, :word) ON DUPLICATE KEY UPDATE query_time=query_time+1")
    @Modifying()
    int saveOrPlusQueryTime(@Param("userId")Long userId, @Param("word") String word);


    List<DtoDbUserWord> getAllByUserIdAndCreatedAtAfter(Long userId, Timestamp time);

    @Query("UPDATE user_word SET word=:word where id=:id")
    @Modifying()
    int updateWord(@Param("id") Long id, @Param("word") String word);
}
