package fun.enou.bot.qq.bot.utils;
import java.util.Random;

/**
 * @Author: nagi
 * @Modified By:
 * @Date Created in 2020-10-06 12:18
 * @Description:
 * @Attention:
 */
public class CommonUtil {
    private static Random random = new Random(System.currentTimeMillis());

    public static boolean randomYes() {
        return random.nextBoolean();
    }

    public static boolean randomYes(double rate) {
        return  random.nextDouble() < rate;
    }
}
