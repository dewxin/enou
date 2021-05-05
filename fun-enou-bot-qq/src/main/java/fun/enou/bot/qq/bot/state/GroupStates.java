package fun.enou.bot.qq.bot.state;

import java.util.HashMap;

import fun.enou.bot.qq.bot.QQBot;
import net.mamoe.mirai.event.ListeningStatus;
import  net.mamoe.mirai.event.events.GroupMessageEvent;

public class GroupStates {

    public static GroupStates newInstance(QQBot bot, Long groupId) {
        GroupStates groupStates = new GroupStates(bot, groupId);
        groupStates.addState(IdleState.newInstance(bot, groupId));
        groupStates.enterIdleState();
        return groupStates;
    }

    private HashMap<String, GroupState> stateMap = new HashMap<>();
    private GroupState currentState;
    private Long groupId;    
    private QQBot bot;

    private GroupStates(QQBot bot, Long groupId) {
        this.bot = bot;
        this.groupId = groupId;
    }

    public GroupState currentState() {
        return currentState;
    }

    public boolean addState(GroupState groupState) {
        if(stateMap.containsKey(groupState.getStateStr())) {
            return false;
        }

        stateMap.put(groupState.getStateStr(), groupState);
        return true;
    }

    public void enterIdleState() {

        /* we already have one when creating the states class,
           in case of the modification in the future,
           we check wheather have idle state. */
        if(!stateMap.containsKey(IdleState.STATE_STR)) {
            addState(IdleState.newInstance(bot, groupId));
        }

        GroupState newState = stateMap.get(IdleState.STATE_STR);
        enterState(newState);
    }

    public ListeningStatus handleGroupMessage(GroupMessageEvent event) {

        return currentState.handleGroupMessage(event);
    }

    public void enterChallengeState() {

        if(!stateMap.containsKey(ChallengeState.STATE_STR)) {
            addState(ChallengeState.newInstance(bot, groupId));
        }
       
        GroupState newState = stateMap.get(ChallengeState.STATE_STR);
        enterState(newState);
    }

    private void enterState(GroupState newState) {
        GroupState prevState = currentState;
		if(prevState !=null && prevState.getStateStr().equals(newState.getStateStr())){
			return;
		}

		if(prevState != null){
			prevState.onExitState(newState);
		}

        currentState = newState;

		newState.onEnterState(prevState);
    }

    public void trySendGroupAdMessage() {
        if(currentState instanceof IdleState) {
            IdleState idleState = (IdleState) currentState;
            idleState.trySendAdSchedule();
        }
    }

}