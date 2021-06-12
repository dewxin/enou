package fun.enou.alpha.redis;

import java.util.LinkedList;
import java.util.List;

import fun.enou.alpha.mapper.DictWordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import fun.enou.alpha.dto.dtodb.DtoDbDictWord;
import fun.enou.core.redis.RedisManager;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

@Component
@Slf4j
public class WordCacheRunner implements CommandLineRunner{

    @Autowired
    private RedisManager redisManager;

    @Autowired
    private DictWordMapper wordRepository;

    @Override
    public void run(String... args) throws Exception {
        try(Jedis jedis = redisManager.getJedis()) {
            Long redisWordCount = jedis.hlen("wordIdToSpell");

            Long mysqlWordCount = wordRepository.count();
            if(redisWordCount >= mysqlWordCount){
                return;
            }

            log.info("redis word count < mysql word count, loading all the word to redis");

            List<DtoDbDictWord> wordList = wordRepository.findAll();

            for(DtoDbDictWord word : wordList) {
                String wordId = word.getId().toString();
                String wordSpell = word.getSpell();
                jedis.hset("wordIdToSpell", wordId, wordSpell);
            }
            
            log.info("all words is loaded to redis");
        }

    }

    
}
