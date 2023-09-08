package com.liubike.task;

public interface TaskInterface {

	Long getLastTime();

	void setLastTime(Long lastTime);

	Long getNextTime();

	void setNextTime(Long nextTime);
	
	void dual(Long now);
	
	Long getInterval();
	
	Boolean isEnabled();

}
