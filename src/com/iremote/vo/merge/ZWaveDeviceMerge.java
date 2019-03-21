package com.iremote.vo.merge;

import com.iremote.common.Utils;
import com.iremote.domain.ZWaveDevice;

public class ZWaveDeviceMerge implements IMerge<ZWaveDevice> {

	@Override
	public void merge(ZWaveDevice desc, ZWaveDevice src) 
	{
		if ( desc == null || src == null )
			return ;
		desc.setName(src.getName());
		
		Utils.merge(desc.getAssociationscenelist(), src.getAssociationscenelist(), new AssociationsceneComparator() , new AssociationsceneMerge());
		
	}

}
