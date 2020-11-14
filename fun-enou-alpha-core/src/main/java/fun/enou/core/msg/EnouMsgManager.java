package fun.enou.core.msg;

import java.text.MessageFormat;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 
 * @author xinlu
 *
 */
public class EnouMsgManager {
	
	private static ConcurrentHashMap<Integer, EnouMessageException> code2MsgMap = new ConcurrentHashMap<>();
	
	@SuppressWarnings("serial")
	public static void registerMessage(Integer code, String msg) {
		if(code2MsgMap.containsKey(code)) {
			String info = MessageFormat.format("code {0} has registered", code);
			throw new Error(info);
		}
		
		code2MsgMap.put(code, new EnouMessageException(code, msg) {});
	}
	
	public static EnouMessageException getMsg(int code) {
		return code2MsgMap.get(code);
	}
}
