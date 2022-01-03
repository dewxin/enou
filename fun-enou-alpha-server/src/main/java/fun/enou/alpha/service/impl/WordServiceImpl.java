package fun.enou.alpha.service.impl;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import fun.enou.alpha.mapper.DictDefMapper;
import fun.enou.alpha.mapper.DictWordMapper;
import fun.enou.alpha.service.WordService;
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
import fun.enou.core.msg.EnouMessageException;
import fun.enou.core.redis.RedisManager;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.dynamic.scaffold.MethodGraph.Linked;

@Slf4j
@Service
public class WordServiceImpl implements WordService {

	@Autowired
	private DictWordMapper wordRepository;
	
	@Autowired
	private DictDefMapper dictDefMapper;

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
			Integer id = dictDefMapper.save(dictDef);
			resultDefList.add(id);
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
		
		List<Integer> defIdList = dbDictWord.getDefIdList();

		// these words should be ignored when loaded into redis, just in case it however occurs again.
		if(defIdList.size() == 0){
			log.error("getWebWordByDictWord Id {} word {} has no def.", dbDictWord.getId(), dbDictWord.getSpell());
			return new DtoWebWord(dbDictWord, new LinkedList<DtoDbDictDef>());
			//todo need collect these empty def words.
		}
		
		List<DtoDbDictDef> dictDefList =  dictDefMapper.findAllById(defIdList);
		
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
			String script = MessageFormat.format("return redis.call(''hrandfield'', ''{0}'', {1}, ''WITHVALUES'')", keyName, count);

			List<Object> keyValueList = (List<Object>)(jedis.eval(script));
			for(int i = 0; i< keyValueList.size(); i+=2){
				int id = Integer.parseInt(keyValueList.get(i).toString());
				String spell = keyValueList.get(i+1).toString();
				DtoDbDictWord dbDictWord = wordRepository.findById(id);
				if(dbDictWord == null) {
					log.warn("cannot find word in database id {} spell {}", id, spell);
					continue;
				}
			
				DtoWebWord word = getWebWordByDictWord(dbDictWord);
				resultList.add(word);
			}

		}
		
		return resultList;
	}

}
