package fun.enou.core;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * @Author: nagi
 * @Modified By:
 * @Date Created in 2020-09-20 19:45
 * @Description:
 * @Attention:
 */
public class EnouPwdEncoder implements PasswordEncoder {

    /** 密钥 **/
    private String salt = "$s0lTb23$";

    public EnouPwdEncoder(String salt) {
        this.salt = salt;
    }
    @Override
    public String encode(CharSequence rawPassword) {

        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        String saltedString = rawPassword.toString()+salt;
        saltedString = Base64.getEncoder().encodeToString(saltedString.getBytes());
        byte[] pwdBytes = digest.digest(saltedString.getBytes(StandardCharsets.UTF_8));
        saltedString = Base64.getEncoder().encodeToString(pwdBytes) + salt;
        pwdBytes = digest.digest(saltedString.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(pwdBytes);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encode(rawPassword).equals(encodedPassword);
    }

    @Override
    public boolean upgradeEncoding(String encodedPassword) {
        return false;
    }
}
