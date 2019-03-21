package com.iremote.infraredcode.liberay;

import com.iremote.common.file.SerializeHelper;
import com.iremote.infraredcode.CodeLiberayHelper;
import com.iremote.infraredcode.stb.StbProductor;
import com.iremote.infraredcode.vo.CodeLiberayVo;

public class STBProductorCodeLiberay extends ProductorCodeLiberay 
{
	public STBProductorCodeLiberay() {
		super();
		combineProductor = StbProductor.combineProductor;
		liberayvo =  SerializeHelper.getObject("resource/stb_parsed_data.ser", new CodeLiberayVo() );
	}

	@Override
	protected int[] composeCodeLiberay(int index ,int[] src)
	{
		return CodeLiberayHelper.composeSTBCodeLiberay(src);
	}
	
	@Override
	protected String getcodeid(int index)
	{
		return String.format("STB_%d", index) ;
	}
}
