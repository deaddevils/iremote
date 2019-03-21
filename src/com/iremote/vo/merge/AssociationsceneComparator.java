package com.iremote.vo.merge;

import java.util.Comparator;

import com.iremote.domain.Associationscene;

public class AssociationsceneComparator implements Comparator<Associationscene> {

	@Override
	public int compare(Associationscene as1, Associationscene as2) {
		
		if ( as1 == null && as2 == null )
			return 0 ;
		if ( as1 == null || as2 == null )
			return 1;
		if ( as1.getChannelid() == as2.getChannelid() && as1.getStatus() == as2.getStatus() )
			return 0;
		return 1 ;
	}

}
