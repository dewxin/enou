package samuragi.enou.service;

import org.springframework.data.domain.Pageable;
import samuragi.enou.dto.dtoweb.DtoWebArticle;

import java.util.List;

public interface IArticleService {
    DtoWebArticle saveArticle(DtoWebArticle webArticle);
    List<DtoWebArticle> getArticles(Pageable pageable);
    List<DtoWebArticle> getArticles(int page, int size);
}
