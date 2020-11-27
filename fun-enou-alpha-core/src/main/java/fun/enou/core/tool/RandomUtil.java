package fun.enou.core.tool;

import java.util.Random;

public class RandomUtil {
	private static Random random = new Random(System.currentTimeMillis());
	
	public static Long randomLong() {
		return random.nextLong();
	}
	
	
	public static Integer randomInt() {
		return random.nextInt();
	}
	
	public static Integer randomInt(int bound) {
		return random.nextInt(bound);
	}
	
	public static Double randomDouble() {
		return random.nextDouble();
	}
	
}
