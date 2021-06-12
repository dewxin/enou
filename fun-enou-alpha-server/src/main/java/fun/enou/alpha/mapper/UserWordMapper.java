package fun.enou.alpha.mapper;

import java.util.List;

import fun.enou.alpha.dto.dtodb.ext.DtoDbUserWord;
import fun.enou.alpha.dto.dtodb.ext.DtoDbUserWordPage;
import fun.enou.alpha.dto.dtodb.ext.DtoDbUserWordTimeStampPage;

public interface UserWordMapper {
       void save(DtoDbUserWord dbUserWord);
       List<Integer> getAllWordList(DtoDbUserWordPage page);

       List<Integer> getWordAfterTime(DtoDbUserWordTimeStampPage timeStamp);
}
