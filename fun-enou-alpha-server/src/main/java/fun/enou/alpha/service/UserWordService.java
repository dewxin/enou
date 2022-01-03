package fun.enou.alpha.service;

import java.util.List;

import fun.enou.core.msg.EnouMessageException;

public interface UserWordService {
    int getWordCount();
    void learnWord(String spell)  throws EnouMessageException;
    List<String> getAllKnownWordsAfter(Long timeStamp, int offset, int count);
    List<String> getKnownWords(int offset, int count);
}
