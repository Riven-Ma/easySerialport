package com.liubike.view.enums;

public enum SeparateDisplayEnum implements ComboBoxItemInterface{
	CTRL("\\r\\n", 0), CR("\\r", 1), LF("\\n", 2);

	private String dsc;

	private Integer val;

	public String getDsc() {
		return dsc;
	}

	@Override
	public Integer getVal() {
		return val;
	}

	SeparateDisplayEnum(String dsc, Integer val) {
		this.dsc = dsc;
		this.val = val;
	}

	@Override
	public String toString() {
		return this.name() + "(" + this.dsc + ")";
	}
	
}
