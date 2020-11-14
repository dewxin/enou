package fun.enou.bot.qq.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "enou.bot")
public class BotProperty {
	
	private Long account;
	
	private String password;
	
	private Long tmpUser;
	
	private String protocol;
	
	

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public Long getAccount() {
		return account;
	}

	public void setAccount(Long account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getTmpUser() {
		return tmpUser;
	}

	public void setTmpUser(Long tmpUser) {
		this.tmpUser = tmpUser;
	}
	
	

}
