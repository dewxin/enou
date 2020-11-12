package fun.enou.bot.qq.misc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import fun.enou.bot.qq.bot.QQBot;

@Component
public class SheduleTask {
	
	@Autowired
	private QQBot bot;

    @Scheduled(cron = "0 0 12 * * ?")
    public void botSendNotcie() {
    	bot.sendEnouWordNotice();
    }
}
