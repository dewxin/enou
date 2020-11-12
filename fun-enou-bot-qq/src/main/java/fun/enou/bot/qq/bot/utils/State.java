package fun.enou.bot.qq.bot.utils;
/**
 * @Author: nagi
 * @Modified By:
 * @Date Created in 2020-10-08 21:09
 * @Description:
 * @Attention:
 */
public class State {

	private static String botLastSendMessage = "";
    private static String lastMessage = "";
    private static double ratePercent = 0;

	public static String getLastMessage() {
        return lastMessage;
    }

    public static void setLastMessage(String lastMessage) {
        State.lastMessage = lastMessage;
    }


    public static String getBotLastSentMessage() {
		return botLastSendMessage;
	}

	public static void setBotLastSentMessage(String lastBotSendMessage) {
		State.botLastSendMessage = lastBotSendMessage;
	}

	public static double getRatePercent() {
        return ratePercent;
    }

    public static void setRatePercent(double ratePercent) {
        State.ratePercent = ratePercent;
    }
}
