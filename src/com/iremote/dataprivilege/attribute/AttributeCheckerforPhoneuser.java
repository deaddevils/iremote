package com.iremote.dataprivilege.attribute;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.iremote.domain.PhoneUser;
import com.iremote.domain.PhoneUserAttribute;
import com.iremote.service.PhoneUserAttributeService;

public class AttributeCheckerforPhoneuser extends AttributeChecker<PhoneUser>
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

		PhoneUserAttributeService puas = new PhoneUserAttributeService();
		List<PhoneUserAttribute> lst = puas.querybyphoneuserid(super.user.getPhoneuserid());
		
		for ( PhoneUserAttribute tpa : lst )
			if (  code.equalsIgnoreCase(tpa.getCode()) && value.equalsIgnoreCase(tpa.getValue()))
				return true;
		
		return false;
	}

}
