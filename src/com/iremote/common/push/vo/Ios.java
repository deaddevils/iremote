package com.iremote.common.push.vo;

import com.alibaba.fastjson.annotation.JSONField;

public class Ios {

	private String sound = "WindowsLogonSound.caf";
	private String badge = "+1";
	@JSONField(name="content-available")
	private String content_available = "1";
	private Object extras = new Extras();
	public String getSound() {
		return sound;
	}
	public void setSound(String sound) {
		this.sound = sound;
	}
	public String getBadge() {
		return badge;
	}
	public void setBadge(String badge) {
		this.badge = badge;
	}
	public Object getExtras() {
		return extras;
	}
	public void setExtras(Object extras) {
		this.extras = extras;
	}

	public String getContent_available() {
		return content_available;
	}

	public void setContent_available(String content_available) {
		this.content_available = content_available;
	}
}
