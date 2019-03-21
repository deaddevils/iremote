package com.iremote.dataprivilege.attribute;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.iremote.domain.ThirdPart;
import com.iremote.domain.ThirdPartAttribute;
import com.iremote.service.ThirdPartAttributeService;

public class AttributeCheckerforThirdpart extends AttributeChecker<ThirdPart>
{
	protected String code ;
	protected String value ;
	
	@Override
	public boolean checkprivilege() 
	{
		if ( super.user == null )
			return false ;
		
		if ( StringUtils.isBlank(code) || StringUtils.isBlank(value))
			return false ;
		
		ThirdPartAttributeService tps = new ThirdPartAttributeService();
		List<ThirdPartAttribute> lst = tps.querybyphoneuserid(super.user.getThirdpartid());
		
		if ( lst == null || lst.size() == 0 )
			return false ;
		
		for ( ThirdPartAttribute tpa : lst )
			if (  code.equalsIgnoreCase(tpa.getCode()) && value.equalsIgnoreCase(tpa.getValue()))
				return true;
		
		return false;
	}

}
