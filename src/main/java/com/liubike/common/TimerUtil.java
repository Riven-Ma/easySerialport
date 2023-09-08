package com.liubike.common;

public class TimerUtil {

	private Long currentTime = System.currentTimeMillis();

	public Long getCurrentTime() {
		return currentTime;
	}

	private TimerUtil() {
		super();
		MyThreadPool.getThreadPool().execute(new Timer());
	}

	private static TimerUtil ins = null;

	public static final TimerUtil getIns() {
		if (ins == null) {
			ins = new TimerUtil();
		}
		return ins;
	}

	class Timer implements Runnable {

		private Object lock = new Object();

		@Override
		public void run() {
			while (true) {
				try {
					synchronized (lock) {
						lock.wait(0,50000);
						currentTime = System.currentTimeMillis();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}

	}

}
