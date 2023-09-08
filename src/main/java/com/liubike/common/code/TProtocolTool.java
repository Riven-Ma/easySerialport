package com.liubike.common.code;

import org.apache.commons.codec.binary.Base64;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class TProtocolTool {
	public static final float CONST_FLOAT_BL = 1000.0f; // 32浮点数转换比例区间等于(FLONGWORD/1000)
	public static final float CONST_SFLOAT_BL = 100.0f; // 16浮点数转换比例区间等于(FWORD/100)

	public static final double CONST_LENGTH_BL = 10.0; // 长宽转换比例
	public static final double CONST_DRAUGHT_BL = 100.0; // 吃水转换比例
	public static final float CONST_SPEED_BL = 100.0f; // 速度转换比例
	public static final float CONST_DIRECTION_BL = 100.0f; // 方向转换比例
	public static final double CONST_LONLAT_BL = 1000000.0; // 经纬度转换比例
	public static final String ENCODING_JAVA = "gbk";
	public static final String ENCODING_CORBA = "iso-8859-1";
	// private static BASE64Encoder base64Encoder = new BASE64Encoder();
	// private static BASE64Decoder base64Decoder = new BASE64Decoder();
	/**
	 * sun.misc.BASE64Encoder 是sun内部使用类，没有公开，所以正式使用时候也不建议使用 使用commons-codec.jar包进行替换
	 */
	
	/**
	 * 
	 * @param data
	 * @param src
	 * @return
	 */
	public static Integer findBytes(byte[] data,byte[] src) {
		Integer datalen=data.length;
		Integer headLen=src.length;
		Integer headFirstByteIndex = -1;
		for (int i = 0; i < datalen; i++) {
			Integer reduceLen = datalen - i;
			if (reduceLen <= headLen) {
				break;
			}
			// find first byte
			if (src[0] == (data[i] & 0Xff)) {
				reduceLen = datalen - i;
				if (reduceLen <= headLen) {
					return headFirstByteIndex;
				}
				headFirstByteIndex = i;
				int index = headFirstByteIndex;
				Boolean isEqual = true;
				for (int j = 1; j < headLen; j++) {
					if (src[j] != data[index + 1]) {
						isEqual = false;
						break;
					}
				}
				// if unequal then continue find after headFirstByteIndex
				if (!isEqual) {
					continue;
				}
				return headFirstByteIndex;
			}

		}
		return headFirstByteIndex;
	}
	
	private static Base64 base64Handler = new Base64();

	public static String java2corba(String str) throws Exception {
		return new String(str.getBytes(ENCODING_JAVA), ENCODING_CORBA);
	}

	public static String corba2java(String str) throws Exception {
		return new String(str.getBytes(ENCODING_CORBA), ENCODING_JAVA);
	}

	// 将 s 进行 BASE64 编码
	public static String Base64Encode(String s) {
		if (s == null) {
			return null;
		}
		// return base64Encoder.encode(s.getBytes());
		return base64Handler.encodeAsString(s.getBytes());
	}

	// 将 BASE64 编码的字符串 s 进行解码
	public static String Base64Decode(String s) {
		if (s == null) {
			return null;
		}
		try {
			// byte[] b = base64Decoder.decodeBuffer(s);
			byte[] b = base64Handler.decode(s);
			return new String(b);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static byte[] Base64DecodeByte(String s) {
		if (s == null) {
			return null;
		}
		try {
			// byte[] b = base64Decoder.decodeBuffer(s);
			byte[] b = base64Handler.decode(s);
			return b;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static int NetStr2Word(String A2) { // 把字符串转为数字
		byte[] TA = A2.getBytes(); // 对单个操作需要 使用这种方法
		int lRet = (GetDelphiByte(TA[0]) << 8) + GetDelphiByte(TA[1]); // 移动一下
		return lRet;
	}

	public static int NetByteWord(byte[] TA) { // 把byte[] 转换为数字
		if (TA.length < 2) {
			return 0;
		}
		int lRet = (GetDelphiByte(TA[0]) << 8) + GetDelphiByte(TA[1]); // 移动一下
		return lRet;
	}

	public static byte[] WordToNetByte(long lWord) {
		byte[] bRet = new byte[2];
		bRet[0] = (byte) ((lWord >> 8) & 0xff);
		bRet[1] = (byte) (lWord & 0xff);
		return bRet;
	}

	public static byte[] longTo8Byte(long number) {
		long temp = number;
		byte[] b = new byte[8];

		for (int i = 0, len = b.length; i < len; i++) {
			b[len - 1 - i] = (byte) (temp & 0xff);// 将最低位保存在最高位
			temp = temp >> 8; // 向右移8位
		}
		return b;
	}

	public static byte[] longto4Byte(long lMsg) { // long 变为4 byte
		byte[] bTem = new byte[4];
		for (int i = 0; i < 4; i++) {
			bTem[i] = (byte) (lMsg >> (8 * (3 - i)) & 0xff);
		}
		return bTem;
	}

	/**
	 * 将Integer变成2 byte 网络序
	 * 
	 * @param iVal
	 * @return
	 */
//	public static byte[] IntTo2Byte(int iVal) {
//		return WordToNetByte(iVal);
//	}

	public static long NetByteLong(byte[] TA) {
		if (TA.length < 4) {
			return 0;
		}
		long lRet = (long) (GetDelphiByte(TA[0]) << 24) + (GetDelphiByte(TA[1]) << 16) + (GetDelphiByte(TA[2]) << 8)
				+ GetDelphiByte(TA[3]);
		return lRet;
	}

	public static byte[] Doubleto4Byte(double fmsg) { // double 变为4 byte
		byte[] bTem = new byte[4];
		long ltemplat = 0;
		ltemplat = Math.round(fmsg * CONST_LONLAT_BL);
		for (int i = 0; i < 4; i++) {
			bTem[i] = (byte) (ltemplat >> (8 * (3 - i)) & 0xFF);
		}
		return bTem;
	}

	public static double BytesToDouble(byte[] TA) {
		double lRet = (double) (GetDelphiByte(TA[0]) << 24) + (GetDelphiByte(TA[1]) << 16) + (GetDelphiByte(TA[2]) << 8)
				+ GetDelphiByte(TA[3]);
		return lRet / CONST_LONLAT_BL;
	}

	public static byte[] Double1000000ToBytes(double dValue) {
		dValue = dValue * 1000000.0;
		int F = 0;
		if (dValue < 0) {
			F = -1;
			dValue = dValue * (-1);
		} else {
			F = 0;
		}
		long lValue = Math.round(dValue);
		if (F < 0) {
			lValue = lValue | 0x80000000;
		} else {
			lValue = lValue & 0x7FFFFFFF;
		}

		byte[] bRet = longto4Byte(lValue);
		return bRet;
	}

	public static int GetDelphiByte(byte bIn) { // 获得在DELPHI中的数值 DELPHI中
												// byte是从0..255 JAVA是总-128..127
		return bIn & 0xff;
	}

	public static byte GetCheckSum(byte s[]) { // UEPP中的校验的计算
		int iRet = GetDelphiByte(s[0]);
		int i;
		for (i = 1; i < s.length; i++) {
			iRet = iRet ^ GetDelphiByte(s[i]);
		}
		return (byte) iRet;
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

	public static byte[] GetFromTwoBytes(byte[] bA, byte[] bB, int iACount, int iBCount) { // 将两个byte数组加到同一个数组里
																							// 都从0开始
		if (iACount > bA.length) {
			return new byte[0];
		}
		if (iBCount > bB.length) {
			return new byte[0];
		}
		byte[] bRet = new byte[iACount + iBCount];
		int i = 0;
		for (i = 0; i < iACount; i++) {
			bRet[i] = bA[i];
		}
		for (i = 0; i < iBCount; i++) {
			bRet[iACount + i] = bB[i];
		}
		return bRet;
	}

	public static String ConvertBytesToStr(byte[] bFrame) { // 从byte[] 转成String
															// 要使用这个方法。new
															// String(bTemp);
		return new String(bFrame);
	}

	public static String GetStrFromBytes(byte[] bFrame, int iStart, int iLength) { // 获得一个String
																					// 从iStart开始
																					// 后的iLength长的数据
		byte[] bTemp = GetBytes(bFrame, iStart, iLength);
		return ConvertBytesToStr(bTemp);
	}

	public static byte[] float10ToBytes(float float10) {
		float10 = float10 * 10;
		long lValue = Math.round(float10);
		byte[] bRet = WordToNetByte(lValue);
		return bRet;
	}

	public static byte[] float100ToBytes(float float100) {
		float100 = float100 * 100;
		long lValue = Math.round(float100);
		byte[] bRet = WordToNetByte(lValue);
		return bRet;
	}

	public static byte[] Speed2MdtSpeed(float fSpeed) {
		fSpeed = fSpeed * CONST_SPEED_BL;
		long lValue = Math.round(fSpeed);
		byte[] bRet = WordToNetByte(lValue);
		return bRet;
	}

	public static float MdtSpeed2Speed(byte[] bSpeed) {
		long lSpeed = NetByteWord(bSpeed);
		float fRet = lSpeed / CONST_SPEED_BL;
		return fRet;
	}

	public static byte[] Direction2MdtDirection(float fDirection) {
		fDirection = fDirection * CONST_DIRECTION_BL;
		long lValue = Math.round(fDirection);
		byte[] bRet = WordToNetByte(lValue);
		return bRet;
	}

	public static float MdtDirection2Direction(byte[] bDirection) {
		long lDirection = NetByteWord(bDirection);
		float fRet = lDirection / CONST_DIRECTION_BL;
		return fRet;
	}

	public static byte[] GetDateTimeBytes(Date inDate) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String date = simpleDateFormat.format(inDate);
		byte[] bRet = new byte[7];
		byte[] bYear = WordToNetByte(Long.parseLong(date.substring(0, 4)));
		for (int i = 0; i < 2; i++) {
			bRet[i] = bYear[i];
		}
		bRet[2] = Byte.parseByte(date.substring(4, 6));
		bRet[3] = Byte.parseByte(date.substring(6, 8));
		bRet[4] = Byte.parseByte(date.substring(8, 10));
		bRet[5] = Byte.parseByte(date.substring(10, 12));
		bRet[6] = Byte.parseByte(date.substring(12, 14));
		return bRet;
	}

	public static Date BytesToDateTime(byte[] bIn) {
		// java.util.Date dTime = new java.util.Date();
		// if (bIn.length != 7) {
		// return dTime;
		// }
		// dTime.setYear(NetByteWord(GetBytes(bIn, 0, 2)));
		// dTime.setMonth(bIn[2]);
		// dTime.setDate(bIn[3]);
		// dTime.setHours(bIn[4]);
		// dTime.setMinutes(bIn[5]);
		// dTime.setSeconds(bIn[6]);
		//
		// return dTime;
		GregorianCalendar cl = new GregorianCalendar();

		return cl.getTime();
	}

	public static byte Fto15(byte bIn) {
		// byte[] bLb=new byte[] {
		byte[] bLib = new byte[] { 0x30, 0x31, 0x32, 0x33, 0x34, 0x35, 0x36, 0x37, 0x38, 0x39, 0x41, 0x42, 0x43, 0x44,
				0x45, 0x46 };
		for (byte b = 0; b <= bLib.length; b++) {
			if (bIn == bLib[b]) {
				return b;
			}
		}
		return -1;
	}

	public static String HexStrToStr(String str) {
		return new String(HexStrToBytes(str.getBytes()));
	}

	public static byte[] HexStrToBytes(byte[] bRcv) {
		int iLen = bRcv.length;
		if (iLen % 2 != 0) {
			return new byte[0];
		}
		int iCount = iLen / 2;
		int in1 = 0;
		int in2 = 0;
		int in3 = 0;
		byte[] bRet = new byte[iCount];
		for (int i = 0; i < iCount; i++) {
			in1 = Fto15(bRcv[i * 2]);
			in2 = Fto15(bRcv[i * 2 + 1]);
			if (in1 < 0 || in2 < 0) {
				return new byte[0];
			}
			in3 = in1 * 16 + in2;
			bRet[i] = (byte) in3;
		}
		return bRet;
	}

	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D",
			"E", "F" };

	public static String ByteToHexString(byte b) {
		int n = b;
		if (n < 0) {
			n = 256 + n;
		}
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	public static String BytesToHexStr(byte[] bData, String eparator) {
		if (bData == null) {
			return "";
		}
		StringBuilder resultSb = new StringBuilder(bData.length * 2);
		for (byte b : bData) {
			resultSb.append(ByteToHexString(b) + eparator);
		}
		return resultSb.toString();
	}

	public static String BytesToHexStr(byte[] bData) {
		return BytesToHexStr(bData, "");
	}

	public static String BytesToHexStr(List<Byte> bData) {
		if (bData == null) {
			return "";
		}
		StringBuilder resultSb = new StringBuilder(bData.size() * 2);
		for (byte b : bData) {
			resultSb.append(ByteToHexString(b));
		}
		return resultSb.toString();
	}

	public static Integer HexByteToInt(byte b) {
		int n = b;
		if (n < 0) {
			n = 256 + n;
		}
		return n;
	}

	/**
	 * 将每个字节转换成8位的二进制字符串(反转：高位变低位，低位变高位)
	 * 
	 * @param byteArray byte数组
	 * @return 字符串
	 */
	public static String ByteArrayToBinaryString(byte[] byteArray) {
		int capacity = byteArray.length * 8;
		StringBuilder sb = new StringBuilder(capacity);
		for (int i = byteArray.length - 1; i >= 0; i--) {
			sb.append(byte2bits(byteArray[i]));
		}
		return sb.toString();
	}

	/**
	 * 将一个byte转换成8位的二进制字符串
	 * 
	 * @param b byte
	 * @return String
	 */
	public static String byte2bits(byte b) {
		int z = b;
		z |= 256;
		String str = Integer.toBinaryString(z);
		int len = str.length();
		return str.substring(len - 8, len);
	}

	public static String StrToHexStr(String value) {
		if (value == null) {
			return "";
		}
		return BytesToHexStr(value.getBytes());
	}

	/**
	 * 默认时间： 1982-10-02 23:00:00
	 * 
	 * @param sDate
	 * @return
	 */
	public static final Date SetDate(String sDate) { // 返回的值
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			if (sDate == null || sDate.trim().equals("")) {
				return sdf.parse("1982-10-02");
			}
			return sdf.parse(sDate);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				return sdf.parse("1982-10-02");
			} catch (ParseException ex) {
				ex.printStackTrace();
				return null;
			}
		}
	}

	public static final Date SetDateTime(String sDate) { // 返回的值
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			if (sDate == null || sDate.trim().equals("")) {
				return sdf.parse("1982-10-02 00:00:00");
			}
			return sdf.parse(sDate);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				return sdf.parse("1982-10-02 00:00:00");
			} catch (ParseException ex) {
				ex.printStackTrace();
				return null;
			}
		}
	}

	public static final java.sql.Date SetSqlDate(String sDate) {
		SimpleDateFormat dateFormat;
		dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // "yyyy-MM-dd"为待转时间字符串的格式
		Date dDATE = null;
		try {
			dDATE = dateFormat.parse(sDate);
		} catch (ParseException ex) {
			ex.printStackTrace();
			java.sql.Date dsqlDate = new java.sql.Date(System.currentTimeMillis());
			return dsqlDate;
		}
		java.sql.Date dsqlDate = new java.sql.Date(dDATE.getTime());
		return dsqlDate;
	}

	public static final java.sql.Date SetSqlDate(Date dDate) {
		dDate = new Date();
		java.sql.Date dRetDate = new java.sql.Date(dDate.getTime());
		return dRetDate;
	}

	public static final java.sql.Time SetSqlTime(Date dDate) {
		java.sql.Time dRetTime = new java.sql.Time(dDate.getTime());
		return dRetTime;
	}

	public static final java.sql.Timestamp SetSqlTimestamp(Date dDate) {
		java.sql.Timestamp dRetTimestamp = java.sql.Timestamp.valueOf(DateTime2Str(dDate));
		return dRetTimestamp;
	}

	/**
	 * 时间格式： 1982-10-02 23:00:00
	 * 
	 * @param
	 * @return
	 */
	public static final String DateTime2Str(Date value) {
		if (value == null) {
			return "";
		}
		try {
			SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			return myFmt.format(value);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static final String Date2Str(Date value) {
		if (value == null) {
			return "";
		}
		try {
			SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd");

			return myFmt.format(value);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static byte[] getSelfNumBytes(String sSelfNum) {
		if (sSelfNum.length() > 12) {
			sSelfNum = sSelfNum.substring(1, 12);
		} else {
			while (sSelfNum.length() < 12) {
				sSelfNum = "0" + sSelfNum;
			}
		}
		String sTemp = "";
		byte[] bSelfNum = new byte[6];
		for (int i = 0; i < 6; i++) {
			sTemp = sSelfNum.substring(i * 2, (i + 1) * 2);
			bSelfNum[i] = Byte.parseByte(sTemp);
		}
		return bSelfNum;
	}

	public static final String trimStr(Object value) {
		if (value == null) {
			return "";
		} else {
			return (String) value;
		}
	}

	public static final String trimDate(Object value) {
		if (value == null) {
			return "";
		}
		Date rDate = new Date();
		String sDefault = "1900-01-01";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			rDate = sdf.parse((String) value);
			return sdf.format(rDate);
		} catch (Exception e) {
			e.printStackTrace();
			return sDefault;
		}
	}

	public static final String trimDate2(Object value) {
		if (value == null) {
			return "";
		}
		Date rDate = new Date();
		String sDefault = "1900-01-01";
		SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");
		try {
			rDate = sdf.parse((String) value);
			return sdf.format(rDate);
		} catch (Exception e) {
			e.printStackTrace();
			return sDefault;
		}
	}

	// 校验 函数
	public static int GetXor(byte s[]) {
		int iRet = s[0];
		int i;
		for (i = 1; i < s.length - 1; i++) {

			iRet = iRet ^ s[i];
		}
		return iRet;
	}

	/**
	 * copyBytes(,,)
	 * 
	 * @param dest
	 * @param iStartIndex
	 * @param src
	 * @return count of the bytes copied.
	 */
	public static int copyBytes(byte[] dest, int iStartIndex, byte[] src) {
		if (null == dest || null == src) {
			return -1;
		}
		int ilen = src.length;
		int ilendesc = dest.length - iStartIndex;
		if (ilendesc < ilen) {
			return -1;
		}
		for (int i = 0; i < ilen; i++) {
			dest[iStartIndex + i] = src[i];
		}

		return ilen;
	}

	///////////////
	public static byte[] sfloat2Bytes(float fValue) {
		fValue = fValue * CONST_SFLOAT_BL;
		long lValue = Math.round(fValue);
		if (lValue < 0) {
			lValue |= 0x8000L;
		}

		byte[] bRet = WordToNetByte(lValue);
		return bRet;
	}

	public static float bytes2Sfloat(byte[] bValue) {
		long lValue = NetByteWord(bValue);
		if ((lValue & 0x8000L) > 0) {
			lValue &= 0x7FFFL;
			lValue *= -1;
		}

		float fRet = lValue / CONST_SFLOAT_BL;
		return fRet;
	}

	///////
	public static float bytes2Float(byte[] bValue) {
		long lValue = NetByteLong(bValue);
		if ((lValue & 0x80000000L) > 0) {
			lValue &= 0x7FFFFFFFL;
			lValue *= -1;
		}

		float fRet = lValue / CONST_FLOAT_BL;
		return fRet;
	}

	public static byte[] float2Bytes(float fValue) {
		fValue = fValue * CONST_FLOAT_BL;
		long lValue = Math.round(fValue);
		if (lValue < 0) {
			lValue |= 0x80000000L;
		}

		byte[] bRet = longto4Byte(lValue);
		return bRet;
	}

	public static String str2MD5(String _str) {
		String resultString = null;
		try {
			resultString = new String(_str);
			MessageDigest md = MessageDigest.getInstance("MD5");
			resultString = BytesToHexStr(md.digest(resultString.getBytes()));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return resultString;
	}

	public static String str2md5(String _str) {
		String resultString = str2MD5(_str);
		if (resultString == null) {
			return null;
		}
		return resultString.toLowerCase();
	}

	/**
	 * int转换成一个byte时，范围为0~255,否则； 转换失败
	 * 
	 * @param ival
	 * @return
	 */
	public static byte intToByte(int ival) {
		if (ival < 0 || ival > 255) {
			ival = 0;
		}
		return (byte) (ival & 0xff);
	}

	/**
	 * 将IP字符串转换成4字节数组，ipstr如：198.125.1.35
	 * 
	 * @param ipStr
	 * @return
	 */
	public static byte[] ipStrTo4Byte(String ipStr) {
		String[] tmp = ipStr.split("\\.");
		byte[] ipByte = new byte[4];
		for (int i = 0; i < ipByte.length; i++) {
			String ip = tmp[i];
			int num = Integer.valueOf(ip);
			if (num > 0 && num < 255) {
				ipByte[i] = intToByte(num);
			} else {
				ipByte[i] = 0;
			}
		}
		return ipByte;
	}

	/**
	 * 获取本机IP,
	 * 
	 * @return
	 */
	public static String getLocalIp() {
		InetAddress addr = null;
		String localIp = "127.0.0.1";
		try {
			addr = InetAddress.getLocalHost();
			localIp = addr.getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		return localIp;
	}

	/**
	 * 获取本机主机名称
	 * 
	 * @return
	 */
	public static String getLocalHostName() {
		String hostName;
		try {
			InetAddress addr = InetAddress.getLocalHost();
			hostName = addr.getHostName();
		} catch (Exception ex) {
			hostName = "";
		}
		return hostName;
	}

	public static String[] getAllLocalHostIp() {
		String[] ret = null;
		try {
			String hostName = getLocalHostName();
			if (hostName.length() > 0) {
				InetAddress[] addrs = InetAddress.getAllByName(hostName);
				if (addrs.length > 0) {
					ret = new String[addrs.length];
					for (int i = 0; i < addrs.length; i++) {
						ret[i] = addrs[i].getHostAddress();
					}
				}
			}

		} catch (Exception ex) {
			ret = null;
		}
		return ret;
	}

	public static byte[] long2Bytes(long num) {
		byte[] byteNum = new byte[8];
		for (int ix = 0; ix < 8; ++ix) {
			int offset = 64 - (ix + 1) * 8;
			byteNum[ix] = (byte) ((num >> offset) & 0xff);
		}
		return byteNum;
	}

	public static byte[] long2BytesL(long num) {
		byte[] byteNum = new byte[8];
		for (int ix = 0; ix < 8; ++ix) {
			int offset = 64 - (ix + 1) * 8;
			byteNum[7 - ix] = (byte) ((num >> offset) & 0xff);
		}
		return byteNum;
	}

	public static long bytes2Long(byte[] byteNum) {
		long num = 0;
		for (int ix = 0; ix < 8; ++ix) {
			num <<= 8;
			num |= (byteNum[ix] & 0xff);
		}
		return num;
	}

	public static byte[] int2Bytes(int num) {
		byte[] byteNum = new byte[4];
		for (int ix = 0; ix < 4; ++ix) {
			int offset = 32 - (ix + 1) * 8;
			byteNum[ix] = (byte) ((num >> offset) & 0xff);
		}
		return byteNum;
	}

	public static byte[] int2BytesL(int num) {
		byte[] byteNum = new byte[4];
		for (int ix = 0; ix < 4; ++ix) {
			int offset = 32 - (ix + 1) * 8;
			byteNum[3 - ix] = (byte) ((num >> offset) & 0xff);
		}
		return byteNum;
	}

	public static byte[] intTo2Bytes(int num) {
		byte[] byteNum = new byte[2];
		for (int ix = 0; ix < 2; ++ix) {
			int offset = 16 - (ix + 1) * 8;
			byteNum[ix] = (byte) ((num >> offset) & 0xff);
		}
		return byteNum;
	}

	public static byte[] intTo2BytesL(int num) {
		byte[] byteNum = new byte[2];
		for (int ix = 0; ix < 2; ++ix) {
			int offset = 16 - (ix + 1) * 8;
			byteNum[1 - ix] = (byte) ((num >> offset) & 0xff);
		}
		return byteNum;
	}

	public static Integer bytes4ToInt(byte[] byteNum) {
		int num = 0;
		for (int ix = 0; ix < 4; ++ix) {
			num <<= 8;
			num |= (byteNum[ix] & 0xff);
		}
		return num;
	}

	public static Integer bytes2ToInt(byte[] byteNum) {
		int num = 0;
		for (int ix = 0; ix < 2; ++ix) {
			num <<= 8;
			num |= (byteNum[ix] & 0xff);
		}
		return num;
	}

}
