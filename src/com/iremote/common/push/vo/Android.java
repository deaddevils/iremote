package com.iremote.common.push.vo;

public class Android {

	private String title;
	private Object extras = new Extras();;
	private int builder_id;

	public int getBuilder_id() {
		return builder_id;
	}
	public void setBuilder_id(int builder_id) {
		this.builder_id = builder_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Object getExtras() {
		return extras;
	}
	public void setExtras(Object extras) {
		this.extras = extras;
	}
}
