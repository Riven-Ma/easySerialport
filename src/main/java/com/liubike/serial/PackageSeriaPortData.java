package com.liubike.serial;

import java.util.Iterator;
import java.util.LinkedList;

import com.liubike.common.MyThreadPool;
import com.liubike.common.TimerUtil;
import com.liubike.common.broadcast.BroadcastInterface;
import com.liubike.common.broadcast.BroadcastManager;

public class PackageSeriaPortData {

	private static final PackageSeriaPortData ins = new PackageSeriaPortData();

	private PackageSeriaPortData() {
		super();
		MyThreadPool.getThreadPool().execute(new BytesBuffer());
	}

	public static PackageSeriaPortData getIns() {
		return ins;
	}

	private LinkedList<byte[]> byteBuffer = new LinkedList<byte[]>();

	private static Boolean ready = false;

	private Long lastTime = 0L;
	

	private TimerUtil timerUtil = TimerUtil.getIns();

	private BroadcastManager broadcastManager = BroadcastManager.getIns();
	
	public void lastTimeAdd(Long timeMillis) {
		lastTime = timerUtil.getCurrentTime() + timeMillis;
	}

	public void addToBuffer(byte[] message) {
		lastTimeAdd(20L);
		synchronized (byteBuffer) {
			try {
				byteBuffer.addLast(message);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		ready = true;
	}

	class BytesBuffer implements Runnable {

		private Object lock = new Object();

		@Override
		public void run() {
			while (true) {
				try {

					while (true) {
						if (ready && lastTime < timerUtil.getCurrentTime()) {
							break;
						}
						synchronized (lock) {
							lock.wait(0,50000);
							continue;
						}
						
						
					}

					synchronized (byteBuffer) {
						int size = byteBuffer.size();
//						System.out.println("byteBuffer.size():" + size);
						if (size == 0) {
							continue;
						}
						int len = 0;
						Iterator<byte[]> its = byteBuffer.iterator();
						while (its.hasNext()) {
							byte[] item = its.next();
							len += item.length;
						}
						byte[] data = new byte[len];
						its = byteBuffer.iterator();
						int index = 0;
						while (its.hasNext()) {
							byte[] item = its.next();
							System.arraycopy(item, 0, data, index, item.length);
							index += item.length;
						}
						broadcastManager.send(data, BroadcastInterface.RECEIVE_SERIA_PORT_DATA);
						byteBuffer.clear();
					}

				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					ready = false;
				}
			}

		}

	}

}
