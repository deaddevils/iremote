package com.iremote.vo;

import java.util.ArrayList;
import java.util.List;

public class NotifyUserList {

	private List<Integer> messageUser = new ArrayList<Integer>();
	private List<Integer> NotificationUser = new ArrayList<Integer>();
	private List<Integer> smsUser = new ArrayList<Integer>();
	private List<Integer> callUser = new ArrayList<Integer>();
	private List<Integer> mailUser = new ArrayList<Integer>();

	public synchronized List<Integer> getMessageUser() {
		return messageUser;
	}
	public synchronized void setMessageUser(List<Integer> messageUser) {
		this.messageUser = messageUser;
	}
	public synchronized List<Integer> getNotificationUser() {
		return NotificationUser;
	}
	public synchronized void setNotificationUser(List<Integer> notificationUser) {
		NotificationUser = notificationUser;
	}
	public synchronized List<Integer> getSmsUser() {
		return smsUser;
	}
	public synchronized void setSmsUser(List<Integer> smsUser) {
		this.smsUser = smsUser;
	}
	public synchronized List<Integer> getCallUser() {
		return callUser;
	}
	public synchronized void setCallUser(List<Integer> callUser) {
		this.callUser = callUser;
	}

	public synchronized List<Integer> getMailUser() {
		return mailUser;
	}

	public synchronized void setMailUser(List<Integer> mailUser) {
		this.mailUser = mailUser;
	}
}
