package com.iremote.infraredcode.vo;

import java.io.Serializable;

public class RemoteControllerCode  implements Serializable{

	private String ctrlcode;
	private String codeid;
	
	public RemoteControllerCode(String ctrlcode, String codeid) {
		super();
		this.ctrlcode = ctrlcode;
		this.codeid = codeid;
	}

	public String getCtrlcode() {
		return ctrlcode;
	}

	public void setCtrlcode(String ctrlcode) {
		this.ctrlcode = ctrlcode;
	}

	public String getCodeid() {
		return codeid;
	}

	public void setCodeid(String codeid) {
		this.codeid = codeid;
	}

}
