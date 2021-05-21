package fun.enou.bot.qq.bot.listener;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fun.enou.bot.qq.bot.QQBot;
import fun.enou.bot.qq.controller.BotController;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.GroupMessageEvent;

public class GroupMessageListener extends SimpleListenerHost {

	private QQBot qqBot;
	public GroupMessageListener() {
	}

	public GroupMessageListener(QQBot qqBot) {
		this.qqBot = qqBot;
	}

	@EventHandler
	public ListeningStatus onMessage(GroupMessageEvent event)  {

		Long groupId = event.getGroup().getId();
		return qqBot.getGroupStates(groupId).handleGroupMessage(event);

	}


}
