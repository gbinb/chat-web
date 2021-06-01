package cn.fetosoft.chat.core.utils;

import java.util.concurrent.*;

/**
 * 线程池工具类
 * @author guobingbing
 * @create 2020/12/25 10:52
 */
public final class ThreadPoolUtil {

	private static final int CORE = Runtime.getRuntime().availableProcessors();
	private static final ExecutorService THREAD_POOL = new ThreadPoolExecutor(CORE<<1, CORE<<2, 5, TimeUnit.MINUTES, new ArrayBlockingQueue<>(8192));

	/**
	 * 执行任务
	 * @param runnable
	 */
	public static void execute(Runnable runnable){
		THREAD_POOL.execute(runnable);
	}

	/**
	 * 执行带有返回结果的任务
	 * @param task
	 * @param <T>
	 * @return
	 */
	public static <T> Future<T> submit(Callable<T> task){
		return THREAD_POOL.submit(task);
	}
}
