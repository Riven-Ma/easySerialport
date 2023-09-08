package com.liubike.common.code;

import java.util.List;

/**
 * @author Administrator
 */
public class DecodeTool {

	public final static String SPORT_STR_START = "*STATUS#"; // 运动数据字符串标志
	public final static String SPORT_DATA_NULL = "NULL"; // 无运动数据
	public final static byte MASK = 0x01;
	
	
	

	/**
	 * 将字节数组转为long<br>
	 * 如果input为null,或offset指定的剩余数组长度不足8字节则抛出异常
	 * 
	 * @param input
	 * @param offset    起始偏移量
	 * @param bigEndian 输入数组是否大端模式
	 * @return
	 */
	public static long net8bytesToLong(byte[] input, int offset, boolean bigEndian) {
		long value = 0;
		for (int count = 0; count < 8; ++count) {
			int shift = (bigEndian ? (7 - count) : count) << 3;
			value |= ((long) 0xff << shift) & ((long) input[offset + count] << shift);
		}
		return value;
	}

	/**
	 * 将网络4字节转换成Long
	 *
	 * @param in
	 * @param index
	 * @return
	 */
	public static int net4bytesToLong(byte[] in, int index) {
		return (TProtocolTool.GetDelphiByte(in[index]) << 24) + (TProtocolTool.GetDelphiByte(in[index + 1]) << 16)
				+ (TProtocolTool.GetDelphiByte(in[index + 2]) << 8) + TProtocolTool.GetDelphiByte(in[index + 3]);
	}

	/**
	 * 将网络4字节转换成int 高位在前
	 * 
	 * @param in
	 * @param index
	 * @return
	 */
	public static int net4byteToIntH(final byte[] in, int index) {
		return (TProtocolTool.GetDelphiByte(in[index]) << 24) + (TProtocolTool.GetDelphiByte(in[index + 1]) << 16)
				+ (TProtocolTool.GetDelphiByte(in[index + 2]) << 8) + TProtocolTool.GetDelphiByte(in[index + 3]);
	}

	/**
	 * 将网络4字节转换成int 低位在前
	 * 
	 * @param in
	 * @param index
	 * @return
	 */
	public static int net4bytesToIntL(byte[] in, int index) {
		return (TProtocolTool.GetDelphiByte(in[index + 3]) << 24) + (TProtocolTool.GetDelphiByte(in[index + 2]) << 16)
				+ (TProtocolTool.GetDelphiByte(in[index + 1]) << 8) + TProtocolTool.GetDelphiByte(in[index]);
	}

	/**
	 * 将网络8字节转换成int 高位在前
	 * 
	 * @param in
	 * @param index
	 * @return
	 */
	public static int net8bytesToIntH(byte[] in, int index) {
		return TProtocolTool.GetDelphiByte(in[index + 7]) + (TProtocolTool.GetDelphiByte(in[index + 6]) << 8)
				+ (TProtocolTool.GetDelphiByte(in[index + 5]) << 16)
				+ (TProtocolTool.GetDelphiByte(in[index + 4]) << 24)
				+ (TProtocolTool.GetDelphiByte(in[index + 3]) << 32)
				+ (TProtocolTool.GetDelphiByte(in[index + 2]) << 40)
				+ (TProtocolTool.GetDelphiByte(in[index + 1]) << 48) + (TProtocolTool.GetDelphiByte(in[index]) << 56);
	}

	/**
	 * 将网络2字节转换成word
	 *
	 * @param in
	 * @param index
	 * @return
	 */
	public static int bytes2ToWordHighBefore(byte[] in, int index) {
		return (TProtocolTool.GetDelphiByte(in[index]) << 8) + TProtocolTool.GetDelphiByte(in[index + 1]);
	}

	/**
	 * 将网络2字节转换成word
	 *
	 * @param
	 * @param index
	 * @return
	 */
	public static int bytes2ToWordLowBefore(final byte[] src, int index) {
		return (TProtocolTool.GetDelphiByte(src[index + 1]) << 8) + TProtocolTool.GetDelphiByte(src[index]);
	}

	/**
	 * 获取有符号整数
	 * 
	 * @param in
	 * @param index
	 * @return
	 */
	public static int bytesToSymbolWord(byte[] in, int index) {
		byte[] tmp = GetBytes(in, index, 2);
		int ret = 0;
		// 判断最高位是否为1，如果为1的话则取反加一
		if ((tmp[0] & 0x80) == 0x80) {
			for (int i = 0; i < 2; i++) {
				tmp[i] = (byte) (tmp[i] ^ 0xFF); // 取反
			}
			ret = (TProtocolTool.GetDelphiByte(tmp[0]) << 8) + TProtocolTool.GetDelphiByte(tmp[1]) + 1; // 加一
			ret *= -1; // 增加符号
		} else {
			ret = (TProtocolTool.GetDelphiByte(tmp[0]) << 8) + TProtocolTool.GetDelphiByte(tmp[1]);
		}

		return ret;

	}

