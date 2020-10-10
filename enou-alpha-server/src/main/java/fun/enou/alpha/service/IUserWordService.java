package fun.enou.alpha.service;


import fun.enou.alpha.dto.dtoweb.DtoWebUserWord;

import java.util.List;

/**
 * @Author: nagi
 * @Modified By:
 * @Date Created in 2020-09-22 18:37
 * @Description:
 * @Attention:
 */
public interface IUserWordService {
     DtoWebUserWord saveWord(DtoWebUserWord webUserWord);
     DtoWebUserWord updateWord(DtoWebUserWord webUserWord);
     List<DtoWebUserWord> getAllWordsAfter(Long timeStamp);
}
