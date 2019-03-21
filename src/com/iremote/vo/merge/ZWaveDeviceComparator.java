package com.iremote.vo.merge;

import java.util.Comparator;

import com.iremote.domain.ZWaveDevice;

public class ZWaveDeviceComparator implements Comparator<ZWaveDevice> {

	@Override
	public int compare(ZWaveDevice zd1, ZWaveDevice zd2) {
		
		if ( zd1 == null && zd2 == null )
			return 0;
		if ( zd1 == null || zd2 == null )
			return 1 ;
		if ( zd1.getDeviceid() == null )
			return 1 ;
		if (zd1.getDeviceid().equals(zd2.getDeviceid()) && zd1.getNuid() == zd2.getNuid() )
			return 0 ;
		return 1 ;
	}

}
