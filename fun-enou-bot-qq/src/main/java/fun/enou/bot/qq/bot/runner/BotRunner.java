package fun.enou.bot.qq.bot.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import fun.enou.bot.qq.bot.config.CommonProperty;
import fun.enou.bot.qq.bot.listener.FriendEventListener;
import fun.enou.bot.qq.bot.listener.FriendMessageListener;
import fun.enou.bot.qq.bot.listener.GroupMessageListener;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactoryJvm;
import net.mamoe.mirai.event.Events;
import net.mamoe.mirai.utils.BotConfiguration;

@Component
@Slf4j
public class BotRunner implements CommandLineRunner {
	
    private Bot bot;
    
    @Autowired
    private CommonProperty commonProperty;

    public Bot getBot() {
        return bot;
    }

    @Override
    public void run(String... args) throws Exception {
    	
    	Thread thread = new Thread(() -> {
            bot = BotFactoryJvm.newBot(
                    commonProperty.getAccount(), 
                    commonProperty.getPassword(),
                    new BotConfiguration() {{
                        // 设备缓存信息
                        setProtocol(MiraiProtocol.ANDROID_PAD);
                        fileBasedDeviceInfo("deviceInfo.json");
                    }}
            );

            bot.login();
            
            log.info("{} bot login succeed", bot.getNick());

            Events.registerEvents(bot, new GroupMessageListener());
            Events.registerEvents(bot, new FriendMessageListener());
            Events.registerEvents(bot, new FriendEventListener());
            
            bot.join();
            
            log.info("bot after join");
        });
        thread.start();

    }
}
