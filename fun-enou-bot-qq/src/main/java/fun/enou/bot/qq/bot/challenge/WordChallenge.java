package fun.enou.bot.qq.bot.challenge;

import fun.enou.bot.qq.bot.QQBot;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.github.dewxin.generated.auto_client.DtoWebWord;


@Slf4j
public class WordChallenge {

	private static WordChallenge wordChallenge = new WordChallenge();

    public static WordChallenge instance() {
        return wordChallenge;
	}
	
	private ExecutorService executor = Executors.newSingleThreadExecutor();
	
	private Queue<OneChallenge> challengeList = new LinkedList<>();
	private int challengeCount = 5;
	private int wordCount = challengeCount * 4;
	private QQBot qqBot;

	private Future<Boolean> prepareChallengeFuture = null;
	
	public Future<Boolean> prepare() {
		return executor.submit(()->{
			reset();
			getWordAndParseToChallenge();
			return true;
		});
	}

	public boolean isOver() {
		return challengeList.isEmpty();
	}

	public void setBot(QQBot bot) {
		qqBot = bot;
	}

	public OneChallenge getOneChallenge() {
		if(isOver()){
			if(prepareChallengeFuture == null) {
				prepareChallengeFuture = prepare();
			}

			try {
				prepareChallengeFuture.get();
			} catch (InterruptedException ex){
				log.warn("Interrupted!");
				Thread.currentThread().interrupt();
			} catch( ExecutionException ex) {
				for(StackTraceElement ele : ex.getStackTrace()) {
					log.warn(ele.toString());
				}
			}
		}

		OneChallenge oneChallenge = challengeList.poll();
		
		if(isOver()) {
			prepareChallengeFuture = prepare();
		}

		return oneChallenge;
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

		log.info("word count is {}, chanllenge count is {}", wordList.size(), challengeList.size());
	}
	
	private void reset() {
		challengeList.clear();
	}

}
