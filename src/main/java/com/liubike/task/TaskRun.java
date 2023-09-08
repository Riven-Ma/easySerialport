package com.liubike.task;

import java.util.ArrayList;

import com.liubike.common.TimerUtil;

public class TaskRun implements Runnable {

	private Object lock = new Object();

	private ArrayList<TaskInterface> list = new ArrayList<TaskInterface>();

	private TimerUtil timerUtil = TimerUtil.getIns();

	public void addTask(TaskInterface task) {
		list.add(task);
	}

	@Override
	public void run() {

		while (true) {
			try {

				synchronized (lock) {
					lock.wait(1000);
				}
				Long now = timerUtil.getCurrentTime();
				list.forEach(e -> {
					if (e.isEnabled() && now > e.getNextTime()) {
						e.dual(now);
						e.setLastTime(now);
						e.setNextTime(now + e.getInterval());
					}
				});

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}
