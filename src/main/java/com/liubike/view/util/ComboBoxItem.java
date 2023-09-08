package com.liubike.view.util;

public  class ComboBoxItem <T> {
	
	public ComboBoxItem(T t) {
		super();
		this.type=t;
	
	}
	
	
	
	protected T type;

	public T getType() {
		return type;
	}
	

	
	@Override
	public String toString() {
		return type.toString();
	}



}
