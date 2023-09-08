package com.liubike.common.queue.info;

import com.liubike.common.broadcast.Broadcast;
import com.liubike.common.broadcast.BroadcastInterface;

public class BroadcastInfo implements BroadcastInfoInterface{

	public BroadcastInfo(Broadcast boadcast, BroadcastInterface listener) {
		super();
		this.boadcast = boadcast;
		this.listener = listener;
	}

	private Broadcast boadcast;

	private BroadcastInterface listener;

	public Broadcast getBoadcast() {
		return boadcast;
	}

	public void setBoadcast(Broadcast boadcast) {
		this.boadcast = boadcast;
	}

	public BroadcastInterface getListener() {
		return listener;
	}

	public void setListener(BroadcastInterface listener) {
		this.listener = listener;
	}
	
	
	@Override
	public void notice() {
		listener.receive(boadcast);
	}
	

}
