package fun.enou.core.msg;

public class EnouMsgJson {
	
	private int code;
	
	private String msg;

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

	public EnouMsgJson(int code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	} 
	
}
