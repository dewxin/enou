package fun.enou.alpha.service;

import fun.enou.alpha.dto.dtodb.DtoDbUserWord;
import fun.enou.alpha.dto.dtoweb.DtoWebUserWord;
import fun.enou.alpha.misc.SessionHolder;
import fun.enou.alpha.repository.UserWordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: nagi
 * @Modified By:
 * @Date Created in 2020-09-22 18:55
 * @Description:
 * @Attention:
 */
@Service
public class TUserWordService implements IUserWordService{
	
	
    @Autowired
    UserWordRepository wordRepository;

    @Autowired
    SessionHolder sessionHolder;

    @Override
    public DtoWebUserWord saveWord(DtoWebUserWord webUserWord) {

        Long userId = sessionHolder.getUserId();

        int ret = wordRepository.saveOrPlusQueryTime(userId, webUserWord.getWord());
        DtoDbUserWord dbUserWord = wordRepository.getByUserIdAndWord(userId, webUserWord.getWord());
        return dbUserWord.toDtoWeb();
    }

    @Override
    public DtoWebUserWord updateWord(DtoWebUserWord webUserWord) {
        //todo if the word  after modified already existed in the database,
        // there will be an error.
        wordRepository.update(webUserWord.toDtoDb());
        return webUserWord;
    }

    @Override
    public List<DtoWebUserWord> getAllWordsAfter(Long timeStampMilli) {

        Timestamp timestamp = new Timestamp(timeStampMilli);
        Long userId = sessionHolder.getUserId();

        List<DtoDbUserWord> dbWordList = wordRepository.getAllByUserIdAndCreatedAtAfter(userId, timestamp);

        return dbWordList.stream().map(DtoDbUserWord::toDtoWeb).collect(Collectors.toList());
    }
}
