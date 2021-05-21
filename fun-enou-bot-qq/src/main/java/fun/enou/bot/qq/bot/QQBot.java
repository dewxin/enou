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
import fun.enou.bot.qq.bot.state.GroupStates;
import fun.enou.bot.qq.config.BotProperty;
import fun.enou.bot.qq.controller.BotController;
import fun.enou.core.redis.RedisManager;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
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

	/* FIXME the mirai module we rely on has a real severe bug, will occupying the whole cpu and memory 
	   when the connection is reset by the remote end. */
	private Bot bot;
	private Friend tmpUser;

	//due to the lack of memory , this field needs to be reset sometimes.
	private HashMap<Long, GroupStates> groupIdToStateMap = new HashMap<>();
	
	@Autowired
	private ApplicationContext context;
	@Autowired
	private RedisManager redisManager;

	@Autowired
	private BotProperty botProperty;
	
	@Autowired
	private BotController botController;
	
	public GroupStates getGroupStates(Long groupId) {
		if(!groupIdToStateMap.containsKey(groupId)) {
			groupIdToStateMap.put(groupId, GroupStates.newInstance(this, groupId));
		}
		return groupIdToStateMap.get(groupId);
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
		WordChallenge.instance().prepare();
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
		bot = BotFactory.INSTANCE.newBot(botProperty.getAccount(), botProperty.getPassword(), botConfiguration);
		
		bot.login();
		log.info("{} bot login succeed", bot.getNick());
		try(Jedis jedis = redisManager.getJedis()) {
			jedis.set("bot", bot.getId() + bot.getNick());
		}
	}
	
	public void registerEvents() {

		bot.getEventChannel().registerListenerHost(new GroupMessageListener(this));
		bot.getEventChannel().registerListenerHost(new FriendMessageListener());
		bot.getEventChannel().registerListenerHost(new FriendEventListener());
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
		for(GroupStates groupStates :groupIdToStateMap.values()){
			groupStates.trySendGroupAdMessage();
		}
	}
	
	public void enterChallengeState(Long groupId) {
		GroupStates groupStates = getGroupStates(groupId);
		groupStates.enterChallengeState();
	}

	public void enterIdleState(Long groupId) {
		GroupStates groupStates = getGroupStates(groupId);
		groupStates.enterIdleState();
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
