package fun.enou.bot.qq.bot.listener;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fun.enou.bot.qq.bot.QQBot;
import fun.enou.bot.qq.bot.state.IdleState;
import fun.enou.bot.qq.bot.utils.CommonUtil;
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
	public ListeningStatus onMessage(GroupMessageEvent event)  {

		String content = event.getMessage().contentToString();
		if(content.toLowerCase().startsWith("enterstate") && qqBot.isDevGroup(event.getGroup())) {
			String targetState = content.toLowerCase().split(" ")[1];
			if(targetState.equals("idle")){
				qqBot.enterIdleState();
			} else if(targetState.equals("challenge")) {
				qqBot.enterChallengeState();
			}
		}

		return qqBot.getState().handleGroupMessage(event);

	}


	//处理事件处理时抛出的异常
	@Override
	public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception) {
	}
}
