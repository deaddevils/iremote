package com.iremote.dataprivilege;

import java.util.List;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeCheckorbase;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeType;
import com.iremote.domain.ThirdPart;
import com.iremote.thirdpart.wcj.domain.ComunityRemote;
import com.iremote.thirdpart.wcj.service.ComunityRemoteService;

public class ThirdpartDataPrivilegeCheckor extends DataPrivilegeCheckorbase
{
	private ThirdPart thirdpart;

	public ThirdpartDataPrivilegeCheckor(ThirdPart thirdpart)
	{
		super();
		this.thirdpart = thirdpart;
		init();
	}
	
	protected void init()
	{
		ComunityRemoteService crs = new ComunityRemoteService();
		List<ComunityRemote> lst = crs.query(thirdpart.getThirdpartid());
	
		for ( ComunityRemote cr : lst )
		{
			addPrivelege(cr.getDeviceid() ,IRemoteConstantDefine.DEVICE_MAJORTYPE_ZWAVE, 0, DataPrivilegeType.MODIFY);
			addPrivelege(cr.getDeviceid() ,IRemoteConstantDefine.DEVICE_MAJORTYPE_INFRARED, 0, DataPrivilegeType.MODIFY);
		}
	}
}
