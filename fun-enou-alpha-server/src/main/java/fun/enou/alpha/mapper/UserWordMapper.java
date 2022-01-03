package fun.enou.alpha.mapper;

import java.util.List;

import fun.enou.alpha.dto.dtodb.ext.DtoDbUserWord;
import fun.enou.alpha.dto.dtodb.ext.DtoDbUserWordPage;
import fun.enou.alpha.dto.dtodb.ext.DtoDbUserWordTimeStampPage;
import org.apache.ibatis.annotations.Select;

public interface UserWordMapper {

       @Select("select count(word_id) from euser_word where user_id = #{userId}")
       int count(long userId);
       void save(DtoDbUserWord dbUserWord);

       List<Integer> getAllWordList(DtoDbUserWordPage page);
       List<Integer> getWordAfterTime(DtoDbUserWordTimeStampPage timeStamp);
}
