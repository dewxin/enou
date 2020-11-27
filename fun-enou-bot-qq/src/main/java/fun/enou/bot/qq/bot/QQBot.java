package fun.enou.bot.qq.bot;

import java.text.MessageFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import fun.enou.bot.qq.bot.challenge.WordChallenge;
import fun.enou.bot.qq.bot.listener.FriendEventListener;
import fun.enou.bot.qq.bot.listener.FriendMessageListener;
import fun.enou.bot.qq.bot.listener.GroupMessageListener;
import fun.enou.bot.qq.bot.state.IdleState;
import fun.enou.bot.qq.bot.state.BotState;
import fun.enou.bot.qq.bot.state.ChallengeState;
import fun.enou.bot.qq.config.BotProperty;
import fun.enou.bot.qq.controller.BotController;
import fun.enou.core.redis.RedisManager;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactoryJvm;
import net.mamoe.mirai.contact.ContactList;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.event.Events;
import net.mamoe.mirai.utils.BotConfiguration;
import net.mamoe.mirai.utils.BotConfiguration.MiraiProtocol;

@Component
@Slf4j
public class QQBot {

	private Bot bot;
	private Friend tmpUser;

	private BotState state;
	
	@Autowired
	private ApplicationContext context;
	@Autowired
	private RedisManager redisManager;

	@Autowired
	private BotProperty botProperty;
	
	@Autowired
	private BotController botController;
	
	public BotState getState() {
		return state;
	}

	public Bot getBot() {
		return bot;
	}

	public BotController getBotController() {
		return botController;
	}

	public void setBotController(BotController botController) {
		this.botController = botController;
	}

	public void init() {
		IdleState.instance().setBot(this);
		ChallengeState.instance().setBot(this);
		WordChallenge.instance().setBot(this);

		this.state = IdleState.instance();
	}

	public void createInstanceAndLogin() {

		MiraiProtocol protocol = MiraiProtocol.ANDROID_WATCH;
		if(botProperty.getProtocol().toLowerCase().contains("pad")) {
			protocol = MiraiProtocol.ANDROID_PAD;
		} else if(botProperty.getProtocol().toLowerCase().contains("phone")) {
			protocol = MiraiProtocol.ANDROID_PHONE;
		}

		BotConfiguration botConfiguration = new BotConfiguration() ;
		botConfiguration.setProtocol(protocol);
		botConfiguration.fileBasedDeviceInfo("deviceInfo.json");
		bot = BotFactoryJvm.newBot(botProperty.getAccount(), botProperty.getPassword(), botConfiguration);
		
		bot.login();
		log.info("{} bot login succeed", bot.getNick());
		redisManager.getJedis().set("bot", bot.getId() + bot.getNick());
	}
	
	public void registerEvents() {

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
	
	public void enterChallengeState() {
		enterState(ChallengeState.instance());
	}

	public void enterIdleState() {
		enterState(IdleState.instance());
	}

	private void enterState(BotState botState) {
		if(this.state != null)
			this.state.onExitState();

		this.state = botState;
		this.state.onEnterState();
	}
	
	public void sendMsgToAllGroups(String message) {
		if(isDevProfile()) {
			sendMsgToDevGroups(message);
			return;
		}

        ContactList<Group> groupList = getBot().getGroups();
        
        for(Group group: groupList) {
            group.sendMessage(message);
        }
	}

	private boolean isDevProfile() {
		String []profiles = context.getEnvironment().getActiveProfiles();

		for(String profile: profiles) {
			if(profile.equals("dev")) return true;
		}
		return false;
	}

	
	private void sendMsgToDevGroups(String message) {
        ContactList<Group> groupList = getBot().getGroups();
        
        for(Group group: groupList) {
			if(isDevGroup(group))
            	group.sendMessage(message);
        }
	}
	public boolean isDevGroup(Group group) {
		if(group.getId() == 1156821940)
			return true;
		return false;
	}
	
	public void mainLoop() {

		bot.join();
	}

}
