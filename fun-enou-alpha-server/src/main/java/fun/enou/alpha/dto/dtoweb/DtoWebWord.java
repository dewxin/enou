package fun.enou.alpha.dto.dtoweb;

import java.util.LinkedList;
import java.util.List;

import fun.enou.alpha.dto.dtodb.DtoDbDictDef;
import fun.enou.alpha.dto.dtodb.DtoDbDictWord;

public class DtoWebWord {
	
	private Integer id;

	private String spell;
	
	private String pronounce;
	
	private String usPronounce;
	
	private String[] defList;
	
	private String[] chDefList;

	public DtoWebWord() {}
	
	public DtoWebWord(DtoDbDictWord dbDictWord, Iterable<DtoDbDictDef> dbDictDef) {
		this.id = dbDictWord.getId();
		this.spell = dbDictWord.getSpell();
		this.pronounce = dbDictWord.getPronounce();
		this.usPronounce = dbDictWord.getUsPronounce();
		
		List<String> defList = new LinkedList<>(); 
		List<String> chDefList = new LinkedList<>();
		for(DtoDbDictDef dictDef : dbDictDef) {
			defList.add(dictDef.getDef());
			chDefList.add(dictDef.getChDef());
		}
		
		this.defList = defList.toArray(new String[defList.size()]);
		this.chDefList = chDefList.toArray(new String[chDefList.size()]);
	}
	
	public DtoDbDictWord toDtoDbWord() {
		DtoDbDictWord word = new DtoDbDictWord();
		word.setPronounce(pronounce);
		word.setSpell(spell);
		word.setUsPronounce(usPronounce);
		return word;
	}
	
	public List<DtoDbDictDef> toDtoDbDef() {
		List<DtoDbDictDef> dbDefList = new LinkedList<>();
		for(int i = 0; i < defList.length; ++i) {
			DtoDbDictDef dbDictDef = new DtoDbDictDef();
			dbDictDef.setDef(defList[i]);
			dbDictDef.setChDef(chDefList[i]);
			dbDefList.add(dbDictDef);
		}
		return dbDefList;
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

	public String[] getDefList() {
		return defList;
	}

	public void setDefList(String[] defList) {
		this.defList = defList;
	}

	public String[] getChDefList() {
		return chDefList;
	}

	public void setChDefList(String[] chDefList) {
		this.chDefList = chDefList;
	}


	
}
