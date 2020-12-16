package fun.enou.alpha.service;

import static org.hamcrest.CoreMatchers.containsString;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import fun.enou.alpha.dto.dtodb.DtoDbDictDef;
import fun.enou.alpha.dto.dtodb.DtoDbDictWord;
import fun.enou.alpha.dto.dtoweb.DtoWebWord;
import fun.enou.alpha.msg.MsgEnum;
import fun.enou.alpha.repository.DictDefRepository;
import fun.enou.alpha.repository.DictWordRepository;
import fun.enou.core.msg.EnouMessageException;
import fun.enou.core.redis.RedisManager;
import fun.enou.core.tool.RandomUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TWordService implements IWordService{

	@Autowired
	private DictWordRepository wordRepository;
	
	@Autowired
	private DictDefRepository defRepository;

	@Autowired
	private RedisManager redisManager;

	@Override
	public void uploadWord(DtoWebWord webWord) {
		
		if(webWord.getLinkWord() != null) {
			try {

				DtoDbDictWord linkWord = wordRepository.findBySpell(webWord.getLinkWord());
				DtoDbDictWord dictWord = webWord.toDtoDbWord();
				dictWord.setDefId(linkWord.getDefId());
				wordRepository.save(dictWord);
				return;
			} catch (Exception ex) {
				String messageTrace =Arrays.toString(ex.getStackTrace());
				log.error(messageTrace);
			}

		}

		DtoDbDictWord dictWord = webWord.toDtoDbWord();
		List<DtoDbDictDef> dictDefList = webWord.toDtoDbDef();
		List<Integer> resultDefList = new LinkedList<>();
		for(DtoDbDictDef dictDef : dictDefList) {
			DtoDbDictDef result = defRepository.save(dictDef);
			resultDefList.add(result.getId());
		}
		
		ObjectMapper objMapper = new ObjectMapper();
		String idList = "";
		try {
			idList = objMapper.writeValueAsString(resultDefList);
		} catch (JsonProcessingException e) {
			log.warn(e.getMessage());
		}
		dictWord.setDefId(idList);
		wordRepository.save(dictWord);
	}
	
	private DtoWebWord getWebWordByDictWord(DtoDbDictWord dbDictWord) throws EnouMessageException {
		ObjectMapper objectMapper = new ObjectMapper();
		
		List<Integer> defIdList = new LinkedList<>();
		try {
			defIdList = objectMapper.readValue(dbDictWord.getDefId(), new TypeReference<List<Integer>>(){});
		} catch (JsonProcessingException e) {
			log.warn(e.getMessage());
			MsgEnum.WORD_DEF_LIST_PARSE_FAIL.ThrowException();
		}
		
		Iterable<DtoDbDictDef> dictDefList =  defRepository.findAllById(defIdList);
		
		return new DtoWebWord(dbDictWord, dictDefList);
	}

	@Override
	public DtoWebWord getWebWord(String spell) throws EnouMessageException {

		DtoDbDictWord dbDictWord = wordRepository.findBySpell(spell);
		if(dbDictWord == null) {
			MsgEnum.WORD_NOT_FOUND.ThrowException();
		}
		
		return getWebWordByDictWord(dbDictWord);
	}

	@Override
	public List<DtoWebWord> getWebWordList(int count) throws EnouMessageException {
		
		List<DtoWebWord> resultList = new LinkedList<>();
		
        try(Jedis jedis = redisManager.getJedis()) {

			String keyName = "wordIdToSpell";
			String script = MessageFormat.format("return redis.call(''hrandmember'', ''{0}'', {1})", keyName, count);

			List<Object> keyValueList = (List<Object>)(jedis.eval(script));
			for(int i = 0; i< keyValueList.size(); i+=2){
				int id = Integer.parseInt(keyValueList.get(i).toString());
				Optional<DtoDbDictWord> dbDictWordOptional = wordRepository.findById(id);
				if(!dbDictWordOptional.isPresent())
					continue;
			
				DtoWebWord word = getWebWordByDictWord(dbDictWordOptional.get());
				resultList.add(word);
			}

		}
		
		return resultList;
	}

}
