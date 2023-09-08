package com.liubike.bms.encode;

import java.lang.reflect.Field;
import java.util.LinkedList;

import com.liubike.serialize.EncodeProcessInterface;
import com.liubike.serialize.FieldTag;

public class Process implements EncodeProcessInterface {

	private Integer lenIndex = 0;

	@Override
	public void encoddingFieldBefore(Class<?> clazz,Field field, Object value, FieldTag fieldTag, LinkedList<Byte> bytes) {
		if(clazz.equals(Ver.class)) {
			return;
		}
		
		if ("value".equals(field.getName())) {
			this.lenIndex = bytes.size();
		}
	}

	@Override
	public void encoddingFieldAfter(Class<?> clazz,Field field, Object value, FieldTag fieldTag, LinkedList<Byte> bytes) {
		if(clazz.equals(Ver.class)) {
			return;
		}
		
		if ("value".equals(field.getName())) {
			int len = bytes.size() - this.lenIndex;
			bytes.add(this.lenIndex, (byte) (len & 0xff));
		}
	}

	@Override
	public void encoddingTailBefore(LinkedList<Byte> bytes) {
		byte[] crc = BMScrc.calcCRC(bytes);
		bytes.addLast(crc[0]);
		bytes.addLast(crc[1]);
	}
}