	/**
	 * 获取有符号整数，小端
	 * 
	 * @param
	 * @param index
	 * @return
	 */
	public static int byte2ToSymbolWordD(final byte[] src, int index) {
		byte[] tmp = new byte[2];
		tmp[0] = src[index + 1];
		tmp[1] = src[index];
		int ret = 0;
		// 判断最高位是否为1，如果为1的话则取反加一
		if ((tmp[0] & 0x80) == 0x80) {
			for (int i = 0; i < 2; i++) {
				tmp[i] = (byte) (tmp[i] ^ 0xFF); // 取反
			}
			ret = (TProtocolTool.GetDelphiByte(tmp[0]) << 8) + TProtocolTool.GetDelphiByte(tmp[1]) + 1; // 加一
			ret *= -1; // 增加符号
		} else {
			ret = (TProtocolTool.GetDelphiByte(tmp[0]) << 8) + TProtocolTool.GetDelphiByte(tmp[1]);
		}
		return ret;
	}

	/**
	 * 获取有符号整数
	 * 
	 * @param
	 * @param index
	 * @return
	 */
	public static int byte2ToSymbolWord(final byte[] src, int index) {
		byte[] tmp = new byte[2];
		tmp[0] = src[index];
		tmp[1] = src[index + 1];
		int ret = 0;
		// 判断最高位是否为1，如果为1的话则取反加一
		if ((tmp[0] & 0x80) == 0x80) {
			for (int i = 0; i < 2; i++) {
				tmp[i] = (byte) (tmp[i] ^ 0xFF); // 取反
			}
			ret = (TProtocolTool.GetDelphiByte(tmp[0]) << 8) + TProtocolTool.GetDelphiByte(tmp[1]) + 1; // 加一
			ret *= -1; // 增加符号
		} else {
			ret = (TProtocolTool.GetDelphiByte(tmp[0]) << 8) + TProtocolTool.GetDelphiByte(tmp[1]);
		}
		return ret;
	}

	/**
	 * 将一个网络字节转换成word
	 * 
	 * @param in
	 * @param index
	 * @return
	 */
	public static int bytesToWord(byte[] in, int index) {
		return TProtocolTool.GetDelphiByte(in[index]);
	}

	/**
	 * 解析media数据
	 */
	public static String decoderMediaData(byte[] in, int index, int len) {
		if (len <= 0) {

			return "";
		}

		byte[] dst = new byte[len];
		for (int i = 0; i < len; i++) {
			dst[i] = in[index + i];
		}

		return TProtocolTool.BytesToHexStr(dst);

	}

	/**
	 * 解析media数据
	 */
	public static byte[] decoderMediaBytesData(byte[] in, int index, int len) {
		if (len <= 0) {

			return null;
		}

		byte[] dst = new byte[len];
		for (int i = 0; i < len; i++) {
			dst[i] = in[index + i];
		}
		return dst;
	}

	public static String decoderBytes(byte[] in, int index, int len) {
		StringBuffer dId = new StringBuffer(len);
		for (int i = 0; i < len; i++) {
			dId.append((char) in[index + i]);
		}
		return dId.toString();
	}

	public static String decoderIp(byte[] in, int index, int len) {
		StringBuffer dId = new StringBuffer(len + 3);
		for (int i = 0; i < len; i++) {
			if (i == 0) {
				dId.append((int) (in[index + i] & 0xFF));
			} else {
				dId.append(".");
				dId.append((int) (in[index + i] & 0xFF));
			}
		}
		return dId.toString();
	}

	/**
	 * 生成 checkSum
	 */
	public static byte calcCRC(List<Byte> in, int begin) {
		byte r = 0;
		for (int i = begin; i < in.size(); i++) {
			r += in.get(i);
		}
		return (byte) TProtocolTool.GetDelphiByte(r);
	}

	/**
	 * CRC 计算
	 * 
	 * @param in
	 * @param index 开始位置
	 * @param len   长度
	 * @return
	 */
	public static int calcCRC(byte[] in, int index, int len) {
		byte r = 0;

		for (int i = 0; i < len; i++) {
			r += in[index + i];
		}
		return TProtocolTool.GetDelphiByte(r);
	}

	/**
	 * CRC 计算
	 * 
	 * @param in
	 * @param index 开始位置
	 * @param len   长度
	 * @return
	 */
	public static int checkSum4Byte(byte[] in, int index, int len) {
		int r = 0;

		for (int i = 0; i < len; i++) {
			r += (in[index + i] & 0xff);
		}
		return r;
	}

