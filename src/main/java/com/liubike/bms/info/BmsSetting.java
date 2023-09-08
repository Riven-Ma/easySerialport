package com.liubike.bms.info;

public class BmsSetting {
	
	private String bmsSn="03LC30A213180409";
	private Integer reduceTime=60;
	private Integer batT=30;
	private Integer perReduce=1;
	private Integer batV=50;
	private Integer soh=90;
	private Integer batQ=90;
	
	
	public String getBmsSn() {
		return bmsSn;
	}
	public void setBmsSn(String bmsSn) {
		this.bmsSn = bmsSn;
	}
	public Integer getReduceTime() {
		return reduceTime;
	}
	public void setReduceTime(Integer reduceTime) {
		this.reduceTime = reduceTime;
	}
	public Integer getBatT() {
		return batT;
	}
	public void setBatT(Integer batT) {
		this.batT = batT;
	}
	public Integer getPerReduce() {
		return perReduce;
	}
	public void setPerReduce(Integer perReduce) {
		this.perReduce = perReduce;
	}
	public Integer getBatV() {
		return batV;
	}
	public void setBatV(Integer batV) {
		this.batV = batV;
	}
	public Integer getSoh() {
		return soh;
	}
	public void setSoh(Integer soh) {
		this.soh = soh;
	}
	public Integer getBatQ() {
		return batQ;
	}
	public void setBatQ(Integer batQ) {
		this.batQ = batQ;
	}
	
	
	

}
