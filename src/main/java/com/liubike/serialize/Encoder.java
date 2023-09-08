package com.liubike.serialize;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.liubike.common.code.TProtocolTool;

public class Encoder {

	private final LinkedList<Byte> list = new LinkedList<Byte>();

	private EncodeProcessInterface process = null;

	private EncodeInterface encodeObject = null;
	
	


	public byte[] encode(EncodeInterface encodeObject, EncodeProcessInterface process) throws Exception {
		this.process = process;
		return encode(encodeObject);
	}

	public byte[] encode(EncodeInterface encodeObject) throws Exception {
		this.encodeObject = encodeObject;
		try {
			byte[] head = encodeObject.getHead();
			if (head != null) {
				for (byte b : head) {
					list.addLast(b);
				}
			}
			
			
			encodeBegin(encodeObject);
			
			if(process!=null) {
				process.encoddingTailBefore(list);
			}
			byte[] tail = encodeObject.getTail();
			if (tail != null) {
				for (byte b : tail) {
					list.addLast(b);
				}
			}
			
			return listToBytes();
		} catch (Exception e) {
			throw e;
		} finally {
			process = null;
			encodeObject = null;
			list.clear();
			
		}
	
	}

	public void encodeBegin(Object obj)throws Exception {
		Class<?> clazz = obj.getClass();
		while (true) {
			try {
				Field[] fields = clazz.getDeclaredFields();
				for (Field f : fields) {
					try {
						f.setAccessible(true);
						Object value = f.get(obj);
						if (value == null) {
							continue;
						}
						FieldTag p = f.getAnnotation(FieldTag.class);
						if (p != null) {
							if(process!=null) {
								process.encoddingFieldBefore(clazz,f, value, p,list);
							}
							encodding(value, p);
							if(process!=null) {
								process.encoddingFieldAfter(clazz,f, value, p,list);
							}
						}

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				clazz = clazz.getSuperclass();
				if (Object.class == clazz) {
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
				break;
			}
		}

	}


	private void encodding(final Object value, final FieldTag p)throws Exception {
		
		if (value.getClass().isArray()) {
			// 非包装类
			int arrayLen = Array.getLength(value);
			if (arrayLen == 0) {
				return;
			}
			int listHeadIndex = list.size();
			for (int i = 0; i < arrayLen; i++) {
				Object item = Array.get(value, i);
				// 如果不是基础类型,则为自定义对象数组
				if (!this.dualBaseType(item, p)) {
					encodeBegin(item);
				}
			}
			int listLen = list.size() - listHeadIndex;
			byte[] listLenNum = dualInt(listLen, p.assembleLen(), p.endian());
			insertBytesTolist(listLenNum, listHeadIndex);
			return;
		}

		if (value instanceof List) {
			List<?> objList = (List<?>) value;
			int listHeadIndex = list.size();
			for (Object item : objList) {
				if (!dualBaseType(item, p)) {
					encodeBegin(item);
				}
			}
			int listLen = list.size() - listHeadIndex;
			byte[] listLenNum = dualInt(listLen, p.assembleLen(), p.endian());
			insertBytesTolist(listLenNum, listHeadIndex);
			return;
		}
		
		if(!this.dualBaseType(value, p)) {
			encodeBegin(value);
		}

	}

	private Boolean dualBaseType(Object value, final FieldTag p) throws Exception {
		if (value instanceof Integer) {
			addBytesTolist(dualInt((Integer) value, p.len(), p.endian()));
			return true;
		}
		if (value instanceof Long) {
			addBytesTolist(dualLong((Long) value, p.endian()));
			return true;
		}
		if (value instanceof String) {
			addBytesTolist(((String) value).getBytes());
			return true;
		}
		if (value instanceof Byte) {
			list.addLast((Byte) value);
			return true;
		}
		if (value instanceof Boolean) {
			list.addLast((Byte) value);
			return true;
		}
		// 未知类型
		if ((value instanceof Short) || (value instanceof Float) || (value instanceof Double)) {
			throw new RuntimeException("Unsupported type of collection");
		}
		return false;
	}

	private void insertBytesTolist(byte[] dataBytes, int index)throws Exception {
		if (dataBytes == null) {
			return;
		}
		int len = dataBytes.length;
		for (int i = 0; i < len; i++) {
			list.add(index, dataBytes[len - 1 - i]);
		}
	}

	private void addBytesTolist(byte[] dataBytes)throws Exception {
		if (dataBytes == null) {
			return;
		}
		for (byte b : dataBytes) {
			list.addLast(b);
		}
		return;
	}

	private byte[] dualInt(Integer data, int len, Boolean endian)throws Exception {
		if (endian) {
			switch (len) {
			case 2:
				return TProtocolTool.intTo2Bytes(data);
			case 4:
				return TProtocolTool.int2Bytes(data);
			default:
				return null;
			}
		} else {
			switch (len) {
			case 2:
				return TProtocolTool.intTo2BytesL(data);
			case 4:
				return TProtocolTool.int2BytesL(data);
			default:
				return null;
			}
		}
	}

	private byte[] dualLong(Long data, Boolean endian)throws Exception {
		if (endian) {
			return TProtocolTool.long2Bytes(data);
		} else {
			return TProtocolTool.long2BytesL(data);
		}
	}

	private byte[] listToBytes() throws Exception {
		Integer len= list.size();
		if(len==null||len==0) {
			return null;
		}
		byte[] data=new byte[len];
		Iterator<Byte> its=list.iterator();
		int i=0;
		while(its.hasNext()) {
			data[i]=its.next();
			i++;
		}
		AbstractCodding codiding=(AbstractCodding)encodeObject;
		codiding.setData(data);
		return data;
	}

}
