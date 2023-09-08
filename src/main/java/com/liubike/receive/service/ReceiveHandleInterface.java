package com.liubike.receive.service;

public interface ReceiveHandleInterface {
	
	
	byte[] head();
	
	void receiveData(byte[] data,Integer headIndex)throws Exception;

}
