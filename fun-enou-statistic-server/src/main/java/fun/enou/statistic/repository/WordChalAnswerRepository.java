package fun.enou.statistic.repository;

import org.springframework.data.repository.CrudRepository;

import fun.enou.statistic.dto.dtodb.DtoDbChalAnswerCount;
import fun.enou.statistic.repository.ext.WordChalAnswerExt;

public interface WordChalAnswerRepository extends CrudRepository<DtoDbChalAnswerCount, Integer>, WordChalAnswerExt{
}
