package fun.enou.bot.qq.bot.challenge;

import fun.enou.bot.qq.bot.QQBot;
import fun.enou.feign.generated.auto_client.DtoWebWord;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


@Slf4j
public class WordChallenge {

	private static WordChallenge wordChallenge = new WordChallenge();

    public static WordChallenge instance() {
        return wordChallenge;
    }
	
	private Queue<OneChallenge> challengeList = new LinkedList<>();
	private int challengeCount = 10;
	private int wordCount = challengeCount * 4;
	private QQBot qqBot;
	
	public void prepare() {
		reset();
		getWordAndParseToChallenge();
	}

	public boolean isOver() {
		return challengeList.isEmpty();
	}

	public void setBot(QQBot bot) {
		qqBot = bot;
	}

	public OneChallenge getOneChallenge() {
		if(isOver()) prepare();
		return challengeList.poll();
	}

	private void getWordAndParseToChallenge() {
		// the word count may be less than expected value
		List<DtoWebWord> wordList = qqBot.getBotController().getRandomWord(wordCount);
		List<DtoWebWord> tmpListCount4 = new LinkedList<>();
		for(DtoWebWord webWord : wordList) {
			if(tmpListCount4.size() == 4)  {
				OneChallenge oneChallenge = OneChallenge.newInstance(tmpListCount4);
				challengeList.add(oneChallenge);
				tmpListCount4 = new LinkedList<>();
			}

			tmpListCount4.add(webWord);
		}
		if(tmpListCount4.size() > 1) {
			OneChallenge oneChallenge = OneChallenge.newInstance(tmpListCount4);
			challengeList.add(oneChallenge);
		}

		log.info("word count is {0}, chanllenge count is {1}", wordList.size(), challengeList.size());
	}
	
	private void reset() {
		challengeList.clear();
	}

}
