package fun.enou.bot.qq.bot.state;

import java.text.MessageFormat;
import java.util.Timer;
import java.util.TimerTask;

import fun.enou.bot.qq.bot.QQBot;
import fun.enou.bot.qq.bot.challenge.OneChallenge;
import fun.enou.bot.qq.bot.challenge.WordChallenge;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.message.GroupMessageEvent;

@Slf4j
public class ChallengeState extends BotState {


    public static ChallengeState newInstance(QQBot bot, Long groupId) {
        ChallengeState state = new ChallengeState();
        state.setBot(bot);
        state.setGroupId(groupId);
        return state;
    }

    private OneChallenge currentChallenge;
    private Timer challengeTimer;

    public ChallengeState() {
        state = "challenge";
    }

    @Override
    public ListeningStatus handleGroupMessage(GroupMessageEvent event) {
        String msg = event.getMessage().contentToString();
        //todo get the sender's qq number and nickname , store the info 
        if(currentChallenge == null) {
            qqBot.enterIdleState(groupId);
            return ListeningStatus.LISTENING;
        }

        if(currentChallenge.isTrueAnswer(msg)) {
            String nickName = event.getSender().getNick();
            String reply = MessageFormat.format("恭喜{0}回答正确, 虽然也没什么卵用。", nickName);
            event.getGroup().sendMessage(reply);
            challengeOver();
        }

        return ListeningStatus.LISTENING;
    }

    @Override
    public void onEnterState() {
        log.info("bot enter challenge state groupId is {0}", groupId);;
        currentChallenge = WordChallenge.instance().getOneChallenge();
        qqBot.getBot().getGroup(groupId).sendMessage("请听题:   Attention:");
        qqBot.getBot().getGroup(groupId).sendMessage(currentChallenge.getQuestion());
        for(String option : currentChallenge.getOptionList()) {
            qqBot.getBot().getGroup(groupId).sendMessage(option);
        }

        challengeTimer = new Timer(true);
        challengeTimer.schedule(new TimerTask(){

            @Override
            public void run () {
                try {
                    challengeOver();
                    qqBot.getBot().getGroup(groupId).sendMessage("居然没有人能解答出我的问题，人生真是寂寞如雪啊~  Even the rain man can solve this, hmmm ");
                } catch (Exception e) {
                    log.warn(e.getMessage());
                } 
            }

        }, 1000l*30);
    }


    private void challengeOver() {
        qqBot.enterIdleState(groupId);
        challengeTimer.cancel();
        challengeTimer = null;
        qqBot.getBot().getGroup(groupId).sendMessage(currentChallenge.getExplanation());
    }

    // private void startWordChallenge() {
    //     WordChallenge.instance().prepare();
    //     Timer timer = new Timer(true);
    //     timer.schedule(new TimerTask(){

    //         //todo before getting the resource ,lock it
    //         @Override
    //         public void run() {

    //             //handle the last round logic 
    //             boolean explanationRound = false;
    //             if(currentChallenge != null) {
    //                 qqBot.sendMsgToAllGroups(currentChallenge.getExplanation());
    //                 explanationRound = true;
    //                 currentChallenge = null;
    //             }

    //             if(WordChallenge.instance().isOver()) {
    //                 qqBot.sendMsgToAllGroups("本次词库挑战到此结束，可以私聊机器人 getDef word, 查找单词的意思。");
    //                 qqBot.enterIdleState();
    //                 timer.cancel();
    //             }


    //             if(!explanationRound) {
    //                 currentChallenge = WordChallenge.instance().getOneChallenge();

    //                 qqBot.sendMsgToAllGroups(currentChallenge.getQuestion());
    //                 for(String option : currentChallenge.getOptionList()) {
    //                     qqBot.sendMsgToAllGroups(option);
    //                 }
    //             }

    //         }
            
    //     }, 1000, 1000l*30);
    // }


    @Override
    public void onExitState() {
        log.info("bot exit challenge state groupId is {0}", groupId);
    }


}
