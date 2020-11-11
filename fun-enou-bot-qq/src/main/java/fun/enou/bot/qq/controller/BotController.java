package fun.enou.bot.qq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import fun.enou.core.msg.EnouMsgJson;

	//todo
	//info is stored in the redis, done by the bot thread
	//
@RestController
@RequestMapping("/bot")
public class BotController {
	
	@Autowired
	private RestTemplate restTemplate;
	

	@GetMapping("/info")
	public String getBotInfo() {
		return "no bot info yet";
	}
	
	@GetMapping("/word/random") 
	public String getOneRandomWord(){
    	EnouMsgJson msgJson = restTemplate.getForEntity("http://SERVICE-ALPHA/word/random", EnouMsgJson.class).getBody();
    	return msgJson.getData().toString(); 
	}
	
	

}
