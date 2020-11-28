package fun.enou.bot.qq.bot.state;

import java.util.List;

import fun.enou.bot.qq.bot.QQBot;
import fun.enou.bot.qq.bot.utils.CommonUtil;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.message.GroupMessageEvent;

/**
 * @Author: nagi
 * @Modified By:
 * @Date Created in 2020-10-08 21:09
 * @Description:
 * @Attention:
 */
public class IdleState extends BotState {

    private String botLastSendMessage = "";
    private String lastMessage = "";
    private double ratePercent = 0;


    public static IdleState newInstance(QQBot bot, Long groupId) {
        IdleState state = new IdleState();
        state.setBot(bot);
        state.setGroupId(groupId);
        return state;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getBotLastSentMessage() {
        return botLastSendMessage;
    }

    public void setBotLastSentMessage(String lastBotSendMessage) {
        botLastSendMessage = lastBotSendMessage;
    }

    public double getRatePercent() {
        return ratePercent;
    }

    public void setRatePercent(double ratePercent) {
        this.ratePercent = ratePercent;
    }

    @Override
    public ListeningStatus handleGroupMessage(GroupMessageEvent event) {

        if (event.getMessage().toString().matches(".*\\[mirai:at:.*\\].*")) {
			return ListeningStatus.LISTENING;
		}
		
		if(event.getMessage().toString().matches(".*\\[mirai:image:.*\\].*")) {
			return ListeningStatus.LISTENING;
        }
        
        String content = event.getMessage().contentToString();
        if (getLastMessage().equals(content) && !getBotLastSentMessage().equals(content)) {
            setRatePercent(getRatePercent() + 0.1);
        } else {
            setRatePercent(0);
            setLastMessage(content);
        }

        if (CommonUtil.randomYes(getRatePercent())) {
            setBotLastSentMessage(getLastMessage());
            event.getGroup().sendMessage(getLastMessage());
            setRatePercent(0);
        }

        if (content.toLowerCase().startsWith("getdef")) {
            String word = content.split(" ")[1];
            String result = "";
            try{
                result = qqBot.getBotController().getWordDef(word);
            } catch (Exception exception) {
                result = "找不到单词，或者遇到异常，解析失败" ;
            } finally {
                event.getGroup().sendMessage(result);
            }
        }

        if(content.startsWith("出题")) {
            qqBot.enterChallengeState(groupId);
        }


        return ListeningStatus.LISTENING;
    }

    @Override
    public void onEnterState() {
        // qqBot.sendMsgToAllGroups("机器人进入Idle状态");
    }

    @Override
    public void onExitState() {
        // qqBot.sendMsgToAllGroups("机器人退出Idle状态");
    }



}
