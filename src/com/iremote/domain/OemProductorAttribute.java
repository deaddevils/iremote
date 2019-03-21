package com.iremote.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.struts2.json.annotations.JSON;

import com.alibaba.fastjson.annotation.JSONField;

@Entity
@Table(name="oemproductorattribute")
public class OemProductorAttribute 
{
	private int oemproductorattributeid;
	private OemProductor oemproductor ;
	private String code;
	private String value ;
	
	@Id
	public int getOemproductorattributeid() {
		return oemproductorattributeid;
	}
	public void setOemproductorattributeid(int oemproductorattributeid) {
		this.oemproductorattributeid = oemproductorattributeid;
	}

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	@JSONField(serialize = false)
	@JSON(serialize=false)
	@ManyToOne(targetEntity=OemProductor.class,cascade={CascadeType.DETACH},fetch=FetchType.LAZY)           
	@JoinColumn(name="oemproductorid",referencedColumnName="oemproductorid",nullable=false)
	public OemProductor getOemproductor() {
		return oemproductor;
	}
	public void setOemproductor(OemProductor oemproductor) {
		this.oemproductor = oemproductor;
	}
}
