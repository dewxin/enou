package fun.enou.bot.qq.bot.listener;


import org.jetbrains.annotations.NotNull;

import kotlin.coroutines.CoroutineContext;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.FriendAddEvent;
import net.mamoe.mirai.event.events.NewFriendRequestEvent;

public class FriendEventListener extends SimpleListenerHost {
	//自动同意加好友
	@EventHandler
	public ListeningStatus onAddingFriend(NewFriendRequestEvent event) {
		event.accept();
		return ListeningStatus.LISTENING;
	}
	
	//加好友后自动消息
	@EventHandler
	public ListeningStatus onAddFriend(FriendAddEvent event) {
		event.getFriend().sendMessage(
				String.format("很高兴认识你，{}。",event.getFriend().getNick())
		);
		return ListeningStatus.LISTENING;
	}
	
	//处理事件处理时抛出的异常
	@Override
	public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception) {
	}
}
