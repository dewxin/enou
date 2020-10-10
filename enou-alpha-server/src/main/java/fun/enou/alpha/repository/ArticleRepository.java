package fun.enou.alpha.repository;

import fun.enou.alpha.dto.dtodb.DtoDbArticle;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ArticleRepository extends PagingAndSortingRepository<DtoDbArticle, Long> {
    List<DtoDbArticle> findByTitle(String title);
}
