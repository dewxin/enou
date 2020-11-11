package fun.enou.alpha.dto.dtoweb;


import javax.validation.constraints.NotNull;

import fun.enou.alpha.dto.dtodb.DtoDbUserThirdInfo;

public class DtoWebUserThirdInfo {
	
	private Long userId;
	
	@NotNull
	private String thirdParty;
	
	@NotNull
	private String thirdAccount;
	
	
	public DtoWebUserThirdInfo() {
		super();
	}

	public DtoWebUserThirdInfo(Long userId, @NotNull String thirdParty, @NotNull String thirdAccount) {
		super();
		this.userId = userId;
		this.thirdParty = thirdParty;
		this.thirdAccount = thirdAccount;
	}

	public DtoDbUserThirdInfo toDtoDb() {
		return new DtoDbUserThirdInfo(userId, thirdParty, thirdAccount);
	}
	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getThirdParty() {
		return thirdParty;
	}

	public void setThirdParty(String thirdParty) {
		this.thirdParty = thirdParty;
	}

	public String getThirdAccount() {
		return thirdAccount;
	}

	public void setThirdAccount(String thirdAccount) {
		this.thirdAccount = thirdAccount;
	}
	
}
