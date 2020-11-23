package fun.enou.core.msg;

public class EnouMsgJson<T> {
	
	private int code;
	
	private String msg;
	
	private T data;
	


	public EnouMsgJson() {
		super();
	}

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
	
	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	private EnouMsgJson(int code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
		this.data = null;
	} 
	
	private EnouMsgJson(T data) {
		super();
		this.code = 0;
		this.msg = null;
		this.data = data;
	} 
	
	public static EnouMsgJson<?> createErrorMsg(int code, String msg) {
		return new EnouMsgJson<Object>(code, msg);
	}
	
	
	public static <T> EnouMsgJson<T> createDataMsg(T data) {
		return new EnouMsgJson<T>(data);
	}
	
}
