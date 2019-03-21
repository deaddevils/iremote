package com.iremote.action.helper;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.domain.ZWaveDevice;
import com.iremote.infraredtrans.ConnectionManager;
import com.iremote.infraredtrans.SynchronizeRequestHelper;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvIntArray;
import com.iremote.service.ZWaveDeviceService;

public class AddDevice2RemoteHelper {
	private int resultCode = ErrorCodeDefine.SUCCESS ;

	public void addSpecialDevice2Remote(String deviceid){
		if(StringUtils.isBlank(deviceid)){
			this.resultCode = ErrorCodeDefine.PARMETER_ERROR;
			return;
		}
		ZWaveDeviceService zds = new ZWaveDeviceService();
		List<ZWaveDevice> zwavedevicelist = zds.querybydeviceid(deviceid);
		List<Integer> nuidlist = new ArrayList<>();
		for(ZWaveDevice z : zwavedevicelist){
			if((z.getNuid() & 0xff0000) == 0x200000 || (z.getNuid() & 0xff000000) == 0x100000){
				nuidlist.add(z.getNuid());
			}
		}
		if(nuidlist.size()==0){
			return ;
		}
		int[] nuids = new int[nuidlist.size()];
		for(int i = 0;i<nuidlist.size();i++){
		    nuids[i] = nuidlist.get(i);
		}
		CommandTlv ct = new CommandTlv(105 ,1 );

		ct.addUnit(new TlvIntArray(83 , nuids , 4));

		byte[] rp = SynchronizeRequestHelper.synchronizeRequest(deviceid, ct , 8);
		if ( rp == null ){
			if(ConnectionManager.isOnline(deviceid) == false){
				this.resultCode = ErrorCodeDefine.DEVICE_OFFLINE;
			}else{
				this.resultCode = ErrorCodeDefine.TIME_OUT;	
			}
		}
		
	}

	public int getResultCode() {
		return resultCode;
	}
	
	
}
