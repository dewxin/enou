package fun.enou.alpha.dto.dtoweb;

import java.util.LinkedList;
import java.util.List;

import com.google.common.collect.Lists;

import fun.enou.alpha.dto.dtodb.DtoDbDictDef;
import fun.enou.alpha.dto.dtodb.DtoDbDictWord;
import fun.enou.alpha.dto.dtodb.DtoDbDictDef;

public class DtoWebWord {
	
	private Integer id;

	private String spell;
	
	private String pronounce;
	
	private String usPronounce;
	
	private List<DtoDbDictDef> definitionList = new LinkedList<DtoDbDictDef>();

	public DtoWebWord() {}
	
	public DtoWebWord(DtoDbDictWord dbDictWord, Iterable<DtoDbDictDef> dbDictDef) {
		this.id = dbDictWord.getId();
		this.spell = dbDictWord.getSpell();
		this.pronounce = dbDictWord.getPronounce();
		this.usPronounce = dbDictWord.getUsPronounce();
		dbDictDef.forEach(def->{definitionList.add(def);});
		
	}
	
	public DtoDbDictWord toDtoDbWord() {
		DtoDbDictWord word = new DtoDbDictWord();
		word.setPronounce(pronounce);
		word.setSpell(spell);
		word.setUsPronounce(usPronounce);
		return word;
	}
	
	public List<DtoDbDictDef> toDtoDbDef() {

		return definitionList;
	}

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

	public List<DtoDbDictDef> getDefinitionList() {
		return definitionList;
	}

	public void setDefinitionList(List<DtoDbDictDef> definitionList) {
		this.definitionList = definitionList;
	}

	
}
