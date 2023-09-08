package com.liubike.bms.encode;

import java.util.List;

import com.liubike.common.code.TProtocolTool;

public class BMScrc {

	public static byte[] calcCRC(List<Byte> in) {
		int r = 0;
		for (int i = 1; i < in.size(); i++) {
			r += in.get(i) & 0xFF;
		}
		return TProtocolTool.intTo2BytesL(r);
	}

}
