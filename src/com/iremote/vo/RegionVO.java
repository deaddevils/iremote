package com.iremote.vo;

import java.util.List;


public class RegionVO {
	private Integer regionid;
	private String name;
	private List<ProvinceVO> provinces;
	public Integer getRegionid() {
		return regionid;
	}
	public void setRegionid(Integer regionid) {
		this.regionid = regionid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<ProvinceVO> getProvinces() {
		return provinces;
	}
	public void setProvinces(List<ProvinceVO> provinces) {
		this.provinces = provinces;
	}
	
	
}
