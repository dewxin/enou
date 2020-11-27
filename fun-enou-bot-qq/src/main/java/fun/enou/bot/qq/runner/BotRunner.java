package fun.enou.bot.qq.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import fun.enou.bot.qq.bot.QQBot;


@Component
public class BotRunner implements CommandLineRunner {

	@Autowired
	private QQBot bot;
	

	@Autowired
	RestTemplate restTemplate;


	@Override
	public void run(String... args) throws Exception {

		Thread thread = new Thread(() -> {

			bot.init();
			bot.createInstanceAndLogin();
			bot.registerEvents();
			bot.mainLoop();
		});
		thread.start();

	}
}
