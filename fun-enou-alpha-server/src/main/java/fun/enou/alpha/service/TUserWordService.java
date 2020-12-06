package fun.enou.alpha.service;

import com.netflix.discovery.converters.Auto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fun.enou.alpha.dto.dtodb.DtoDbDictWord;
import fun.enou.alpha.dto.dtodb.ext.DtoDbUserWord;
import fun.enou.alpha.misc.SessionHolder;
import fun.enou.alpha.msg.MsgEnum;
import fun.enou.alpha.repository.DictWordRepository;
import fun.enou.alpha.repository.UserWordRepository;
import fun.enou.core.msg.EnouMessageException;

@Service
public class TUserWordService implements IUserWordService {

    @Autowired
    private DictWordRepository dictWordRepository;

    @Autowired
    private UserWordRepository userWordRepository;

    @Autowired
    private SessionHolder sessionHolder;

    @Override
    public void learnWord(String spell) throws EnouMessageException {
        DtoDbDictWord dbDictWord = dictWordRepository.findBySpell(spell);
        if(dbDictWord == null) {
            MsgEnum.WORD_NOT_FOUND.ThrowException();
        }

        Long userId = sessionHolder.getUserId();
        DtoDbUserWord dbUserWord = new DtoDbUserWord();
        dbUserWord.setUserId(userId);
        dbUserWord.setWordId(dbDictWord.getId());
        userWordRepository.save(dbUserWord);

    }

    
}
