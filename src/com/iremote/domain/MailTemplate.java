//package com.iremote.domain;
//
//import org.hibernate.annotations.GenericGenerator;
//
//import javax.persistence.*;
//
//@Entity
//@Table(name="mailtemplate")
//public class MailTemplate {
//
//	private int mailtemplateid;
//	private String key;
//	private String language;
//	private int platform;
//	private String host;
//	private String port;
//	private String fromaddress;
//	private String toaddress;
//	private String userName;
//	private String passWord;
//	//Authentication
//	private boolean validate;
//	private String subject;
//	private String content;
//
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@GenericGenerator(name = "generator", strategy = "increment")
//    @Column(name = "mailtemplateid")
//
//	public int getMailtemplateid() {
//		return mailtemplateid;
//	}
//
//	public void setMailtemplateid(int mailtemplateid) {
//		this.mailtemplateid = mailtemplateid;
//	}
//
//	public String getKey() {
//		return key;
//	}
//
//	public void setKey(String key) {
//		this.key = key;
//	}
//
//	public String getLanguage() {
//		return language;
//	}
//
//	public void setLanguage(String language) {
//		this.language = language;
//	}
//
//	public int getPlatform() {
//		return platform;
//	}
//
//	public void setPlatform(int platform) {
//		this.platform = platform;
//	}
//
//	public String getHost() {
//		return host;
//	}
//
//	public void setHost(String host) {
//		this.host = host;
//	}
//
//	public String getPort() {
//		return port;
//	}
//
//	public void setPort(String port) {
//		this.port = port;
//	}
//
//	public String getFromaddress() {
//		return fromaddress;
//	}
//
//	public void setFromaddress(String fromaddress) {
//		this.fromaddress = fromaddress;
//	}
//
//	public String getToaddress() {
//		return toaddress;
//	}
//
//	public void setToaddress(String toaddress) {
//		this.toaddress = toaddress;
//	}
//
//	public String getUserName() {
//		return userName;
//	}
//
//	public void setUserName(String userName) {
//		this.userName = userName;
//	}
//
//	public String getPassWord() {
//		return passWord;
//	}
//
//	public void setPassWord(String passWord) {
//		this.passWord = passWord;
//	}
//
//	public boolean isValidate() {
//		return validate;
//	}
//
//	public void setValidate(boolean validate) {
//		this.validate = validate;
//	}
//
//	public String getSubject() {
//		return subject;
//	}
//
//	public void setSubject(String subject) {
//		this.subject = subject;
//	}
//
//	public String getContent() {
//		return content;
//	}
//
//	public void setContent(String content) {
//		this.content = content;
//	}
//}
