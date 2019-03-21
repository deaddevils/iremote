package com.iremote.service;

import com.iremote.common.Hibernate.CriteriaWrap;
import com.iremote.common.Hibernate.ExpWrap;
import com.iremote.domain.Address;

public class AddressService extends BaseService<Address>{

	public Address queryByPhoneuserid(Integer phoneuserid){
		CriteriaWrap cw = new CriteriaWrap(Address.class.getName());
		cw.add(ExpWrap.eq("phoneuserid", phoneuserid));
		return cw.uniqueResult();
	}
}
