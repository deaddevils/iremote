package com.iremote.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="messagetemplate")
public class MessageTemplate {

	private int messagetemplateid;
	private String key;
	private String language;
	private int platform;
	private String value;
	private String alitemplatecode;
	private String alitemplateparam;
	
	@Id    
	@GeneratedValue(strategy = GenerationType.IDENTITY)   
	@GenericGenerator(name = "generator", strategy = "increment")   
    @Column(name = "messagetemplateid")  
	public int getMessagetemplateid() {
		return messagetemplateid;
	}
	public void setMessagetemplateid(int messagetemplateid) {
		this.messagetemplateid = messagetemplateid;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public int getPlatform() {
		return platform;
	}
	public void setPlatform(int platform) {
		this.platform = platform;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getAlitemplatecode()
	{
		return alitemplatecode;
	}
	public void setAlitemplatecode(String alitemplatecode)
	{
		this.alitemplatecode = alitemplatecode;
	}
	public String getAlitemplateparam()
	{
		return alitemplateparam;
	}
	public void setAlitemplateparam(String alitemplateparam)
	{
		this.alitemplateparam = alitemplateparam;
	}
}
