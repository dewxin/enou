package fun.enou.alpha.mapper;

import fun.enou.alpha.dto.dtodb.DtoDbDictDef;
import fun.enou.alpha.dto.dtodb.DtoDbDictWord;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

import java.util.List;

public interface DictWordMapper {

    @Select("select * from oxford_dict_word where spell = #{spell}")
    DtoDbDictWord findBySpell(String spell);

    @Select("select count(*) from oxford_dict_word")
    long count();

    @Select("select * from oxford_dict_word where id = #{id}")
    DtoDbDictWord findById(Integer id);

    @Select("select * from oxford_dict_word")
    List<DtoDbDictWord> findAll();

    @Insert("insert into oxford_dict_word(spell,pronounce,us_pronounce,def_id) values(#{spell}, #{pronounce}, #{usPronounce}, #{defId})")
    @SelectKey(before=false, resultType = int.class, keyProperty = "id", statement = "select LAST_INSERT_ID ()")
    Integer save(DtoDbDictWord word);
}
