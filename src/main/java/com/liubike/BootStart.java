package com.liubike;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import com.liubike.common.MyThreadPool;
import com.liubike.common.broadcast.BroadcastRun;

/**
 * @author riven
 */
@SpringBootApplication
public class BootStart {

	

	public static void main(String[] args) {
		SpringApplicationBuilder builder = new SpringApplicationBuilder(BootStart.class);
		builder.headless(false).run(args);
		MyThreadPool.getThreadPool().execute(new BroadcastRun());
		MyThreadPool.getThreadPool().execute(new BroadcastRun());
		
	
	}
}
