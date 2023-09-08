package com.liubike.common.queue;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

import com.liubike.common.queue.info.BroadcastInfoInterface;

public class BroadcastQueue extends BaseQueue<BroadcastInfoInterface> implements CmdQueue<BroadcastInfoInterface>{
	
	private static final BroadcastQueue ins = new BroadcastQueue();

	private BroadcastQueue() {
		super();
	}

	public static BroadcastQueue getIns() {
		return ins;
	}
	
	private BlockingDeque<BroadcastInfoInterface> queue = new LinkedBlockingDeque<BroadcastInfoInterface>(40);

	@Override
	public BroadcastInfoInterface poll() {
		return super.poll(queue);
	}

	@Override
	public boolean add(BroadcastInfoInterface message) {
		return super.add(queue, message);
	}
	
	

}
