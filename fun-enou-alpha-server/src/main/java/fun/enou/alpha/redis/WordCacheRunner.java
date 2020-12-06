package fun.enou.alpha.redis;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import fun.enou.alpha.dto.dtodb.DtoDbDictWord;
import fun.enou.alpha.repository.DictWordRepository;
import fun.enou.core.redis.RedisManager;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

@Component
@Slf4j
public class WordCacheRunner implements CommandLineRunner{

    @Autowired
    private RedisManager redisManager;

    @Autowired
    private DictWordRepository wordRepository;

    @Override
    public void run(String... args) throws Exception {
        try(Jedis jedis = redisManager.getJedis()) {
            Long redisWordCount = jedis.hlen("wordIdToSpell");

            Long mysqlWordCount = wordRepository.count();
            if(redisWordCount >= mysqlWordCount){
                return;
            }

            log.info("redis word count < mysql word count, loading all the word to redis");

            Iterable<DtoDbDictWord> wordCollection = wordRepository.findAll();

            List<DtoDbDictWord> wordList = new LinkedList<>();
            for(DtoDbDictWord word : wordCollection) {
                wordList.add(word);
            }

            for(DtoDbDictWord word : wordList) {
                String wordId = word.getId().toString();
                String wordSpell = word.getSpell();
                jedis.hset("wordIdToSpell", wordId, wordSpell);
            }
            
            log.info("all words is loaded to redis");
        }

    }

    
}
