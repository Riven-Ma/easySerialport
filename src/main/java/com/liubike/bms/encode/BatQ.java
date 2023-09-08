package com.liubike.bms.encode;

import com.liubike.serialize.FieldTag;

public class BatQ extends AbstractBMS<Integer> {

	@FieldTag(len = 1)
	private byte cmd = BMSCmdCode.BAT_Q;

	@FieldTag(len = 2, endian = false)
	private Integer value = 80;

	
	@Override
	public byte getCmd() {
		return cmd;
	}

	public Integer getValue() {
		return value;
	}

	@Override
	public void setValue(Integer value) {
		this.value = value;
	}

}
