package com.iremote.infraredcode.vo;

import com.iremote.infraredcode.CodeLiberayHelper;
import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class CommonCodeQuery extends CodeQueryBase {

	private String productor;

	public CommonCodeQuery(String productor , String[] querycode) {
		super();
		this.productor = productor;
		this.querystring = CodeLiberayHelper.string2int(querycode);
	}

	@Override
	public String getProductor() {
		return productor;
	}

	@Override
	public String[] getQueryCodeLiberay() {
		return null;
	}

}
