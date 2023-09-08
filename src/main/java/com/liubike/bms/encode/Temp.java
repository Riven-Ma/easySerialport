package com.liubike.bms.encode;

import com.liubike.serialize.FieldTag;

public class Temp extends AbstractBMS<Integer> {

	@FieldTag(len = 1)
	private byte cmd = BMSCmdCode.BAT_TEMP;

	@FieldTag(len = 4, endian = false)
	private Integer value = 3013;

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
