package com.liubike.view.enums;

public enum DecodeFormatEnum implements ComboBoxItemInterface{

	ASCII(0), UTF8(1), GBK(2);

	private Integer val;

	@Override
	public Integer getVal() {
		return val;
	}

	DecodeFormatEnum(Integer val) {
		this.val = val;
	}

	@Override
	public String toString() {
		return this.name();
	}

	
	
}
