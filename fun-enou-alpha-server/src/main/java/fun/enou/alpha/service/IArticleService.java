package fun.enou.alpha.service;

import fun.enou.alpha.dto.dtoweb.DtoWebArticle;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IArticleService {
    DtoWebArticle saveArticle(DtoWebArticle webArticle);
    List<DtoWebArticle> getArticles(Pageable pageable);
    List<DtoWebArticle> getArticles(int page, int size);
}
