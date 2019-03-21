package com.iremote.asiainfo.vo;

public class StatusTranslate {

	private String action ;
	private String name ;
	private int value ;
	
	public StatusTranslate(String action , String name, int value) {
		super();
		this.action = action ;
		this.name = name;
		this.value = value;
	}
	public String getName() {
		return name;
	}
	public int getValue() {
		return value;
	}
	public String getAction() {
		return action;
	}
	
	
}
