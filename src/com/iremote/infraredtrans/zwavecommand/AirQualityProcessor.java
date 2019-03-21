package com.iremote.infraredtrans.zwavecommand;

import com.alibaba.fastjson.JSON;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;

public class AirQualityProcessor extends ZWaveReportBaseProcessor
{
	private Object[] status = new Object[15];
	
	public AirQualityProcessor()
	{
		super();
		super.dontsavenotification();
	}

	@Override
	protected void updateDeviceStatus()
	{
		if ( zrb.getCmd().length < 17 ) 
			return ;
		
		status[0] = 0;
		
		readhcho();
		readtvoc();
		readpm2_5();
		readaqi();

		status[13] = (Utils.readint(zrb.getCmd(), 8, 10) * 1f) / 10f;
		status[14] = Utils.readint(zrb.getCmd(), 10, 12);

		status[7] = 1;
		status[8] = Utils.readint(zrb.getCmd(), 14, 16);
		status[9] = 1;
		status[10] = Utils.readint(zrb.getCmd(), 16, 18);
		
		
		zrb.getDevice().setStatuses(JSON.toJSONString(status));	
	}
	
	private void readaqi()
	{
		int i = Utils.readint(zrb.getCmd(), 12, 14);
		if ( i == 0xffff )
		{
			status[11] = -1;
			status[12] = -1;
		}
		else 
		{
			status[12] = i ;
			i = ( i / 50) + 1 ;
			if ( i > 6 ) i = 6 ;
			status[11] = i;
		}
	}
	
	private void readpm2_5()
	{
		//PM2.5
		int i = Utils.readint(zrb.getCmd() , 6 , 8 );
		if ( i == 0xffff )
		{
			status[5] = -1;
			status[6] = -1;
		}
		else 
		{
			status[6] = i;
			if ( i <= 35 )
				status[5] = 1;
			else if ( i <= 75 )
				status[5] = 2;
			else if ( i <= 115 )
				status[5] = 3;
			else if ( i <= 151 )
				status[5] = 4;
			else if ( i <= 250 )
				status[5] = 5;
			else 
				status[5] = 6;
		}
	}

	private void readtvoc()
	{
		//TVOC
		int i = Utils.readint(zrb.getCmd(), 4, 6) ;
		
		if ( i == 0xffff )
		{
			status[3] = -1 ;
			status[4] = -1 ;
		}
		else 
		{
			float f = ( i * 1f) / 1000f ;
			status[4] = f ;
			
			if ( f <= 0.6 )
				status[3] = 1 ;
			else 
				status[3] = 4;
		}
	}
	
	private void readhcho()
	{
		//HCHO
		int i = Utils.readint(zrb.getCmd(), 2, 4);
		
		if ( i == 0xffff )
		{
			status[1] = -1 ;
			status[2] = -1 ;
		}
		else 
		{
			float f = ( i * 1f) / 1000f ;
			status[2] = f;
			if ( f <= 0.1 )
				status[1] = 1 ;
			else 
				status[1] = 4;
		}
	}
	
	@Override
	public String getMessagetype()
	{
		if ( zrb.getCmd().length < 17 ) 
			return null ;
		return IRemoteConstantDefine.WARNING_TYPE_DEVICE_STATUS;
	}
	

	
}
