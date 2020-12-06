package fun.enou.alpha.service;

import fun.enou.core.msg.EnouMessageException;

public interface IUserWordService {
    void learnWord(String spell)  throws EnouMessageException ;
}
