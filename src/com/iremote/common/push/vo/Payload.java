package com.iremote.common.push.vo;

public class Payload {

	private String platform = "all";
	private Audience audience;
	private Notification notification;
	private Message message;
	private Options options = new Options();
	public String getPlatform() {
		return platform;
	}
	public Audience getAudience() {
		return audience;
	}
	public void setAudience(Audience audience) {
		this.audience = audience;
	}
	public Notification getNotification() {
		return notification;
	}
	public void setNotification(Notification notification) {
		this.notification = notification;
	}
	public Message getMessage() {
		return message;
	}
	public void setMessage(Message message) {
		this.message = message;
	}
	public Options getOptions() {
		return options;
	}

	@Override
	public Payload clone(){
		try {
			return (Payload)super.clone();
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
}
