package fun.enou.alpha.mapper;

import fun.enou.alpha.dto.dtodb.DtoDbArticle;
import fun.enou.alpha.dto.dtodb.DtoDbDictDef;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

import java.util.List;

public interface ArticleMapper {

    @Insert("insert into article(title,content) values(#{title}, #{content})")
    @SelectKey(before=false, resultType = long.class, keyProperty = "id", statement = "select LAST_INSERT_ID ()")
    Long save(DtoDbArticle article);

    //todo how could the user know exactly the title?
    @Select("select * from article where title = #{title}")
    List<DtoDbArticle> findByTitle(String title);

    @Select("select * from article limit #{limit} offset #{offset}")
    List<DtoDbArticle> findAll(long limit, int offset);
}
