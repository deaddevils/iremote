package com.iremote.infraredcode.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class CodeLiberayVo implements Serializable {

	private static final long serialVersionUID = -4126381834110935289L;
	
	private int[][] codeliberay;
	private int productorindex[][] ;
	private Map<String , Integer> productorIndexMap = new HashMap<String , Integer>();
	private Map<String , RemoteControllerCode[]> productorRemoteCode = new HashMap<String , RemoteControllerCode[]>();
	
	public int[][] getCodeliberay() {
		return codeliberay;
	}
	public void setCodeliberay(int[][] codeliberay) {
		this.codeliberay = codeliberay;
	}
	public int[][] getProductorindex() {
		return productorindex;
	}
	public void setProductorindex(int[][] productorindex) {
		this.productorindex = productorindex;
	}
	public Map<String, Integer> getProductorIndexMap() {
		return productorIndexMap;
	}
	public void setProductorIndexMap(Map<String, Integer> productorIndexMap) {
		this.productorIndexMap = productorIndexMap;
	}
	public Map<String, RemoteControllerCode[]> getProductorRemoteCode() {
		return productorRemoteCode;
	}
	public void setProductorRemoteCode(
			Map<String, RemoteControllerCode[]> productorRemoteCode) {
		this.productorRemoteCode = productorRemoteCode;
	}
	
}
