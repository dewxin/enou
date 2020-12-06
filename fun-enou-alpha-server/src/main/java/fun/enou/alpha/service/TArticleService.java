package fun.enou.alpha.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import fun.enou.alpha.dto.dtodb.DtoDbArticle;
import fun.enou.alpha.dto.dtoweb.DtoWebArticle;
import fun.enou.alpha.misc.SessionHolder;
import fun.enou.alpha.msg.MsgEnum;
import fun.enou.alpha.repository.ArticleRepository;
import fun.enou.alpha.repository.UserWordRepository;
import fun.enou.core.msg.EnouMessageException;
import fun.enou.core.redis.RedisManager;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

/**
 * @Author: nagi
 * @Description: args(webUser) function's parameter password will be encoded in
 *               aspect and passed to the function
 * @Date Created in 2020-09-20 17:18
 * @Modified By:
 */
@Service
@Slf4j
public class TArticleService implements IArticleService {

	@Autowired
	private ArticleRepository articleRepository;

	@Autowired
	private UserWordRepository userWordRepository;

	@Autowired
	private SessionHolder sessionHolder;

	@Autowired
	private RedisManager redisManager;

	@Override
	public DtoWebArticle saveArticle(DtoWebArticle webArticle) {
		DtoDbArticle dbArticle = webArticle.toDtoDb();

		DtoDbArticle resDb = articleRepository.save(dbArticle);

		DtoWebArticle resWeb = resDb.toDtoWeb();
		return resWeb;
	}

	public List<DtoWebArticle> getArticles(Pageable pageable) {

		List<DtoWebArticle> returnValue = new ArrayList<>();

		List<DtoDbArticle> dbArticleList = articleRepository.findAll(pageable).getContent();

		dbArticleList.forEach(dtoDbArticle -> {
			returnValue.add(dtoDbArticle.toDtoWeb());
		});

		return returnValue;
	}

	public List<DtoWebArticle> getArticles(int page, int size) {
		PageRequest pageRequest = PageRequest.of(page, size);
		List<DtoWebArticle> result = this.getArticles(pageRequest);
		return result;
	}

	@Override
	public List<String> parseUnknownWords(Long articleId) throws EnouMessageException {
		Long userId = sessionHolder.getUserId();
		Optional<DtoDbArticle> articleOptional = articleRepository.findById(articleId);
		if(!articleOptional.isPresent()){
			MsgEnum.ARTICLE_NOT_FOUND.ThrowException();
		}
		
		DtoDbArticle dtoDbArticle = articleOptional.get();
		List<String> knownWordList = getMasteredWordList(userId);

		String article = dtoDbArticle.getContent().replaceAll("[:,)(\'\"0-9.]| - ", "");
		String[] articleWordArray = article.toLowerCase().split(" ");
		HashSet<String> unknownWordSet = new HashSet<>();
		Collections.addAll(unknownWordSet, articleWordArray);

		log.info("known word count is {}", knownWordList.size());
		log.info("unknown word count before remove is {}", unknownWordSet.size());
		unknownWordSet.removeAll(knownWordList);
		log.info("unknown word count after remove is {}", unknownWordSet.size());

		return new ArrayList<>(unknownWordSet);
	}

	private List<String> getMasteredWordList(Long userId) {
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
