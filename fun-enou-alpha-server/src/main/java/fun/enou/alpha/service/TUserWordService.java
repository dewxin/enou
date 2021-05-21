package fun.enou.alpha.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fun.enou.alpha.dto.dtodb.DtoDbDictWord;
import fun.enou.alpha.dto.dtodb.ext.DtoDbUserWord;
import fun.enou.alpha.misc.SessionHolder;
import fun.enou.alpha.msg.MsgEnum;
import fun.enou.alpha.repository.DictWordRepository;
import fun.enou.alpha.repository.UserWordRepository;
import fun.enou.core.msg.EnouMessageException;
import fun.enou.core.redis.RedisManager;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

@Service
@Slf4j
public class TUserWordService implements IUserWordService {

    @Autowired
    private DictWordRepository dictWordRepository;

    @Autowired
    private UserWordRepository userWordRepository;

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

	@Override
	public List<String> getUnknownWords(String statement)  {
		Long userId = sessionHolder.getUserId();
		List<String> knownWordList = getKnownWordList(userId);

		statement = statement.replaceAll("[:,)(\'\"0-9.]| - ", "");
		String[] wordArray = statement.toLowerCase().split(" ");
		HashSet<String> unknownWordSet = new HashSet<>();
		Collections.addAll(unknownWordSet, wordArray);

		log.info("known word count is {}", knownWordList.size());
		log.info("unknown word count before remove is {}", unknownWordSet.size());
		unknownWordSet.removeAll(knownWordList);
		log.info("unknown word count after remove is {}", unknownWordSet.size());

		return new ArrayList<>(unknownWordSet);
	}
	

	private List<String> getKnownWordList(Long userId) {
		//todo need to profile the cost
		List<Integer> wordIdList = userWordRepository.getAllWordList(userId);
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

    
}
