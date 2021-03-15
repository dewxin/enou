package fun.enou.bot.qq.bot.state;

import java.util.List;

import fun.enou.bot.qq.bot.QQBot;
import fun.enou.bot.qq.bot.utils.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.event.Event;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.message.GroupMessageEvent;

/**
 * @Author: nagi
 * @Modified By:
 * @Date Created in 2020-10-08 21:09
 * @Description:
 * @Attention:
 */
@Slf4j
public class IdleState extends GroupState {

    public static final String STATE_STR = "idle";

    //repeat message
    private String botLastSendMessage = "";
    private String lastMessage = "";
    private double repeatRatePercent = 0;

    //ad
    private final String adMessage = "发送“出题”两字，即可测试词汇量。 send 'ask', you can test wheather your IQ above average.";
    private double sendAdRate = 0;
    private long lastTimeSendAdOrEnterChalState = 0;
    private long hourInterval = 24 * 7;

    
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
        return repeatRatePercent;
    }

    public void setRatePercent(double ratePercent) {
        this.repeatRatePercent = ratePercent;
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
        handleRepeate(content, event);


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

        if(content.startsWith("出题") || content.startsWith("ask")) {
            qqBot.enterChallengeState(groupId);
        }

        return ListeningStatus.LISTENING;
    }

    private void handleRepeate(String content, GroupMessageEvent event) {

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
    }

    public void trySendAdSchedule() {

        boolean intervalOK = (System.currentTimeMillis() - lastTimeSendAdOrEnterChalState) > (hourInterval * 60 * 60 * 1000);
        if(!intervalOK)
            return;

        sendAdRate += 0.03;
        if(CommonUtil.randomYes(sendAdRate)) {
            qqBot.sendMsgToGroup(adMessage, groupId);
            sendAdRate = 0;
            lastTimeSendAdOrEnterChalState = System.currentTimeMillis();
        }

    }

    @Override
    public void onEnterState(GroupState oldState) {
        log.info("bot enter idle state groupId is {0}", groupId);
    }

    @Override
    public void onExitState(GroupState newState) {
        log.info("bot exit idle state groupId is {0}", groupId);

        if(newState instanceof ChallengeState) {
            lastTimeSendAdOrEnterChalState = System.currentTimeMillis();
        }
    }

	@Override
	public String getStateStr() {
		return STATE_STR;
	}


}
