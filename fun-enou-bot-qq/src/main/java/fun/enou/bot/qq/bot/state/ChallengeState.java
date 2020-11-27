package fun.enou.bot.qq.bot.state;

import java.util.Timer;
import java.util.TimerTask;

import fun.enou.bot.qq.bot.challenge.OneChallenge;
import fun.enou.bot.qq.bot.challenge.WordChallenge;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.message.GroupMessageEvent;

public class ChallengeState extends BotState {

    private static ChallengeState challengeState = new ChallengeState();

    public static ChallengeState instance() {
        return challengeState;
    }

    private OneChallenge currentChallenge;

    @Override
    public ListeningStatus handleGroupMessage(GroupMessageEvent event) {
        String msg = event.getMessage().contentToString();
        //todo get the sender's qq number and nickname , store the info 
        if(currentChallenge == null) {
            return ListeningStatus.LISTENING;
        }

        // if(currentChallenge.isTrueAnswer(msg)) {
        //     event.getGroup().sendMessage("回答正确。"); //todo delete; 
        // }

        return ListeningStatus.LISTENING;
    }

    @Override
    public void onEnterState() {
        qqBot.sendMsgToAllGroups("机器人进入Challenge状态");
        WordChallenge.instance().prepare();
        Timer timer = new Timer(true);
        timer.schedule(new TimerTask(){

            //todo before getting the resource ,lock it
            @Override
            public void run() {

                //handle the last round logic 
                boolean explanationRound = false;
                if(currentChallenge != null) {
                    qqBot.sendMsgToAllGroups(currentChallenge.getExplanation());
                    explanationRound = true;
                    currentChallenge = null;
                }

                if(WordChallenge.instance().isOver()) {
                    qqBot.sendMsgToAllGroups("本次词库挑战到此结束，可以私聊机器人 getDef word, 查找单词的意思。");
                    qqBot.enterIdleState();
                    timer.cancel();
                }


                if(!explanationRound) {
                    currentChallenge = WordChallenge.instance().getOneChallenge();

                    qqBot.sendMsgToAllGroups(currentChallenge.getQuestion());
                    for(String option : currentChallenge.getOptionList()) {
                        qqBot.sendMsgToAllGroups(option);
                    }
                }

            }
            
        }, 1000, 1000l*30);
    }

    @Override
    public void onExitState() {
        qqBot.sendMsgToAllGroups("机器人退出Challenge状态");
    }


}
