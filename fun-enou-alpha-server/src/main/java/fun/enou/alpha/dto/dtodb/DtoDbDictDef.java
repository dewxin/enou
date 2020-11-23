package fun.enou.alpha.dto.dtodb;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("oxford_dict_def")
public class DtoDbDictDef {

	@Id
	private Integer id;
	
	private String def;
	
	@Column("ch_def")
	private String chDef;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDef() {
		return def;
	}

	public void setDef(String def) {
		this.def = def;
	}

	public String getChDef() {
		return chDef;
	}

	public void setChDef(String chDef) {
		this.chDef = chDef;
	}
	
	
	
}