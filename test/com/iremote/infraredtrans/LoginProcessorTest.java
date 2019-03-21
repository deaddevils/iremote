package com.iremote.infraredtrans;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.jms.JMSUtil;
import com.iremote.common.taskmanager.TaskWrap;
import com.iremote.common.taskmanager.db.DefaultDbSessionFactory;
import com.iremote.domain.GatewayCapability;
import com.iremote.domain.Remote;
import com.iremote.event.user.ShareRelationshipChanged;
import com.iremote.service.GatewayCapabilityService;
import com.iremote.service.RemoteService;
import com.iremote.test.db.Db;
import com.iremote.thirdpart.rentinghouse.event.DeleteRemoteProcessor;
import com.iremote.thirdpart.rentinghouse.event.DeviceStatusChangeProcessor;
import com.iremote.thirdpart.rentinghouse.event.DoorlockOpenProcessor;
import com.iremote.thirdpart.rentinghouse.event.InfoChangeProcessor;
import com.iremote.thirdpart.rentinghouse.event.RemoteOfflineProcessor;
import com.iremote.thirdpart.rentinghouse.event.RemoteOnlineProcessor;
import com.iremote.thirdpart.rentinghouse.event.RemoteOwnerChangeEventProcessor;
import com.iremote.thirdpart.rentinghouse.event.ZWaveDeviceBatteryEventProcessor;
import com.iremote.thirdpart.rentinghouse.event.ZWaveDeviceBatteryLowEventProcessor;


public class LoginProcessorTest {

	protected static ExecutorService exeservice = Executors.newCachedThreadPool();
	
	public static void main(String arg[])
	{
		Db.init();
		
		RemoteService rs = new RemoteService();
		Remote remote = rs.getIremotepassword("iRemote2005000001339");
		int c = 124 ;
		
		if ( remote.getCapability() != null )
		{
			List<GatewayCapability> rl = new ArrayList<GatewayCapability>();
			for ( GatewayCapability cc : remote.getCapability())
			{
				if ( cc.getCapabilitycode() > 32 )
					continue;
				if ( ( c & ( 1 << ( cc.getCapabilitycode() - 1 ) )) == 0 )
					rl.add(cc);
			}
			remote.getCapability().removeAll(rl);
		}
		
		Db.commit();
	}
	
	public static void main1(String arg[])
	{
		JMSUtil.init();
		
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.NOTIFICATION_SHARE_RELA_CHANGED,ShareRelationshipChanged.class);
		
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_DEVICE_STATUS,DeviceStatusChangeProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.NOTIFICATION_INFO_CHANGED,InfoChangeProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.NOTIFICATION_DELETE_REMOTE,DeleteRemoteProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_REMOTE_OFFLINE,RemoteOfflineProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_REMOTE_ONLINE,RemoteOnlineProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_BATTERY,ZWaveDeviceBatteryEventProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_LOW_BATTERY,ZWaveDeviceBatteryLowEventProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.NOTIFICATION_OWNER_CHANGED,RemoteOwnerChangeEventProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_DOOR_LOCK_OPEN,DoorlockOpenProcessor.class);
	
		for ( ;; )
		{
			System.out.println("start....");
			for ( int i = 0 ; i < 100 ; i ++ )
			{
				exeservice.execute(new TaskWrap(new LoginProcessorThread() , new DefaultDbSessionFactory()));
			}
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
}
