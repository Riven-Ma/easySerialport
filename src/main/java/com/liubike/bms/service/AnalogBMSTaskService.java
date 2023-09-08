package com.liubike.bms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liubike.task.AbstractTask;

@Service
public class AnalogBMSTaskService extends AbstractTask {

	@Autowired
	private BmsReceiveHeadService bmsReceiveHeadService;

	@Override
	public void dual(Long now) {
		
	}

}
