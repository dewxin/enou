package fun.enou.bot.qq.bot;

import java.text.MessageFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fun.enou.bot.qq.bot.listener.FriendEventListener;
import fun.enou.bot.qq.bot.listener.FriendMessageListener;
import fun.enou.bot.qq.bot.listener.GroupMessageListener;
import fun.enou.bot.qq.config.BotProperty;
import fun.enou.bot.qq.controller.BotController;
import fun.enou.core.redis.RedisManager;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactoryJvm;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.event.Events;
import net.mamoe.mirai.utils.BotConfiguration;

@Component
@Slf4j
public class QQBot {

	private Bot bot;
	private Friend tmpUser;
	
	@Autowired
	private RedisManager redisManager;

	@Autowired
	private BotProperty botProperty;
	
	@Autowired
	private BotController botController;

	public Bot getBot() {
		return bot;
	}

	public BotController getBotController() {
		return botController;
	}

	public void setBotController(BotController botController) {
		this.botController = botController;
	}

	public void createInstanceAndLogin() {


		bot = BotFactoryJvm.newBot(botProperty.getAccount(), botProperty.getPassword(), new BotConfiguration() {
			{
				MiraiProtocol protocol = MiraiProtocol.ANDROID_WATCH;
				if(botProperty.getProtocol().toLowerCase().contains("pad")) {
					protocol = MiraiProtocol.ANDROID_PAD;
				} else if(botProperty.getProtocol().toLowerCase().contains("phone")) {
					protocol = MiraiProtocol.ANDROID_PHONE;
				}
				// 设备缓存信息
				setProtocol(protocol);
				fileBasedDeviceInfo("deviceInfo.json");
			}
		});
		
		bot.login();
	}
	
	public void registerEvents() {

		log.info("{} bot login succeed", bot.getNick());
		redisManager.getJedis().set("bot", bot.getId() + bot.getNick());

		Events.registerEvents(bot, new GroupMessageListener(this));
		Events.registerEvents(bot, new FriendMessageListener());
		Events.registerEvents(bot, new FriendEventListener());
		tmpUser = bot.getFriend(botProperty.getTmpUser());
		tmpUser.sendMessage("bot is running");
		
	}
	
	public void sendEnouWordNotice() {
		String word = botController.getOneRandomWord();
		String message = MessageFormat.format("你知道{0}的意思吗，点击https://cn.bing.com/search?q={0}+define查看单词意思", word);
		tmpUser.sendMessage(message);
		tmpUser.sendMessage("快登录http://www.enou.fun复习一下单词吧。");
	}
	
	public void mainLoop() {

		bot.join();
//		Long lastMilliTime = 0l;
//		while (true) {
//
////			Long currentMilliTime = System.currentTimeMillis();
////			if(currentMilliTime - 5000> lastMilliTime) {
////				lastMilliTime = currentMilliTime;
////				bot.getFriend(botProperty.getTmpUser()).sendMessage("alive");
////			}
//			
//			try {
//				Thread.sleep(1);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
	}

}
