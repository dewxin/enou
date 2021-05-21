package fun.enou.bot.qq.bot.listener;

import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.FriendMessageEvent;


public class FriendMessageListener extends SimpleListenerHost {
	@EventHandler
	public ListeningStatus onMessage(FriendMessageEvent event) {
		
//		event.getFriend().getId();
//
//	    if(CommonUtil.randomYes()) {
//			event.getSender().sendMessage(event.getMessage());
//		}
		if(event.getMessage().contentToString().contains("checkState")){
			event.getSender().sendMessage("alive");
		}

		return ListeningStatus.LISTENING;
	}
	

}
