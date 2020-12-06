package fun.enou.alpha.service;


import fun.enou.alpha.dto.dtoweb.DtoWebOldUserWord;

import java.util.List;

/**
 * @Author: nagi
 * @Modified By:
 * @Date Created in 2020-09-22 18:37
 * @Description:
 * @Attention:
 */
public interface IOldUserWordService {
     DtoWebOldUserWord saveWord(DtoWebOldUserWord webUserWord);
     DtoWebOldUserWord updateWord(DtoWebOldUserWord webUserWord);
     List<DtoWebOldUserWord> getAllWordsAfter(Long timeStamp);
     
     String getOneRandomWord();
}
