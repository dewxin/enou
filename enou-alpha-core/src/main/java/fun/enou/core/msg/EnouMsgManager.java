package fun.enou.core.msg;

import java.text.MessageFormat;
import java.util.concurrent.ConcurrentHashMap;

public class EnouMsgManager {
	
	private static ConcurrentHashMap<Integer, EnouMessageException> code2MsgMap = new ConcurrentHashMap<>();
	
	public static void registerMessage(Integer code, String msg) {
		if(code2MsgMap.contains(code)) {
			String info = MessageFormat.format("{} code has registered", code);
			throw new Error(info);
		}
		
		code2MsgMap.put(code, new EnouMessageException(code, msg) {});
	}
	
	public static EnouMessageException getMsg(ICodeEnum codeEnum) {
		return code2MsgMap.get(codeEnum.getCode());
	}
}
