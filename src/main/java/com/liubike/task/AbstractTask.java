package com.liubike.task;

public abstract class AbstractTask implements TaskInterface {

	protected Long lastTime = 0l;
	protected Long nextTime = 0l;
	protected Long interval = 0l;
	private Boolean enabled = true;

	public Long getLastTime() {
		return lastTime;
	}

	public void setLastTime(Long lastTime) {
		this.lastTime = lastTime;
	}

	public Long getNextTime() {
		return nextTime;
	}

	public void setNextTime(Long nextTime) {
		this.nextTime = nextTime;
	}

	public Long getInterval() {
		return interval;
	}

	public void setInterval(Long interval) {
		this.interval = interval;
	}

	public Boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

}
