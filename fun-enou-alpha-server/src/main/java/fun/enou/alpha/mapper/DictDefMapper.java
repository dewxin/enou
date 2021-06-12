package fun.enou.alpha.mapper;

import fun.enou.alpha.dto.dtodb.DtoDbDictDef;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectKey;

import java.util.List;

public interface DictDefMapper {

    @Insert("insert into oxford_dict_def(def,pos,phrase,example) values(#{def}, #{pos}, #{phrase}, #{example})")
    @SelectKey(before=false, resultType = int.class, keyProperty = "id", statement = "select LAST_INSERT_ID ()")
    Integer save(DtoDbDictDef dictDef);


    List<DtoDbDictDef> findAllById(@Param("defIdList") List<Integer> defIdList);
}
