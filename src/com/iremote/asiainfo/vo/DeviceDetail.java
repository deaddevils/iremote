package com.iremote.asiainfo.vo;

public class DeviceDetail {
	private Object[] controlList= new Object[]{};
	private Object[] stateList = new Object[]{};
	private Object[] variableList = new Object[]{};
	
	public Object[] getControlList() {
		return controlList;
	}
	public void setControlList(Object[] controlList) {
		this.controlList = controlList;
	}
	public Object[] getStateList() {
		return stateList;
	}
	public void setStateList(Object[] stateList) {
		this.stateList = stateList;
	}
	public Object[] getVariableList() {
		return variableList;
	}
	public void setVariableList(Object[] variableList) {
		this.variableList = variableList;
	}
}
