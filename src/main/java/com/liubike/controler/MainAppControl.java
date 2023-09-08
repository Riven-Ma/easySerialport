package com.liubike.controler;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;

import javax.annotation.PostConstruct;
import javax.swing.AbstractButton;
import javax.swing.JRadioButton;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fazecast.jSerialComm.SerialPort;
import com.liubike.common.broadcast.Broadcast;
import com.liubike.common.broadcast.BroadcastInterface;
import com.liubike.common.broadcast.BroadcastManager;
import com.liubike.common.code.DecodeTool;
import com.liubike.common.code.TProtocolTool;
import com.liubike.serial.service.SerialPortService;
import com.liubike.view.MainApp;
import com.liubike.view.enums.CheckBitEmnu;
import com.liubike.view.util.ComboBoxItem;
import com.liubike.view.util.SerialPortComboBoxItem;

@Component
public class MainAppControl extends MainApp {

	@Autowired
	private SerialPortService serialPortService;

	@PostConstruct
	@Override
	public void initControl() {
		refishSerialPort();
		BroadcastManager.getIns().register(new MainAppBoadcastListener());
	}

	private void refishSerialPort() {
		if (serialPortService == null) {
			return;
		}
		SerialPort[] serialPorts = serialPortService.serialPortInfo();
		if (serialPorts == null || serialPorts.length == 0) {
			return;
		}
		selectSerialCB.removeAllItems();
		for (SerialPort item : serialPorts) {
			SerialPortComboBoxItem selectSerial = new SerialPortComboBoxItem(item);

			selectSerialCB.addItem(selectSerial);
		}
	}

	private SerialPort getSelectSerialPort() {
		Object obj = selectSerialCB.getSelectedItem();
		if (obj == null) {
			return null;
		}

		SerialPort serialPort = (SerialPort) ((SerialPortComboBoxItem) obj).getType();
		Integer baudRat = (Integer) baudRateCB.getSelectedItem();
		serialPort.setBaudRate(baudRat);
		Integer parity = ((CheckBitEmnu) ((ComboBoxItem<?>) checkBitCB.getSelectedItem()).getType()).getVal();
		serialPort.setParity(parity); // 奇偶校验位
		Integer numData = (Integer) dataBitCB.getSelectedItem();
		serialPort.setNumDataBits(numData); // 数据位
		Integer numStop = (Integer) stopBitCB.getSelectedItem();
		serialPort.setNumStopBits(numStop); // 停止位

//		serialPort.setDTR();
//		serialPort.setFlowControl(SerialPort.FLOW_CONTROL_DISABLED);
		return serialPort;
	}

	private void setSerialPortEnable(Boolean b) {
		selectSerialCB.setEnabled(b);
		refushSelectSerialBT.setEnabled(b);
		baudRateCB.setEnabled(b);
		checkBitCB.setEnabled(b);
		stopBitCB.setEnabled(b);
		dataBitCB.setEnabled(b);
	}

	private void onclickOpenSerialBT() {
		if (serialPortService.isOpen()) {
			if (!serialPortService.close()) {
				return;
			}
			setSerialPortEnable(true);
			openSerialBT.setText("打开串口");
			return;
		}

		SerialPort serialPort = getSelectSerialPort();
		serialPortService.setSerialPort(serialPort);

		if (serialPortService.open()) {
			openSerialBT.setText("关闭串口");
			setSerialPortEnable(false);
		} else {
			setSerialPortEnable(true);
			openSerialBT.setText("打开串口");
		}
	}

	@Override
	public void binding() {
		refushSelectSerialBT.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				refishSerialPort();
			}
		});

		openSerialBT.addActionListener(e -> {
			onclickOpenSerialBT();
		});

		cleanReceiveBT.addActionListener(e -> {
			receiveTA.setText(null);
		});

		sendBT.addActionListener(e -> {
			String txt = sendTA.getText();
			txt = txt.toUpperCase();
			txt = txt.replace(" ", "");
			byte[] sendByte = TProtocolTool.HexStrToBytes(txt.getBytes());
			serialPortService.send(sendByte);
		});

		showLineNumCBOX.addActionListener(e -> {
			superReceive.setShowLine(showLineNumCBOX.isSelected());
		});

		bmsSetBT.addActionListener(e -> {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						BMSSetControler window = new BMSSetControler();
						window.show();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		});
	}

	public class MainAppBoadcastListener implements BroadcastInterface {

		@Override
		public int listenerType() {
			Integer type = BroadcastInterface.RECEIVE_SERIA_PORT_DATA;
			if (showSendCBox.isSelected()) {
				// multiple type used "|"
				type |= BroadcastInterface.SEND_SERIA_PORT_DATA;
			}
			return type;
		}

		@Override
		public void receive(Broadcast boadcast) {

			Integer messageType = boadcast.getType();
			byte[] data = (byte[]) boadcast.getMessage();
			if (BroadcastInterface.RECEIVE_SERIA_PORT_DATA == (BroadcastInterface.RECEIVE_SERIA_PORT_DATA
					& messageType)) {
				dualReceiveData(data);
			}

			if (BroadcastInterface.SEND_SERIA_PORT_DATA == (BroadcastInterface.SEND_SERIA_PORT_DATA & messageType)) {
				dualSendData(data);
			}

		}

		private StringBuffer bf = new StringBuffer();

		private void dualReceiveData(byte[] data) {
			bf.setLength(0);
			bf.append( data.length + " byte r<--");

			Enumeration<AbstractButton> allRadioButton = showModelRadioGroup.getElements();
			while (allRadioButton.hasMoreElements()) {
				JRadioButton temp = (JRadioButton) allRadioButton.nextElement();
				if (temp.isSelected()) {
					if (temp.equals(hexadecimalRB)) {
						bf.append(TProtocolTool.BytesToHexStr(data, " "));
					} else {
						try {
							// TODO
							bf.append(new String(data, "utf-8"));
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}
					}
					bf.append("\n");
					receiveTA.append(bf.toString());
				}

			}
		}

		private void dualSendData(byte[] data) {
			bf.setLength(0);
			bf.append( data.length + " byte w-->");
			bf.append(TProtocolTool.BytesToHexStr(data, " "));
			bf.append("\n");
			receiveTA.append(bf.toString());
		}

	}

}
