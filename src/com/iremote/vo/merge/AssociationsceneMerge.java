package com.iremote.vo.merge;

import com.iremote.domain.Associationscene;
import com.iremote.domain.Command;

public class AssociationsceneMerge implements IMerge<Associationscene> {

	@Override
	public void merge(Associationscene desc, Associationscene src) {
		
		if ( desc == null || src == null )
			return ;
		
		desc.getCommandlist().clear();
		if ( desc.getScene() != null && desc.getScene().getCommandlist() != null )
			desc.getScene().getCommandlist().clear();
		desc.getCommandlist().addAll(src.getCommandlist());
		
		for ( Command c : desc.getCommandlist())
			c.setAssociationscene(desc);
		
	}

}
