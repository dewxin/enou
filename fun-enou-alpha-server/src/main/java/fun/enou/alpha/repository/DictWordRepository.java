package fun.enou.alpha.repository;

import org.springframework.data.repository.CrudRepository;

import fun.enou.alpha.dto.dtodb.DtoDbDictWord;

public interface DictWordRepository extends CrudRepository<DtoDbDictWord, Integer> {
	
	DtoDbDictWord findBySpell(String spell);

}
