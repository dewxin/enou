package fun.enou.bot.qq.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dewxin.generated.auto_client.AlphaClient;
import com.github.dewxin.generated.auto_client.DtoWebWord;

import fun.enou.core.msg.EnouMsgJson;

@RestController
@RequestMapping("/bot")
public class BotController {
	
	@Autowired
	private AlphaClient alphaClient;


	@GetMapping("/word/random") 
	public String getOneRandomWord(){

    	EnouMsgJson<String> strEntity = alphaClient.getOneRandomWord();

    	return strEntity.getData();
	}
	
	@GetMapping("/word/{word}/def")
	public String getWordDef(@PathVariable String word) {
		DtoWebWord webWord = alphaClient.getWordInfo(word).getData();
		
		ObjectMapper objectMapper = new ObjectMapper();
		String def = "";
		try {
			def = objectMapper.writeValueAsString(webWord);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return def;
		
	}
	
	@GetMapping("/word")
	public String getWordPronounce(@RequestParam String word) {
		
		DtoWebWord webWord = alphaClient.getWord(word).getData();
		
		ObjectMapper objectMapper = new ObjectMapper();
		String def = "";
		try {
			def = objectMapper.writeValueAsString(webWord);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return def;
	}
	
	public List<DtoWebWord> getRandomWord(Integer count) {
		
		return  alphaClient.getRandomWord(count).getData().getDtoWebWordList();
	}
	
	
}
