package com.liubike.view.util;

import com.fazecast.jSerialComm.SerialPort;

public class SerialPortComboBoxItem extends ComboBoxItem<SerialPort>{

	public SerialPortComboBoxItem(SerialPort t) {
		super(t);
	}

	
	@Override
	public String toString() {
		return type.getSystemPortName();//+"_"+type.getPortLocation();
	}


}
