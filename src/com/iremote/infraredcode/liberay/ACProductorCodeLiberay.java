package com.iremote.infraredcode.liberay;

import com.iremote.common.file.SerializeHelper;
import com.iremote.infraredcode.CodeLiberayHelper;
import com.iremote.infraredcode.vo.CodeLiberayVo;


public class ACProductorCodeLiberay extends ProductorCodeLiberay 
{
	public ACProductorCodeLiberay() {
		super();
		regestCombileProductor("sanlingheavyindustries", new String[]{"mitsubishiheavy"});
		regestCombileProductor("sanlingelectrical", new String[]{"mitsubishimotor"});
		regestCombileProductor("oaks", new String[]{"aux"});

		liberayvo =  SerializeHelper.getObject("resource/ac_parsed_data.ser", new CodeLiberayVo() );
	}

	@Override
	protected int[] composeCodeLiberay(int index ,int[] src)
	{
		if ( src == null )
			return null ;
		return CodeLiberayHelper.composeACCodeLiberay(index , src);
	}
	
	@Override
	protected String getcodeid(int index)
	{
		return String.format("AC_%d", index) ;
	}
}
