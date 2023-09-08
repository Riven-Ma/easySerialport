package com.liubike.bms.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liubike.bms.encode.AbstractBMS;
import com.liubike.bms.encode.BatQ;
import com.liubike.bms.encode.BatV;
import com.liubike.bms.encode.Process;
import com.liubike.bms.encode.Sn;
import com.liubike.bms.encode.Soh;
import com.liubike.bms.encode.Temp;
import com.liubike.bms.encode.Ver;
import com.liubike.receive.service.ReceiveHandleInterface;
import com.liubike.receive.service.ReceiveHandleService;
import com.liubike.serial.service.SerialPortService;
import com.liubike.serialize.Encoder;

@Service
public class BmsReceiveHeadService implements ReceiveHandleInterface{
	
	private static final Map<Byte, AbstractBMS<?>> encodeCmd=new HashMap<Byte, AbstractBMS<?>>();
	
	private static final byte[] head=new byte[] {0x3A,0X16};
	
	private static final Encoder encoder=new Encoder();
	
	@Autowired
	private ReceiveHandleService receiveHandleService;
	@Autowired
	private SerialPortService serialPortService;
	
	@PostConstruct
	public void init() {
		receiveHandleService.register(this);
		regist(new BatQ());
		regist(new BatV());
		regist(new Sn());
		regist(new Soh());
		regist(new Temp());
		regist(new Ver());
	}
	
	private void regist(AbstractBMS<?> entity) {
		encodeCmd.put(entity.getCmd(), entity);
	}
	
	public AbstractBMS<?> getCmdEncodeEntity(Byte key) {
		return encodeCmd.get(key);
	}

	@Override
	public byte[] head() {
		return head;
	}
	
	
	private final Process process=new Process();

	@Override
	public void receiveData(byte[] data, Integer headIndex) throws Exception {
		byte cmd=data[headIndex+2];
		AbstractBMS<?> entity=encodeCmd.get(cmd);
		if(entity==null) {
			return;
		}
		byte[] dataBytes=encoder.encode(entity,process);
		serialPortService.send(dataBytes);
	}
	
	

}
