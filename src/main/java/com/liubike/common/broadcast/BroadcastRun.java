package com.liubike.common.broadcast;

import com.liubike.common.queue.BroadcastQueue;
import com.liubike.common.queue.info.BroadcastInfoInterface;

public class BroadcastRun implements Runnable{

	private BroadcastQueue queue=BroadcastQueue.getIns();
	
	
	
	@Override
	public void run() {
		while(true) {
			try {
				BroadcastInfoInterface broadcast=queue.poll();
				broadcast.notice();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}	
	}

}
