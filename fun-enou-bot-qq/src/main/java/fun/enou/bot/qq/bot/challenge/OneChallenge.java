package fun.enou.bot.qq.bot.challenge;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.github.dewxin.generated.auto_client.DtoDbDictDef;
import com.github.dewxin.generated.auto_client.DtoWebWord;

import org.checkerframework.checker.units.qual.Prefix;

import fun.enou.core.tool.RandomUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OneChallenge {

	private final String PREFIX ="请选出符合定义的单词 Please choose the right option";
	private String definition;
	private String pos;
	
	private List<String> optionList = new LinkedList<>();
	
	private ArrayList<String> validAnswerList = new ArrayList<>(); // A B C D

	private String rightAnswer; // one of { A B C D }
	private DtoWebWord rightAnswerWord;
	private DtoDbDictDef answerDef;

	public boolean isTrueAnswer(String answer) {
		answer = answer.toUpperCase();
		return this.rightAnswer.equals(answer);
	}

	public boolean isValidAnswer(String answer) {
		answer = answer.toUpperCase();
		return this.validAnswerList.contains(answer);
	}

	public String getQuestion() {
		return String.join(", ", PREFIX, definition, pos);
	}

	public String getExplanation() {
		return MessageFormat.format(
			"正确答案是 The right answer is {0}, {1} {2}", rightAnswer, answerDef.getPhrase(), answerDef.getExample());
	}
	
	private void parse(List<DtoWebWord> wordList) {
		int index = RandomUtil.randomInt(wordList.size());
		for(int i = 0; i < wordList.size(); ++i) {
			String option = String.valueOf((char)('A'+i));
			validAnswerList.add(option);
			if(i == index) {
				rightAnswer = option;
				List<DtoDbDictDef> defList = wordList.get(i).getDefinitionList();
				if(defList == null || defList.isEmpty()) {
					log.warn("{} defList is empty", wordList.get(i).getSpell());
					continue;
				}
				int defIndex = RandomUtil.randomInt(defList.size());
				answerDef = defList.get(defIndex);
				definition = answerDef.getDef();
				pos = answerDef.getPos();
				rightAnswerWord = wordList.get(i);
			}
			
			optionList.add(option + ". " + wordList.get(i).getSpell());
		}
	}
	
	public static OneChallenge newInstance(List<DtoWebWord> wordList) {
		OneChallenge oneChallenge = new OneChallenge();
		List<DtoWebWord> refinedList = eliminateNoDefWord(wordList);
		oneChallenge.parse(refinedList);
		return oneChallenge;
	}

	private static List<DtoWebWord> eliminateNoDefWord(List<DtoWebWord> wordList) {
		List<DtoWebWord> refinedList = new ArrayList<>();
		for(DtoWebWord word: wordList) {
			List<DtoDbDictDef> defList = word.getDefinitionList();
			if(defList == null || defList.isEmpty()) {
				log.warn("word {} definition list is empty", word);
				continue;
			}
			refinedList.add(word);
		}
		return refinedList;
	}
	

	public String getDefinition() {
		return definition;
	}

	public void setDefinition(String definition) {
		this.definition = definition;
	}

	public List<String> getOptionList() {
		return optionList;
	}

	public void setOptionList(List<String> optionList) {
		this.optionList = optionList;
	}

	public String getAnswer() {
		return rightAnswer;
	}

	public void setAnswer(String answer) {
		this.rightAnswer = answer;
	}

	public DtoWebWord getRightAnswerWord() {
		return rightAnswerWord;
	}

	public void setRightAnswerWord(DtoWebWord rightAnswerWord) {
		this.rightAnswerWord = rightAnswerWord;
	}
	
}
