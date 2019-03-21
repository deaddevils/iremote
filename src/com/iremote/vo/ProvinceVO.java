package com.iremote.vo;

import java.util.List;

public class ProvinceVO {

	private Integer provinceid;
	private String name;
	private List<CityVO> cities;
	public Integer getProvinceid() {
		return provinceid;
	}
	public void setProvinceid(Integer provinceid) {
		this.provinceid = provinceid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<CityVO> getCities() {
		return cities;
	}
	public void setCities(List<CityVO> cities) {
		this.cities = cities;
	}
	
	
}
