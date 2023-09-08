package com.liubike.controler;

import com.liubike.bms.encode.AbstractBMS;
import com.liubike.bms.encode.BMSCmdCode;
import com.liubike.bms.service.BmsReceiveHeadService;
import com.liubike.spring.SpringBinUtil;
import com.liubike.view.BMSSet;

public class BMSSetControler extends BMSSet{
	
	private BmsReceiveHeadService bmsReceiveHeadService;

	@Override
	public void initControl() {
		bmsReceiveHeadService=(BmsReceiveHeadService) SpringBinUtil.getBean(BmsReceiveHeadService.class);
	}

	@Override
	public void binding() {
			
		conmitBT.addActionListener(e->{
			setBms();
		});
	}
	
	

	@SuppressWarnings("unchecked")
	private void setBms() {
		String bmsSn=bmsSnTF.getText();
		String reduceTime=reduceTimeTF.getText();
		String perReduce=perReduceTF.getText();
		String batT=batTTF.getText();
		String batV=batVTF.getText();
		String soh=sohTF.getText();
		String batQ=batQTF.getText();
		
		((AbstractBMS<Integer>) bmsReceiveHeadService.getCmdEncodeEntity(BMSCmdCode.BAT_TEMP)).setValue(Integer.parseInt(batT));;
		((AbstractBMS<String>)bmsReceiveHeadService.getCmdEncodeEntity(BMSCmdCode.BAT_SN)).setValue(bmsSn);
		((AbstractBMS<Integer>)bmsReceiveHeadService.getCmdEncodeEntity(BMSCmdCode.BAT_Q)).setValue(Integer.parseInt(batQ));
		((AbstractBMS<Integer>)bmsReceiveHeadService.getCmdEncodeEntity(BMSCmdCode.BAT_V)).setValue(Integer.parseInt(batV));
		((AbstractBMS<Integer>)bmsReceiveHeadService.getCmdEncodeEntity(BMSCmdCode.BAT_SOH)).setValue(Integer.parseInt(soh));
	}

}
