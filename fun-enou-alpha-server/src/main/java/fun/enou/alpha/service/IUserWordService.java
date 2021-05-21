package fun.enou.alpha.service;

import java.util.List;

import fun.enou.core.msg.EnouMessageException;

public interface IUserWordService {
    void learnWord(String spell)  throws EnouMessageException ;
    List<String> getUnknownWords(String statement);
}
