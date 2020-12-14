package fun.enou.bot.qq.bot.challenge;

import java.text.MessageFormat;
import java.util.LinkedList;
import java.util.List;

import com.github.dewxin.generated.auto_client.DtoDbDictDef;
import com.github.dewxin.generated.auto_client.DtoWebWord;

import fun.enou.core.tool.RandomUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OneChallenge {

	private final String PREFIX ="请选出符合定义的单词，Please choose the right option";
	private String definition;
	
	private List<String> optionList = new LinkedList<>();
	
	private String answer; // A B C D
	private DtoDbDictDef answerDef;

	public boolean isTrueAnswer(String answer) {
		return this.answer.equals(answer);
	}

	public String getQuestion() {
		return PREFIX + definition;
	}

	public String getExplanation() {
		return MessageFormat.format(
			"正确答案是 The right answer is{0}, {1} {2}", answer, answerDef.getPhrase(), answerDef.getExample());
	}
	
	private void parse(List<DtoWebWord> wordList) {
		int index = RandomUtil.randomInt(wordList.size());
		for(int i = 0; i < wordList.size(); ++i) {
			String option = String.valueOf((char)('A'+i));
			if(i == index) {
				answer = option;
				List<DtoDbDictDef> defList = wordList.get(i).getDefinitionList();
				if(defList.isEmpty()) {
					log.warn("{0} defList is empty", wordList.get(i).getSpell());
					continue;
				}
				int defIndex = RandomUtil.randomInt(defList.size());
				answerDef = defList.get(defIndex);
				definition = answerDef.getDef();
			}
			
			optionList.add(option + ". " + wordList.get(i).getSpell());
		}
	}
	
	public static OneChallenge newInstance(List<DtoWebWord> wordList) {
		OneChallenge oneChallenge = new OneChallenge();
		oneChallenge.parse(wordList);
		return oneChallenge;
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
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	
	
}
