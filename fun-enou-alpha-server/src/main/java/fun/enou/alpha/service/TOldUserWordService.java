package fun.enou.alpha.service;

import fun.enou.alpha.dto.dtodb.DtoDbOldUserWord;
import fun.enou.alpha.dto.dtoweb.DtoWebOldUserWord;
import fun.enou.alpha.misc.SessionHolder;
import fun.enou.alpha.repository.OldUserWordRepository;
import lombok.extern.slf4j.Slf4j;

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
@Slf4j
public class TOldUserWordService implements IOldUserWordService{
	
    @Autowired
    OldUserWordRepository wordRepository;

    @Autowired
    SessionHolder sessionHolder;

    @Override
    public DtoWebOldUserWord saveWord(DtoWebOldUserWord webUserWord) {

        Long userId = sessionHolder.getUserId();

        int ret = wordRepository.saveOrPlusQueryTime(userId, webUserWord.getWord());
        DtoDbOldUserWord dbUserWord = wordRepository.getByUserIdAndWord(userId, webUserWord.getWord());
        return dbUserWord.toDtoWeb();
    }

    @Override
    public DtoWebOldUserWord updateWord(DtoWebOldUserWord webUserWord) {
        //todo if the word  after modified already existed in the database,
        // there will be an error.
        wordRepository.update(webUserWord.toDtoDb());
        return webUserWord;
    }

    @Override
    public List<DtoWebOldUserWord> getAllWordsAfter(Long timeStampMilli) {

        Timestamp timestamp = new Timestamp(timeStampMilli);
        Long userId = sessionHolder.getUserId();

        List<DtoDbOldUserWord> dbWordList = wordRepository.getAllByUserIdAndCreatedAtAfter(userId, timestamp);

        return dbWordList.stream().map(DtoDbOldUserWord::toDtoWeb).collect(Collectors.toList());
    }

	@Override
	public String getOneRandomWord() {
		return wordRepository.findOneRandom().getWord();
	}
}
