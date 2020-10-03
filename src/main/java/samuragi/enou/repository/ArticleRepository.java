package samuragi.enou.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import samuragi.enou.dto.dtodb.DtoDbArticle;

import java.util.List;

public interface ArticleRepository extends PagingAndSortingRepository<DtoDbArticle, Long> {
    List<DtoDbArticle> findByTitle(String title);
}
