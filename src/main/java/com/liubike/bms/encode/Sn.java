package com.liubike.bms.encode;

import com.liubike.serialize.FieldTag;

public class Sn extends AbstractBMS<String> {

	@FieldTag(len = 1)
	private byte cmd = BMSCmdCode.BAT_SN;

	@FieldTag(endian = false)
	private String value = "03LC30A213180409";



	@Override
	public byte getCmd() {
		return cmd;
	}

	public void setCmd(byte cmd) {
		this.cmd = cmd;
	}

	public String getValue() {
		return value;
	}
	@Override
	public void setValue(String value) {
		this.value = value;
	}

	

}
