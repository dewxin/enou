package fun.enou.bot.qq.bot.listener;

import kotlin.coroutines.CoroutineContext;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.message.FriendMessageEvent;
import org.jetbrains.annotations.NotNull;

import fun.enou.bot.qq.bot.utils.CommonUtil;

public class FriendMessageListener extends SimpleListenerHost {
	@EventHandler
	public ListeningStatus onMessage(FriendMessageEvent event) throws Exception {
		
		event.getFriend().getId();

	    if(CommonUtil.randomYes()) {
			event.getSender().sendMessage(event.getMessage());
		}

		return ListeningStatus.LISTENING;
	}
	
	//处理事件处理时抛出的异常
	@Override
	public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception) {
	}
}
