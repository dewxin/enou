package fun.enou.alpha.msg;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import fun.enou.core.msg.EnouMsgManager;

@Component
public class MsgRegisterRunner implements CommandLineRunner{

	@Override
	public void run(String... args) throws Exception {
		for(MsgEnum msg : MsgEnum.values()) {
			EnouMsgManager.registerMessage(msg.getCode(), msg.toString());
		}
	}

}