	/**
	 * 将网络4字节转换成int 高位在前
	 * 
	 * @param in
	 * @param index
	 * @return
	 */
	public static int Net4ByteBufToIntH(final byte[] in, int index) {
		return (TProtocolTool.GetDelphiByte(in[index]) << 24) + (TProtocolTool.GetDelphiByte(in[index + 1]) << 16)
				+ (TProtocolTool.GetDelphiByte(in[index + 2]) << 8) + TProtocolTool.GetDelphiByte(in[index + 3]);
	}

	/**
	 * 将网络4字节转换成int 低位在前
	 * 
	 * @param in
	 * @param index
	 * @return
	 */
	public static int Net4ByteBufToIntL(final byte[] in, int index) {
		return (TProtocolTool.GetDelphiByte(in[index + 3]) << 24) + (TProtocolTool.GetDelphiByte(in[index + 2]) << 16)
				+ (TProtocolTool.GetDelphiByte(in[index + 1]) << 8) + TProtocolTool.GetDelphiByte(in[index]);
	}

	/**
	 * 将网络2字节转换成word
	 *
	 * @param src
	 * @param index
	 * @return
	 */
	public static int Net2ByteBufToWordLowBefore(final byte[] src, int index) {
		return (TProtocolTool.GetDelphiByte(src[index + 1]) << 8) + TProtocolTool.GetDelphiByte(src[index]);
	}

	/**
	 * 获取有符号整数
	 * 
	 * @param src
	 * @param index
	 * @return
	 */
	public static int Net2ByteBufToSymbolWord(final byte[] src, int index) {
		byte[] tmp = new byte[2];
		tmp[0] = src[index];
		tmp[1] = src[index + 1];
		int ret = 0;
		// 判断最高位是否为1，如果为1的话则取反加一
		if ((tmp[0] & 0x80) == 0x80) {
			for (int i = 0; i < 2; i++) {
				tmp[i] = (byte) (tmp[i] ^ 0xFF); // 取反
			}
			ret = (TProtocolTool.GetDelphiByte(tmp[0]) << 8) + TProtocolTool.GetDelphiByte(tmp[1]) + 1; // 加一
			ret *= -1; // 增加符号
		} else {
			ret = (TProtocolTool.GetDelphiByte(tmp[0]) << 8) + TProtocolTool.GetDelphiByte(tmp[1]);
		}

		return ret;

	}

	public static byte[] GetBytes(byte[] bFrame, int iStart, int iLength) { // 获得一个byte[]
		// 从iStart开始
		// 后的iLength长的数据
		byte[] bRet = new byte[iLength];
		int i = 0;
		if (iLength > bFrame.length) {
			return bRet;
		}
		for (i = 0; i < iLength; i++) {
			bRet[i] = bFrame[iStart + i];
		}
		return bRet;
	}

	public static void addArrayBytes(List<Byte> out, byte[] waitAdd) {
		for (byte b : waitAdd) {
			out.add(b);
		}
	}

	public static void addArrayBytes(List<Byte> out, List<Byte> waitAdd) {
		for (byte b : waitAdd) {
			out.add(b);
		}
	}

	/**
	 * 生成 crc
	 */
	public static byte calcCRC(List<Byte> in) {
		byte r = (byte) TProtocolTool.GetDelphiByte(in.get(0));
		for (int i = 1; i < in.size(); i++) {
			r ^= TProtocolTool.GetDelphiByte(in.get(i));
		}
		return r;
	}

	/**
	 * 将inBytes字节数组限定指定长度，长度不够的使用ch填充； 不修改inBytes
	 * 
	 * @param inBytes 需要限制的字节数组
	 * @param len     指定长度
	 * @param ch      填充的值
	 * @return
	 */
	public static byte[] limitLen(final byte[] inBytes, int len, final byte ch) {
		byte[] res = new byte[len];
		if (inBytes.length < len) { // inBytes长度小于指定的len长度
			for (int i = 0; i < inBytes.length; i++) {
				res[i] = inBytes[i];
			}
			for (int i = inBytes.length; i < len; i++) {
				res[i] = ch;
			}
		} else { // 当inBytes长度大于等于指定 len长度
			for (int i = 0; i < len; i++) {
				res[i] = inBytes[i];
			}
		}
		return res;
	}

	public static String bytes2HasString(byte[] src, int srcIndex, int length) {
		byte[] byteArray = new byte[length];
		System.arraycopy(src, srcIndex, byteArray, 0, length);
		return TProtocolTool.BytesToHexStr(byteArray);
	}

	public static String bytes2String(byte[] src, int srcIndex, int length) {
		byte[] byteArray = new byte[length];
		System.arraycopy(src, srcIndex, byteArray, 0, length);
		return new String(byteArray);
	}

}
