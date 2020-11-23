package fun.enou.bot.qq.bot.listener;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fun.enou.bot.qq.bot.QQBot;
import fun.enou.bot.qq.bot.utils.CommonUtil;
import fun.enou.bot.qq.bot.utils.State;
import fun.enou.bot.qq.controller.BotController;
import kotlin.coroutines.CoroutineContext;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.message.GroupMessageEvent;

@Component
public class GroupMessageListener extends SimpleListenerHost {

	//todo 注入失败， bug
	@Autowired
	BotController botController;
	
	
	private QQBot qqBot;
	public GroupMessageListener() {
	}

	public GroupMessageListener(QQBot qqBot) {
		this.qqBot = qqBot;
	}

	@EventHandler
	public ListeningStatus onMessage(GroupMessageEvent event) throws Exception {

		if (event.getMessage().toString().matches(".*\\[mirai:at:.*\\].*")) {
			return ListeningStatus.LISTENING;
		}
		
		if(event.getMessage().toString().matches(".*\\[mirai:image:.*\\].*")) {
			return ListeningStatus.LISTENING;
		}


		String content = event.getMessage().contentToString();
		if(State.getLastMessage().equals(content) && !State.getBotLastSentMessage().equals(content)) {
		    State.setRatePercent(State.getRatePercent() + 0.1);
		} else {
			State.setRatePercent(0);
			State.setLastMessage(content);
		}


		if(CommonUtil.randomYes(State.getRatePercent())) {
			State.setBotLastSentMessage(State.getLastMessage());
			event.getGroup().sendMessage(State.getLastMessage());
			State.setRatePercent(0);
		}
		
		if(content.startsWith("getDef")) {
			String word = content.split(" ")[1];
			String def = qqBot.getBotController().getWordDef(word);
			event.getGroup().sendMessage(def);
		}


		return ListeningStatus.LISTENING;
	}


	//处理事件处理时抛出的异常
	@Override
	public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception) {
	}
}
