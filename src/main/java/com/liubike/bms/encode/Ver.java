package com.liubike.bms.encode;

import com.liubike.serialize.FieldTag;

public class Ver extends AbstractBMS<Byte[]> {

	@FieldTag(len = 1)
	private byte cmd = BMSCmdCode.BAT_VER;

	@FieldTag()
	private Byte[] value = new Byte[] {0x00,0x01,0x03};

	
	@Override
	public byte getCmd() {
		return cmd;
	}

	public Byte[] getValue() {
		return value;
	}

	@Override
	public void setValue(Byte[] value) {
		this.value = value;
	}

}
