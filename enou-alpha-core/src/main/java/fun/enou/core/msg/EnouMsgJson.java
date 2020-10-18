package fun.enou.core.msg;

public class EnouMsgJson {
	
	private int code;
	
	private String msg;
	
	private Object data;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	private EnouMsgJson(int code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
		this.data = null;
	} 
	
	private EnouMsgJson(Object data) {
		super();
		this.code = 0;
		this.msg = null;
		this.data = data;
	} 
	
	public static EnouMsgJson createErrorMsg(int code, String msg) {
		return new EnouMsgJson(code, msg);
	}
	
	public static EnouMsgJson createDataMsg(Object data) {
		return new EnouMsgJson(data);
	}
	
}
