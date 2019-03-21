package com.iremote.infraredcode.liberay;

import com.iremote.common.file.SerializeHelper;
import com.iremote.infraredcode.CodeLiberayHelper;
import com.iremote.infraredcode.vo.CodeLiberayVo;

public class TVProductorCodeLiberay extends ProductorCodeLiberay 
{
	public TVProductorCodeLiberay() {
		super();
		regestCombileProductor("philips", new String[]{"philips","philps"});
		regestCombileProductor("prima", new String[]{"prima" , "xoceco"});
		liberayvo =  SerializeHelper.getObject("resource/tv_parsed_data.ser", new CodeLiberayVo() );
	}
	
	@Override
	protected int[] composeCodeLiberay(int index ,int[] src)
	{
		return CodeLiberayHelper.composeTVCodeLiberay(src);
	}
	
	@Override
	protected String getcodeid(int index)
	{
		return String.format("TV_%d", index) ;
	}
}
