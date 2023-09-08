package com.liubike.common.broadcast;

import java.util.ArrayList;

import com.liubike.common.queue.BroadcastQueue;
import com.liubike.common.queue.info.BroadcastInfo;

public class BroadcastManager {

	private static final ArrayList<BroadcastInterface> list = new ArrayList<BroadcastInterface>();
	
	private static final BroadcastQueue queue=BroadcastQueue.getIns();

	private BroadcastManager() {
		super();
	}

	private static final BroadcastManager ins = new BroadcastManager();

	public static BroadcastManager getIns() {
		return ins;
	}

	public void register(BroadcastInterface boadcast) {
		list.add(boadcast);
	}

	
	public void send(Object obj,int boadcastType) {
		for (BroadcastInterface item : list) {
			Integer listenerType=item.listenerType();
			if(boadcastType==(boadcastType & listenerType)) {
				queue.add(new BroadcastInfo(new Broadcast(obj, boadcastType), item));
				continue;
			}
			if(boadcastType==(boadcastType & listenerType)) {
				queue.add(new BroadcastInfo(new Broadcast(obj, boadcastType), item));
				continue;
			}
		}
	}
	

	

}
