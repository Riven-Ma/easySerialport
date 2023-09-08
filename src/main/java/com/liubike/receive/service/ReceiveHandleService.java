package com.liubike.receive.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import com.liubike.common.broadcast.Broadcast;
import com.liubike.common.broadcast.BroadcastInterface;
import com.liubike.common.broadcast.BroadcastManager;
import com.liubike.common.code.TProtocolTool;

@Service
public class ReceiveHandleService implements BroadcastInterface {

	private static final List<ReceiveHandleInterface> list = new ArrayList<ReceiveHandleInterface>();

	@PostConstruct
	public void init() {
		BroadcastManager.getIns().register(this);
	}

	public void register(ReceiveHandleInterface value) {
		list.add(value);
	}

	@SuppressWarnings("finally")
	@Override
	public void receive(Broadcast boadcast) {
		byte[] data = (byte[]) boadcast.getMessage();
		Iterator<ReceiveHandleInterface> its = list.iterator();
		while (its.hasNext()) {
			ReceiveHandleInterface it = its.next();
			byte[] head = it.head();
			int headLen = head.length;
			int datalen = data.length;
			if (headLen >= datalen) {
				continue;
			}

			Integer headFirstByteIndex = TProtocolTool.findBytes(data, head);

			try {
				if (headFirstByteIndex != -1) {
					it.receiveData(data, headFirstByteIndex);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				return;
			}

		}
	}

	@Override
	public int listenerType() {
		return BroadcastInterface.RECEIVE_SERIA_PORT_DATA;
	}

}
