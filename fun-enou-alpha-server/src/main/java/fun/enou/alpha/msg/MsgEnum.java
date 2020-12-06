package fun.enou.alpha.msg;

import fun.enou.core.msg.EnouMessageException;
import fun.enou.core.msg.EnouMsgManager;

public enum MsgEnum {
	// about account , having nothing to do with business logic
	// start from 1 0000 to 1 9999
	HEADER_NOT_CONTAIN_TOKEN(10001),
	TOKEN_EXPIRED(10002),
	PARSE_TOKEN_FAIL(10003),
	OTHER_ERROR_PARSING_TOKEN(10004),
	TOKEN_NOT_PUT_IN_REDIS(10005),
	TOKEN_NOT_MATCH_REDIS(10006),
	
	ACCOUNT_EXIST(10101),
	ACCOUNT_OR_PWD_WRONG(10102),
	
	
	//about word, range is [20000, 21000)
	WORD_NOT_FOUND(20001),
	WORD_DEF_LIST_PARSE_FAIL(20002),



	//related to article, range is [21000, 22000]
	ARTICLE_NOT_FOUND(21001),
	;
	private Integer code;
	
	
	private EnouMessageException Exception() {
		return EnouMsgManager.getMsg(this.code);
	}
	
	public void ThrowException() throws EnouMessageException {
		throw this.Exception();
	}
	
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
