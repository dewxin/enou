package fun.enou.alpha.service;

import fun.enou.alpha.dto.dtoweb.DtoWebWord;
import fun.enou.core.msg.EnouMessageException;

public interface IWordService {

	void uploadWord(DtoWebWord webWord);
	
	DtoWebWord getWebWord(String webWord) throws EnouMessageException;
}
