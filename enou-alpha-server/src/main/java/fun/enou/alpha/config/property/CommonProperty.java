package fun.enou.alpha.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "enou")
public class CommonProperty {
	
	private String pwdSalt="enou$Salt$";
	private String crossOriginAllowed="*";

	public String getPwdSalt() {
		return pwdSalt;
	}

	public void setPwdSalt(String pwdSalt) {
		this.pwdSalt = pwdSalt;
	}

	public String getCrossOriginAllowed() {
		return crossOriginAllowed;
	}

	public void setCrossOriginAllowed(String crossOrigin) {
		this.crossOriginAllowed = crossOrigin;
	}	
	
}
