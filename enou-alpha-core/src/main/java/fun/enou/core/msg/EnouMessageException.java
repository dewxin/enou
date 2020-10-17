package fun.enou.core.msg;


/**
 * enou base message exception, all exceptions thrown to the client will inherit this exception
 * code format [0100][0000] the front part commonly starts from 100, indicating the module id, 0-99 is reserved
 * the rear part means error id; 
 * better to use an enum type represent the code
 * @author nagi
 * 
 */
public class EnouMessageException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int code;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	
	public EnouMessageException(int code, String message) {
		super(message);
		this.code = code;
	}

}
