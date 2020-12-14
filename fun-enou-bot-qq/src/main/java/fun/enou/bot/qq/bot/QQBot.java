package fun.enou.bot.qq.bot;

import java.text.MessageFormat;
import java.util.HashMap;

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
import redis.clients.jedis.Jedis;

@Component
@Slf4j
public class QQBot {

	private Bot bot;
	private Friend tmpUser;

	private HashMap<Long, BotState> stateMap = new HashMap<>();
	
	@Autowired
	private ApplicationContext context;
	@Autowired
	private RedisManager redisManager;

	@Autowired
	private BotProperty botProperty;
	
	@Autowired
	private BotController botController;
	
	public BotState getGroupState(Long groupId) {
		if(!stateMap.containsKey(groupId)) {
			stateMap.put(groupId, IdleState.newInstance(this, groupId));
		}
		return stateMap.get(groupId);
	}

	public void setGroupState(Long groupId, BotState botState) {
		if(!stateMap.containsKey(groupId)) {
			stateMap.put(groupId, IdleState.newInstance(this, groupId));
			return;
		}

		stateMap.replace(groupId, botState);
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
		WordChallenge.instance().setBot(this);

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
		try(Jedis jedis = redisManager.getJedis()) {
			jedis.set("bot", bot.getId() + bot.getNick());
		}
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

	public void trySendGroupAdMessage() {
		for(BotState botState :stateMap.values()){
			if(botState instanceof IdleState) {
				IdleState idleState = (IdleState) botState;
				idleState.trySendAdSchedule();
			}
		}
	}
	
	public void enterChallengeState(Long groupId) {
		ChallengeState state = ChallengeState.newInstance(this, groupId);
		enterState(groupId, state);
	}

	public void enterIdleState(Long groupId) {
		IdleState state = IdleState.newInstance(this, groupId);
		enterState(groupId, state);
	}

	private void enterState(Long groupId, BotState botState) {
		BotState prevState = getGroupState(groupId);
		if(prevState.getState().equals(botState.getState())){
			return;
		}
		if(prevState != null){
			prevState.onExitState();
		}

		setGroupState(groupId, botState);

		botState.onEnterState();
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

	public void sendMsgToGroup(String message, Long groupId) {
		getBot().getGroup(groupId).sendMessage(message);
	}

	private boolean isDevProfile() {
		String []profiles = context.getEnvironment().getActiveProfiles();

		for(String profile: profiles) {
			if(profile.equals("dev")) return true;
		}
		return false;
	}

	
	public void sendMsgToDevGroups(String message) {
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
