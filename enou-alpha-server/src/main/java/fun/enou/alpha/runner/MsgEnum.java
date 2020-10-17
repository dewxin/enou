package fun.enou.alpha.runner;

import fun.enou.core.msg.ICodeEnum;

public enum MsgEnum implements ICodeEnum{
	// about account , having nothing to do with business logic
	// start from 1 0000 to 1 9999
	HEADER_NOT_CONTAIN_TOKEN(10001),
	TOKEN_EXPIRED(10002),
	PARSE_TOKEN_FAIL(10003),
	UNKOWN_ERROR_PARSING_TOKEN(10004),
	TOKEN_NOT_PUT_IN_REDIS(10005),
	
	ACCOUNT_EXIST(10101),
	;
	private Integer code;
	
	
	
	public Integer getCode() {
		return code;
	}



	public void setCode(Integer code) {
		this.code = code;
	}



	private MsgEnum(int code) {
		this.code = code;
	}
}
