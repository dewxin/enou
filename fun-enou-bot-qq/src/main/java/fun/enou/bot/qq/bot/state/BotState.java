package fun.enou.bot.qq.bot.state;

import fun.enou.bot.qq.bot.QQBot;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.message.GroupMessageEvent;

public abstract class BotState {

    protected QQBot qqBot;
    
    public void setBot(QQBot qqBot) {
        this.qqBot = qqBot;
    }

    public QQBot getBot() {
        return this.qqBot;
    }

    public abstract ListeningStatus handleGroupMessage(GroupMessageEvent event);
    public abstract void onEnterState();
    public abstract void onExitState();
}
