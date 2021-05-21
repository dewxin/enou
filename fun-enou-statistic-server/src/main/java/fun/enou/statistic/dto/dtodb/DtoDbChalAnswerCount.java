package fun.enou.statistic.dto.dtodb;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import fun.enou.statistic.dto.dtoweb.DtoWebChalAnswerCount;

@Table("word_challenge_answer")
public class DtoDbChalAnswerCount {
    @Id
    private Integer id;
    @Column("spell")
    private String wordSpell;
    @Column("correct_count")
    private Integer rightOptionCount;
    @Column("wrong_count")
    private Integer falseOptionCount;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getWordSpell() {
		return wordSpell;
	}
	public void setWordSpell(String wordSpell) {
		this.wordSpell = wordSpell;
	}
	public Integer getRightOptionCount() {
		return rightOptionCount;
	}
	public void setRightOptionCount(Integer rightOptionCount) {
		this.rightOptionCount = rightOptionCount;
	}
	public Integer getFalseOptionCount() {
		return falseOptionCount;
	}
	public void setFalseOptionCount(Integer falseOptionCount) {
		this.falseOptionCount = falseOptionCount;
	}
	public DtoDbChalAnswerCount(Integer id, String wordSpell, Integer rightOptionCount, Integer falseOptionCount) {
		this.id = id;
		this.wordSpell = wordSpell;
		this.rightOptionCount = rightOptionCount;
		this.falseOptionCount = falseOptionCount;
	}

    public DtoWebChalAnswerCount toWeb() {
        return new DtoWebChalAnswerCount(id, wordSpell, rightOptionCount, falseOptionCount);
    }
    
}
