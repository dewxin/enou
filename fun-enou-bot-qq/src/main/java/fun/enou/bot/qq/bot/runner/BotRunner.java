package fun.enou.bot.qq.bot.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import fun.enou.bot.qq.bot.QQBot;
import fun.enou.bot.qq.controller.BotController;
import lombok.extern.slf4j.Slf4j;


@Component
@Slf4j
public class BotRunner implements CommandLineRunner {

	@Autowired
	private QQBot bot;
	

	@Autowired
	RestTemplate restTemplate;


	@Override
	public void run(String... args) throws Exception {

		Thread thread = new Thread(() -> {

			bot.createInstanceAndLogin();
			bot.registerEvents();
			bot.mainLoop();
		});
		thread.start();

	}
}
