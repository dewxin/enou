package fun.enou.alpha.service.impl;

import java.util.ArrayList;
import java.util.List;

import fun.enou.alpha.mapper.ArticleMapper;
import fun.enou.alpha.service.ArticleService;
import fun.enou.alpha.service.UserWordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import fun.enou.alpha.dto.dtodb.DtoDbArticle;
import fun.enou.alpha.dto.dtoweb.DtoWebArticle;

/**
 * @Author: nagi
 * @Description: args(webUser) function's parameter password will be encoded in
 *               aspect and passed to the function
 * @Date Created in 2020-09-20 17:18
 * @Modified By:
 */
@Service
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	private ArticleMapper articleMapper;

	@Autowired
	private UserWordService userWordService;


	@Override
	public DtoWebArticle saveArticle(DtoWebArticle webArticle) {
		DtoDbArticle dbArticle = webArticle.toDtoDb();

		Long id = articleMapper.save(dbArticle);
		dbArticle.setId(id);

		DtoWebArticle resWeb = dbArticle.toDtoWeb();
		return resWeb;
	}

	public List<DtoWebArticle> getArticles(Pageable pageable) {

		List<DtoWebArticle> returnValue = new ArrayList<>();

		List<DtoDbArticle> dbArticleList = articleMapper.findAll(pageable.getOffset(), pageable.getPageSize());

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



}
