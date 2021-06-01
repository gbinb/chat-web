package cn.fetosoft.chat.core.utils;

import java.util.*;

/**
 * @author guobingbing
 * @create 2020-02-27 11:48
 */
public class IdGenerateUtils {
	public static final long EPOCH;

	private static final long SEQUENCE_BITS = 12L;

	private static final long WORKER_ID_BITS = 10L;

	private static final long SEQUENCE_MASK = (1 << SEQUENCE_BITS) - 1;

	private static final long WORKER_ID_LEFT_SHIFT_BITS = SEQUENCE_BITS;

	private static final long TIMESTAMP_LEFT_SHIFT_BITS = WORKER_ID_LEFT_SHIFT_BITS + WORKER_ID_BITS;

	private static final long WORKER_ID_MAX_VALUE = 1L << WORKER_ID_BITS;

	private static long workerId;

	private static long sequence;

	private static long lastTime;

	static {
		Calendar calendar = Calendar.getInstance();
		calendar.set(2020, Calendar.JANUARY, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		EPOCH = calendar.getTimeInMillis();
		IdGenerateUtils.workerId = NetUtil.ipToLong(NetUtil.getLocalIP());
	}

	/**
	 * Generate key.
	 *
	 * @return key type is @{@link Long}.
	 */
	public static synchronized Number generateKey() {
		long currentMillis = System.currentTimeMillis();
		if (lastTime == currentMillis) {
			if (0L == (sequence = (sequence + 1) & SEQUENCE_MASK)) {
				currentMillis = waitUntilNextTime(currentMillis);
			}
		} else {
			sequence = 0;
		}
		lastTime = currentMillis;
		return ((currentMillis - EPOCH) << TIMESTAMP_LEFT_SHIFT_BITS) | (workerId << WORKER_ID_LEFT_SHIFT_BITS) | sequence;
	}

	private static long waitUntilNextTime(final long lastTime) {
		long time = System.currentTimeMillis();
		while (time <= lastTime) {
			time = System.currentTimeMillis();
		}
		return time;
	}

	/**
	 * 获取UUID
	 * @return
	 */
	public static String buildUUid(){
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	public static void main(String[] args) {
		System.out.println(IdGenerateUtils.generateKey().toString().length());
	}
}
