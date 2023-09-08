package com.liubike.bms.encode;

import com.liubike.serialize.AbstractCodding;
import com.liubike.serialize.EncodeInterface;

public class AbstractBMS<T> extends AbstractCodding implements EncodeInterface {

	private byte[] head = new byte[] { 0x3A, 0x16 };

	private byte[] tail = new byte[] { 0X0D, 0X0A };

	@Override
	public byte[] getHead() {
		return head;
	}

	@Override
	public byte[] getTail() {
		return tail;
	}
	
	public byte getCmd() {
		return 0x00;
	}

	

	
	public void setValue(T value) {
		
		
	}
	
	
}
