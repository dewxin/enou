package fun.enou.alpha.repository.ext;

import java.util.List;

import fun.enou.alpha.dto.dtodb.ext.DtoDbUserWord;

public interface UserWordExt {
       void save(DtoDbUserWord dbUserWord);
       List<Integer> getAllWordList(Long userId);
}
