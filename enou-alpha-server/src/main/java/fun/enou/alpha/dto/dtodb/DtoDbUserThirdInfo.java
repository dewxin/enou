package fun.enou.alpha.dto.dtodb;

import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import fun.enou.alpha.dto.dtoweb.DtoWebUserThirdInfo;

@Table("euser_third_info")
public class DtoDbUserThirdInfo {

	@Column("user_id")
	private Long userId;
	
	@Column("third_party")
	private String thirdParty;
	
	@Column("third_account")
	private String thirdAccount;
	
	

	public DtoDbUserThirdInfo(Long userId, String thirdParty, String thirdAccount) {
		super();
		this.userId = userId;
		this.thirdParty = thirdParty;
		this.thirdAccount = thirdAccount;
	}
	
	public DtoWebUserThirdInfo ToDtoWeb() {
		return new DtoWebUserThirdInfo(userId, thirdParty, thirdAccount);
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
