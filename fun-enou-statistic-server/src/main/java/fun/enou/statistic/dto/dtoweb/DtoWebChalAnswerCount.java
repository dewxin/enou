package fun.enou.statistic.dto.dtoweb;

import javax.validation.constraints.NotNull;

import fun.enou.statistic.dto.dtodb.DtoDbChalAnswerCount;

public class DtoWebChalAnswerCount {

    @NotNull
    private Integer id;
    @NotNull
    private String wordSpell;
    @NotNull
    private Integer rightOptionCount;
    @NotNull
    private Integer falseOptionCount;

    public DtoWebChalAnswerCount(Integer id, String wordSpell, Integer rightOptionCount, Integer falseOptionCount) {
		this.id = id;
		this.wordSpell = wordSpell;
		this.rightOptionCount = rightOptionCount;
		this.falseOptionCount = falseOptionCount;
	}

    public DtoDbChalAnswerCount toDb() {
        return new DtoDbChalAnswerCount(id, wordSpell, rightOptionCount, falseOptionCount);
    }

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
    
    @Override
    public String toString() {
        return "DtoWebChalAnswerCount [falseOptionCount=" + falseOptionCount + ", rightOptionCount=" + rightOptionCount
                + ", wordSpell=" + wordSpell + "]";
    }


}
