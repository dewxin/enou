package samuragi.enou.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import samuragi.enou.dto.dtodb.DtoDbUserWord;
import samuragi.enou.dto.dtoweb.DtoWebUserWord;
import samuragi.enou.misc.SessionHolder;
import samuragi.enou.repository.UserWordRepository;

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
@Slf4j
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
        wordRepository.updateWord(webUserWord.getId(), webUserWord.getWord());
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
