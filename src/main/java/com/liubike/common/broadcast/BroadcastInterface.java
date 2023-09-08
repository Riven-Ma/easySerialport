package com.liubike.common.broadcast;

public interface BroadcastInterface {
	
	
	void receive(Broadcast boadcast);
	
	int listenerType();
	
	public static final int RECEIVE_SERIA_PORT_DATA= 0X00000001;
	public static final int SEND_SERIA_PORT_DATA= 0X00000002;

}
