package samuragi.enou.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import samuragi.enou.dto.dtodb.DtoDbArticle;
import samuragi.enou.dto.dtoweb.DtoWebArticle;
import samuragi.enou.repository.ArticleRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author:  nagi
 * @Description: args(webUser) function's parameter password will be encoded in aspect and passed to the function
 * @Date Created in 2020-09-20 17:18
 * @Modified By:
 */
@Service
public class TArticleService implements IArticleService {

    @Autowired
    ArticleRepository articleRepository;

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

        dbArticleList.forEach( dtoDbArticle -> {returnValue.add(dtoDbArticle.toDtoWeb());});

        return returnValue;
    }

    public List<DtoWebArticle> getArticles(int page, int size) {
        PageRequest pageRequest= PageRequest.of(page, size);
        List<DtoWebArticle> result = this.getArticles(pageRequest);
        return result;
    }
}
