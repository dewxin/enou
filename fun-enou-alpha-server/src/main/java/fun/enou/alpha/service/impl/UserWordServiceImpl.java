package fun.enou.alpha.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import fun.enou.alpha.mapper.DictWordMapper;
import fun.enou.alpha.mapper.UserWordMapper;
import fun.enou.alpha.service.UserWordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fun.enou.alpha.dto.dtodb.DtoDbDictWord;
import fun.enou.alpha.dto.dtodb.ext.DtoDbUserWord;
import fun.enou.alpha.dto.dtodb.ext.DtoDbUserWordPage;
import fun.enou.alpha.dto.dtodb.ext.DtoDbUserWordTimeStampPage;
import fun.enou.alpha.misc.SessionHolder;
import fun.enou.alpha.msg.MsgEnum;
import fun.enou.core.msg.EnouMessageException;
import fun.enou.core.redis.RedisManager;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

@Service
@Slf4j
public class UserWordServiceImpl implements UserWordService {

    @Autowired
    private DictWordMapper dictWordRepository;

    @Autowired
    private UserWordMapper userWordRepository;

    @Autowired
    private SessionHolder sessionHolder;

	@Autowired
	private RedisManager redisManager;


    @Override
    public void learnWord(String spell) throws EnouMessageException {
        DtoDbDictWord dbDictWord = dictWordRepository.findBySpell(spell);
        if(dbDictWord == null) {
            MsgEnum.WORD_NOT_FOUND.ThrowException();
        }

        Long userId = sessionHolder.getUserId();
        DtoDbUserWord dbUserWord = new DtoDbUserWord();
        dbUserWord.setUserId(userId);
        dbUserWord.setWordId(dbDictWord.getId());
        userWordRepository.save(dbUserWord);

    }

	private List<String> getKnownWordList(Long userId, int offset, int count) {
		//todo need to profile the cost
		DtoDbUserWordPage page = new DtoDbUserWordPage(userId, offset, count);
		List<Integer> wordIdList = userWordRepository.getAllWordList(page);

		log.info("getKnownWordList userId {} word count {}",userId, wordIdList.size());

		List<String> wordStrList = wordIdToSpell(wordIdList);

		if(wordIdList.size() != wordStrList.size()){
			log.warn("getKnownWordList fail, user id {}, word id size {}, word str size {}", userId, wordIdList.size(), wordStrList.size());
		}

		return wordStrList;
	}

	private List<String> wordIdToSpell(List<Integer> wordIdList) {
		List<String> wordStrList = new ArrayList<>(wordIdList.size());
		//todo  use array
		try(Jedis jedis = redisManager.getJedis()){
			for(Integer id : wordIdList) {
				String strId = String.valueOf(id);

				List<String> partialWordList = jedis.hmget("wordIdToSpell", strId);
				wordStrList.addAll(partialWordList);
			}
		}

		return wordStrList;
	}

	@Override
	public List<String> getKnownWords(int offset, int count) {
		Long userId = sessionHolder.getUserId();
		List<String> knownWordList = getKnownWordList(userId, offset, count);
		return knownWordList;
	}

	@Override
	public List<String> getAllWordsAfter(Long timeStampMilli, int offset, int count) {
		Timestamp updatedTimestamp = new Timestamp(timeStampMilli);
        Long userId = sessionHolder.getUserId();

		DtoDbUserWordTimeStampPage timeStamp = new DtoDbUserWordTimeStampPage();
		timeStamp.setUpdatedTimestamp(updatedTimestamp);
		timeStamp.setUserId(userId);
		timeStamp.setOffset(offset);
		timeStamp.setLimit(count);

        List<Integer> dbWordList = userWordRepository.getWordAfterTime(timeStamp);
		return wordIdToSpell(dbWordList);
	}

}
