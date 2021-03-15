package fun.enou.bot.qq.bot.state;

import fun.enou.bot.qq.bot.QQBot;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.message.GroupMessageEvent;

public abstract class GroupState {

    protected QQBot qqBot;
    protected Long groupId;
    
    public void setBot(QQBot qqBot) {
        this.qqBot = qqBot;
    }

    public QQBot getBot() {
        return this.qqBot;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public abstract ListeningStatus handleGroupMessage(GroupMessageEvent event);
    public abstract void onEnterState(GroupState from);
    public abstract void onExitState(GroupState to);

    public abstract String getStateStr();

}
