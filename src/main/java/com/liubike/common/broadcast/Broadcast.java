package com.liubike.common.broadcast;

public class Broadcast{
	

	public Broadcast(Object message, Integer type) {
		super();
		this.message = message;
		this.type = type;
	}



	private Object message;

	public Object getMessage() {
		return message;
	}

	public void setMessage(Object message) {
		this.message = message;
	}
	
	
	
	private Integer type;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	
	
	
}
