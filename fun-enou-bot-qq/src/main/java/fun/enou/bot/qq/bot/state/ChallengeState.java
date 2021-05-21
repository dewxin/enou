package fun.enou.bot.qq.bot.state;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Timer;
import java.util.TimerTask;

import com.github.dewxin.generated.auto_client.DtoWebWord;

import fun.enou.bot.qq.bot.QQBot;
import fun.enou.bot.qq.bot.challenge.OneChallenge;
import fun.enou.bot.qq.bot.challenge.WordChallenge;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.contact.User;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.events.GroupMessageEvent;

@Slf4j
public class ChallengeState extends GroupState {

    public static final String STATE_STR = "challenge";

    public static ChallengeState newInstance(QQBot bot, Long groupId) {
        ChallengeState state = new ChallengeState();
        state.setBot(bot);
        state.setGroupId(groupId);
        return state;
    }

    private OneChallenge currentChallenge;
    private TimerTask challengTimerTask;
    private Timer challengeTimer;

    private HashSet<Long> userAlreadyAnswerdSet = new HashSet<>();
    private ArrayList<User>  correctUserList = new ArrayList<>();
    private ArrayList<User>  wrongUserList = new ArrayList<>();


    private void resetAnswerUsers() {
        userAlreadyAnswerdSet.clear();
        correctUserList.clear();
        wrongUserList.clear();
    }

    @Override
    public ListeningStatus handleGroupMessage(GroupMessageEvent event) {
        String msg = event.getMessage().contentToString();
        if(currentChallenge == null) {
            qqBot.enterIdleState(groupId);
            return ListeningStatus.LISTENING;
        }

        Long userId = event.getSender().getId();
        boolean userHasAnswerd = userAlreadyAnswerdSet.contains(userId);
        if(currentChallenge.isValidAnswer(msg) && !userHasAnswerd) {
            userAlreadyAnswerdSet.add(userId);

            if(currentChallenge.isTrueAnswer(msg)) {
                correctUserList.add(event.getSender());
                boolean notRun = challengTimerTask.cancel();
                if(notRun) {
                    challengeTimer.schedule(new ChallengeOverTask(this), 1000l*1);
                }
            } else {
                wrongUserList.add(event.getSender());
            }
        }

        return ListeningStatus.LISTENING;
    }

    @Override
    public void onEnterState(GroupState oldState) {
        resetAnswerUsers();
        log.info("bot enter challenge state groupId is {}", groupId);;

        /* todo.fixme currentChallenge could be null */
        currentChallenge = WordChallenge.instance().getOneChallenge();
        if(currentChallenge == null) {
            qqBot.enterIdleState(groupId);
            return;
        }
        qqBot.getBot().getGroup(groupId).sendMessage("请听题:   Attention: ");
        qqBot.getBot().getGroup(groupId).sendMessage(currentChallenge.getQuestion());

        String allOptions = String.join("\r\n", currentChallenge.getOptionList());
        qqBot.getBot().getGroup(groupId).sendMessage(allOptions);

        challengeTimer = new Timer(true);
        challengTimerTask = new ChallengeOverTask(this);
        challengeTimer.schedule(challengTimerTask, 1000l*30);
    }


    private void challengeOver() {
        qqBot.enterIdleState(groupId);
        uploadStatisticData();
        challengeTimer.cancel();
        challengeTimer = null;

        qqBot.getBot().getGroup(groupId).sendMessage(currentChallenge.getExplanation());
        if(correctUserList.isEmpty()) {
            qqBot.getBot().getGroup(groupId).sendMessage("居然没有人能解答出我的问题，人生真是寂寞如雪啊~  Even the rain man can solve this, hmmm ");
        } else {
            StringBuilder userNames = new StringBuilder();
            correctUserList.forEach((user)->{userNames.append(user.getNick()+ " ");});
            String reply = MessageFormat.format("恭喜{0}回答正确, 虽然也没什么卵用。", userNames);
            qqBot.getBot().getGroup(groupId).sendMessage(reply);
        }
    }

    private void uploadStatisticData() {
        DtoWebWord word = currentChallenge.getRightAnswerWord();
        Integer correctCount = correctUserList.size();
        Integer wrongCount = wrongUserList.size();
        qqBot.getBotController().pileUpChalAnswerCount(word.getId(), word.getSpell(), correctCount, wrongCount);;
    }

    @Override
    public void onExitState(GroupState newState) {
        log.info("bot exit challenge state groupId is {}", groupId);
    }

	@Override
	public String getStateStr() {
        return STATE_STR;
	}


    private class ChallengeOverTask extends TimerTask {

        private ChallengeState ownerState;;
        public ChallengeOverTask(ChallengeState state) {
            this.ownerState = state;
        }

        @Override
        public void run () {
            try {
                ownerState.challengeOver();

            } catch (Exception e) {
                log.warn(e.getMessage());
            } 
        }
    }
}
