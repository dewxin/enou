package fun.enou.alpha.dto.dtodb;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("oxford_dict_word")
public class DtoDbDictWord {
	@Id
	private Integer id;
	
	private String spell;
	
	@Column("pronounce")
	private String pronounce;
	
	@Column("us_pronounce")
	private String usPronounce;
	
	@Column("def_id")
	private String defId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSpell() {
		return spell;
	}

	public void setSpell(String spell) {
		this.spell = spell;
	}

	public String getPronounce() {
		return pronounce;
	}

	public void setPronounce(String pronounce) {
		this.pronounce = pronounce;
	}

	public String getUsPronounce() {
		return usPronounce;
	}

	public void setUsPronounce(String usPronounce) {
		this.usPronounce = usPronounce;
	}

	public String getDefId() {
		return defId;
	}

	public void setDefId(String defId) {
		this.defId = defId;
	}
	
	

}
