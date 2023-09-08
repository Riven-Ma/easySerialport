package com.liubike.bms.encode;

import com.liubike.serialize.FieldTag;

public class BatV extends AbstractBMS<Integer> {

	@FieldTag(len = 1)
	private byte cmd =BMSCmdCode.BAT_V;

	@FieldTag(len = 4, endian = false)
	private Integer value=90000;


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

	


//	public static void main(String[] args)throws Exception {
//		Encoder encoder=new Encoder();
//		BatV temp=new BatV();
//		temp.setValue(49739);
//		Process process=new Process();
//		byte[] data=encoder.encode(temp,process);
//		System.out.println(TProtocolTool.BytesToHexStr(data," "));
//		
//	}

}
