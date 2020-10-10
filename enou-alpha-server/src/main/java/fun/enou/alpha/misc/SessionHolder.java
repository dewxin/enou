package fun.enou.alpha.misc;

import org.springframework.stereotype.Component;

/**
 * @Author: nagi
 * @Modified By:
 * @Date Created in 2020-09-22 19:50
 * @Description:
 * @Attention:
 */
@Component
public class SessionHolder {

    ThreadLocal<Long> userIdLocal;

    public SessionHolder() {
        userIdLocal = new ThreadLocal<>();
    }

    public Long getUserId() {
        return userIdLocal.get();
    }

    public void setUserIdLocal(Long userIdLocal) {
        this.userIdLocal.set(userIdLocal);
    }
}
