package com.iremote.common.push.vo;

public class Notification {

	private String alert;
	private Android android = new Android();
	private Ios ios = new Ios() ;
	
	public String getAlert() {
		return alert;
	}
	public void setAlert(String alert) {
		this.alert = alert;
	}
	public Android getAndroid() {
		return android;
	}
	public void setAndroid(Android android) {
		this.android = android;
	}
	public Ios getIos() {
		return ios;
	}
	public void setIos(Ios ios) {
		this.ios = ios;
	}
	
}
