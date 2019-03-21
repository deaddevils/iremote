package com.iremote.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="card")
public class Card
{
	private int cardid ;
	private Integer thirdpartid ;
	private String sha1key ;
	private int cardsequence;
	private Integer cardtype;//0x1 MF; 0x2 ID card; 0xf Other 
	
	
	@Id    
	@GeneratedValue(strategy = GenerationType.IDENTITY)   
	@GenericGenerator(name = "generator", strategy = "increment")   
    @Column(name = "cardid")  
	public int getCardid()
	{
		return cardid;
	}
	public void setCardid(int cardid)
	{
		this.cardid = cardid;
	}
	public Integer getThirdpartid()
	{
		return thirdpartid;
	}
	public void setThirdpartid(Integer thirdpartid)
	{
		this.thirdpartid = thirdpartid;
	}

	public int getCardsequence()
	{
		return cardsequence;
	}
	public void setCardsequence(int cardsequence)
	{
		this.cardsequence = cardsequence;
	}
	public String getSha1key()
	{
		return sha1key;
	}
	public void setSha1key(String sha1key)
	{
		this.sha1key = sha1key;
	}
	public Integer getCardtype() {
		return cardtype;
	}
	public void setCardtype(Integer cardtype) {
		this.cardtype = cardtype;
	}
	
}
