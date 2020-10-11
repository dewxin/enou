package fun.enou.core;

import java.util.Date;

/**
 * @Author: nagi
 * @Modified By:
 * @Date Created in 2020-09-20 22:13
 * @Description:
 * @Attention:
 */
public class TimeUtil {
    public static Date dateAddHour(Date date, int hour) {
        return new Date(date.getTime() + hour * 3600 * 1000);
    }

    public static Date dateAddMinute(Date date, int minute) {
        return new Date(date.getTime() + minute * 60 * 1000);
    }

    public static Date dateAddSecond(Date date, int second) {
        return new Date(date.getTime() + second * 1000);
    }
}
