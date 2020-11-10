package fun.enou.bot.qq.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bot")
public class BotController {
	
	@GetMapping("/info")
	public String getBotInfo() {
		return "no bot info yet";
	}

}
