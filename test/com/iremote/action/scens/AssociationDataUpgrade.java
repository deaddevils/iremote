package com.iremote.action.scens;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.device.operate.IOperationTranslator;
import com.iremote.device.operate.OperationTranslatorStore;
import com.iremote.domain.Associationscene;
import com.iremote.domain.Command;
import com.iremote.domain.Scene;
import com.iremote.domain.ZWaveDevice;
import com.iremote.service.AssociationsceneService;
import com.iremote.service.CommandService;
import com.iremote.service.SceneService;
import com.iremote.test.db.Db;

public class AssociationDataUpgrade
{

	public static void main(String arg[])
	{
		Db.init();
		
		AssociationsceneService ass = new AssociationsceneService();
		SceneService ss = new SceneService();
		
		int max = 30 ;
		for ( int i = 0 ; ; i += max )
		{
			List<Associationscene> lst = ass.query(i, max);
			if ( lst == null || lst.size() == 0 )
				break ;
			
			for ( Associationscene as : lst )
			{
				if ( as.getZwavedevice() == null )
				{
					ass.delete(as);
					continue ;
				}
				ZWaveDevice z = as.getZwavedevice() ;
				
				if ( as.getScene() == null )
				{
					as.setScenetype(IRemoteConstantDefine.SCENE_TYPE_ASSOCIATION);
					as.setScene(new Scene());
					as.getScene().setAssociationscenelist(Arrays.asList(new Associationscene[]{as}));
					as.getScene().setCommandlist(new ArrayList<Command>());
//					as.getScene().getCommandlist().addAll(as.getCommandlist());
					//as.getScene().setPhoneuserid(r.getPhoneuserid());
					as.getScene().setSceneid(Utils.createtoken());
					as.getScene().setScenetype(IRemoteConstantDefine.SCENE_TYPE_ASSOCIATION);
					
					IOperationTranslator ot = OperationTranslatorStore.getInstance().getOperationTranslator(z.getMajortype(), z.getDevicetype());
					if ( ot != null )
					{
						ot.setOperationType(IRemoteConstantDefine.OPERATOR_TYPE_ASSCIATION);
						ot.setZWavedevice(z);
						ot.setStatus(as.getStatus());
						as.setDevicestatus(ot.getDeviceStatus());
						as.setOperator(as.getOperator());
					}
					ss.save(as.getScene());
				}
//				for ( Command c : as.getCommandlist())
//				{
//					c.setScene(as.getScene());
//				}
			}
		}
		
		for ( int i = 0 ; ; i += max)
		{
			CommandService cs = new CommandService();
			List<Command>  lst = cs.query(i, max);
			
			if ( lst == null || lst.size() == 0 )
				break ;
			
			List<Command> rl = new ArrayList<Command>();
			for ( Command c : lst)
			{				
				IOperationTranslator ot = null ;
				if ( c.getZwavedevice() != null )
				{
					ot = OperationTranslatorStore.getInstance().getOperationTranslator(c.getZwavedevice().getMajortype(), c.getZwavedevice().getDevicetype());
					if ( ot != null )
					{
						ot.setZWavedevice(c.getZwavedevice());
						if ( c.getZwavecommand() != null )
							ot.setCommand(c.getZwavecommand());
						else if ( c.getZwavecommands() != null )
							ot.setCommand(c.getZwavecommands());
					}
				}
				else if ( c.getInfrareddevice() != null )
				{
					ot = OperationTranslatorStore.getInstance().getOperationTranslator(c.getInfrareddevice().getMajortype(), c.getInfrareddevice().getDevicetype());
					if ( ot != null )
					{
						ot.setInfrareddevice(c.getInfrareddevice());
						ot.setCommand(c.getInfraredcode());
					}
				}
				else if ( c.getLaunchscenedbid() == null )
				{
					rl.add(c);
				}
				if ( ot != null )
					c.setCommandjsonstr(ot.getCommandjson());
				
			}
			
			for ( Command c : rl )
				cs.delete(c);
			rl.clear();
		}
		
		Db.commit();
	}
}
