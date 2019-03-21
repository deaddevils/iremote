package com.iremote.action.scens;

import java.util.ArrayList;
import java.util.List;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.device.operate.IOperationTranslator;
import com.iremote.device.operate.OperationTranslatorStore;
import com.iremote.domain.Command;
import com.iremote.domain.Scene;
import com.iremote.domain.Timer;
import com.iremote.service.SceneService;
import com.iremote.service.TimerService;
import com.iremote.test.db.Db;

public class TimerDataUpgrade
{
	public static void main(String arg[])
	{
		Db.init();
		TimerService ts = new TimerService();
		SceneService ss = new SceneService();
		
		int max = 30 ;
		for ( int i = 0 ; ; i += max)
		{
			List<Timer> lst = ts.query(i, max);
			
			if ( lst == null || lst.size() == 0 )
				break ;
			
			for ( Timer t : lst )
			{
				t.setScene(new Scene());
				t.setScenetype(IRemoteConstantDefine.SCENE_TYPE_TIMER);
				Scene s = t.getScene();
				s.setSceneid(Utils.createtoken());
				s.setScenetype(IRemoteConstantDefine.SCENE_TYPE_TIMER);
				s.setTimerlist(new ArrayList<Timer>());
				s.getTimerlist().add(t);
				
				ss.save(s);
				
				s.setCommandlist(new ArrayList<Command>());
				
				Command c = new Command();
				s.getCommandlist().add(c);
				c.setScene(s);
				c.setZwavedevice(t.getZwavedevice());
				c.setInfrareddevice(t.getInfrareddevice());
				c.setIndex(0);
				c.setDelay(0);

				if ( t.getZwavedevice() != null )
				{
					c.setApplianceid(t.getZwavedevice().getApplianceid());
					c.setDeviceid(t.getZwavedevice().getDeviceid());
					c.setZwavecommand(t.getZwavecommand());
					
					IOperationTranslator ot = OperationTranslatorStore.getInstance().getOperationTranslator(t.getZwavedevice().getMajortype(), t.getZwavedevice().getDevicetype());
					if ( ot != null )
					{
						ot.setZWavedevice(c.getZwavedevice());
						if ( c.getZwavecommand() != null )
							ot.setCommand(c.getZwavecommand());
						else if ( c.getZwavecommands() != null )
							ot.setCommand(c.getZwavecommands());
						c.setCommandjsonstr(ot.getCommandjson());
					}
				}
				else if ( t.getInfrareddevice() != null )
				{
					c.setApplianceid(t.getInfrareddevice().getApplianceid());
					c.setDeviceid(t.getInfrareddevice().getDeviceid());
					c.setInfraredcode(t.getInfraredcode());
										
					IOperationTranslator ot = OperationTranslatorStore.getInstance().getOperationTranslator(t.getInfrareddevice().getMajortype(), t.getInfrareddevice().getDevicetype());
					if ( ot != null )
					{
						ot.setInfrareddevice(c.getInfrareddevice());
						ot.setCommand(c.getInfraredcode());
						c.setCommandjsonstr(ot.getCommandjson());
					}
				}
			}
		}
		
		Db.commit();
	}
}
