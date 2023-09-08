package com.liubike.view.enums;

public enum CheckBitEmnu implements ComboBoxItemInterface{

	None("无", 0), Odd("奇", 1), Even("偶", 2);

	private String dsc;

	private Integer val;

	public String getDsc() {
		return dsc;
	}

	@Override
	public Integer getVal() {
		return val;
	}

	CheckBitEmnu(String dsc, Integer val) {
		this.dsc = dsc;
		this.val = val;
	}

	@Override
	public String toString() {
		return this.name() + "(" + this.dsc + ")";
	}
	


}
