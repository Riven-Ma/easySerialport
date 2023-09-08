package com.liubike.serial.service;

import org.springframework.stereotype.Service;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.liubike.common.broadcast.BroadcastInterface;
import com.liubike.common.broadcast.BroadcastManager;
import com.liubike.serial.PackageSeriaPortData;

@Service
public class SerialPortService {

	private static final String macosCommPort = "/dev/tty";

	private final String OS = System.getProperty("os.name").toLowerCase();

	private SerialPort serialPort;

	private final SerialPortDataListener dataListener = new DataListener();
	
	private BroadcastManager broadcastManager=  BroadcastManager.getIns();

	public SerialPort[] serialPortInfo() {
		SerialPort[] serialPortArrray = SerialPort.getCommPorts();
		if (!OS.contains("mac") || serialPortArrray == null || (serialPortArrray.length % 2 == 1)) {
			return serialPortArrray;
		}
		SerialPort[] serialPortsFilter = new SerialPort[serialPortArrray.length / 2];
		int index = 0;
		for (int i = 0; i < serialPortArrray.length; i++) {
			if (serialPortArrray[i].getSystemPortPath().contains(macosCommPort)) {
				serialPortsFilter[index] = serialPortArrray[i];
				index++;
			}
		}
		return serialPortsFilter;
	}

	public Boolean isOpen() {
		if (serialPort != null && serialPort.isOpen()) {
			return true;
		}
		return false;
	}

	public Boolean close() {
		if (serialPort != null && serialPort.closePort()) {
			serialPort = null;
			return true;
		}
		return false;
	}

	public Boolean open() {
		if (serialPort == null) {
			return false;
		}
		SerialPort.addShutdownHook(new Thread() {
			public void run() {
				System.out.println("\nRunning shutdown hook");
			}
		});
		serialPort.allowElevatedPermissionsRequest();

		if (serialPort.openPort(10)) {
			serialPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING, 10, 10);
			// 设置监听
			serialPort.addDataListener(dataListener);
			return true;
		}
		return false;
	}

	public void setSerialPort(SerialPort serialPort) {
		this.serialPort = serialPort;
	}

	public int send(byte[] data) {
		if (serialPort == null || !serialPort.isOpen()) {
			return -1;
		}
		Integer res = serialPort.writeBytes(data, data.length);
		if(res>0) {
			broadcastManager.send(data, BroadcastInterface.SEND_SERIA_PORT_DATA);
		}
		return res;
	}

	public class DataListener implements SerialPortDataListener {
		private PackageSeriaPortData queue = PackageSeriaPortData.getIns();

		@Override
		public int getListeningEvents() {
			return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
		}

		Long a=System.currentTimeMillis();
		@Override
		public void serialEvent(SerialPortEvent event) {
			Long b=System.currentTimeMillis();
//			System.out.println("==============="+(b-a));
			a=b;
			queue.lastTimeAdd(20L);
			byte[] newData = event.getReceivedData();
			queue.addToBuffer(newData);
			
			
		}

	}

}
