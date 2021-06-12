package fun.enou.alpha.dto.dtodb;

import fun.enou.alpha.dto.dtoweb.DtoWebUserWord;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Timestamp;

/**
 * @Author: nagi
 * @Modified By:
 * @Date Created in 2020-09-22 18:10
 * @Description:
 */

@Table("user_word")
public class DtoDbOldUserWord {

    @Id
    private Long id;

    @Column("user_id")
    private Long userId;

    private String word;

    @Column("query_time")
    private int queryTime;

    @Column("created_at")
    private Timestamp createdAt;

    public DtoDbOldUserWord() {
    }

    public DtoDbOldUserWord(Long id, Long userId, String word, int queryTime, Timestamp createdAt) {
        this.id = id;
        this.userId = userId;
        this.word = word;
        this.queryTime = queryTime;
        this.createdAt = createdAt;
    }

    public DtoWebUserWord toDtoWeb() {
        return new DtoWebUserWord(id, word);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getQueryTime() {
        return queryTime;
    }

    public void setQueryTime(int queryTime) {
        this.queryTime = queryTime;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
