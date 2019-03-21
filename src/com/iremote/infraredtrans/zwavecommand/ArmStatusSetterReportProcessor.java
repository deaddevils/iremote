package com.iremote.infraredtrans.zwavecommand;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.iremote.action.phoneuser.SetPhoneUserArmStatus;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.common.commandclass.CommandUtil;
import com.iremote.common.constant.ArmStatusSetterResult;
import com.iremote.domain.Associationscene;
import com.iremote.domain.Command;
import com.iremote.domain.Partition;
import com.iremote.infraredtrans.SynchronizeRequestHelper;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvIntUnit;
import com.iremote.service.AssociationsceneService;
import com.iremote.service.PartitionService;


public class ArmStatusSetterReportProcessor extends ZWaveReportBaseProcessor 
{

	@Override
	protected void updateDeviceStatus() 
	{
		int armstatus = IRemoteConstantDefine.PHONEUSER_ARM_STATUS_ARM; 
		if ( zrb.getCommandvalue().getValue() == 0 ) 
			armstatus = IRemoteConstantDefine.PHONEUSER_ARM_STATUS_DISARM;
		
		this.zrb.getDevice().setStatus(armstatus);
		super.oldstatus = -1 ;//always trigger association scene
		
		Integer status = null;
		if ( !hasPartitionFunction())
		{
			SetPhoneUserArmStatus action = new SetPhoneUserArmStatus();
			action.setPhoneuser(super.zrb.getPhoneuser());
			action.setArmstatus(armstatus);
			
			action.executeonDeviceReport(super.zrb.getDevice().getName());

			if ( action.getResultCode() == ErrorCodeDefine.SUCCESS )
				status = returnSuccess(armstatus);
			else 
				status = returnFailed(armstatus);
		}
		else
			status = checkPartitionArmStatus(armstatus);
		
		if ( status != null )
		{
			CommandTlv ct = CommandUtil.createBasicSetCommand(super.zrb.getNuid(), status.byteValue());
			ct.addOrReplaceUnit(new TlvIntUnit(72 , 1 , 1));
			SynchronizeRequestHelper.asynchronizeRequest(super.zrb.getDeviceid(), ct, 1);
		}
	}
	
	private boolean hasPartitionFunction()
	{
		int pf = Utils.getRemotePlatform(super.zrb.getDeviceid());
		return ( pf == IRemoteConstantDefine.PLATFORM_AMETA);
	}

	private int checkPartitionArmStatus(int armstatus)
	{
		AssociationsceneService svr = new AssociationsceneService();
		List<Associationscene> lst = svr.query(zrb.getDevice(), 0 , new int[]{armstatus});
		
		if ( lst == null || lst.size() == 0 )
			return returnSuccess(armstatus);
		
		PartitionService psvr = new PartitionService();
		
		for ( Associationscene as : lst )
		{
			if ( as.getCommandlist() == null || as.getCommandlist().size() == 0 )
				continue ;
			
			for ( Command c : as.getCommandlist())
			{
				if ( c.getCommandjson() == null  )
					continue;
				for ( int i = 0 ; i < c.getCommandjson().size() ; i ++ )
				{
					JSONObject json = c.getCommandjson().getJSONObject(i);
					if ( !isPartitionCommand(json))
						continue;
					int partitionid = json.getIntValue("partitionid");
					Partition p = psvr.query(partitionid);
					if ( p == null )
						continue;
					if ( !isPartitionStatusMatch(p , json))
						return returnFailed(armstatus);
				}
			}
		}
		return returnSuccess(armstatus);
	}
	
	private boolean isPartitionStatusMatch(Partition p , JSONObject json)
	{
		String op = json.getString("operation");
		if ( IRemoteConstantDefine.WARNING_TYPE_USER_ARM.equals(op)
				&& IRemoteConstantDefine.PHONEUSER_ARM_STATUS_ARM == p.getArmstatus() )
			return true;
		if ( IRemoteConstantDefine.WARNING_TYPE_USER_INHOME_ARM.equals(op)
				&& IRemoteConstantDefine.PHONEUSER_ARM_STATUS_SLEEPING_ARM == p.getArmstatus() )
			return true;
		if ( IRemoteConstantDefine.WARNING_TYPE_USER_DISARM.equals(op)
				&& IRemoteConstantDefine.PHONEUSER_ARM_STATUS_DISARM == p.getArmstatus() )
			return true;
		return false ;
	}
	
	private boolean isPartitionCommand(JSONObject json)
	{
		if ( !json.containsKey("operation"))
			return false;
		String op = json.getString("operation");
		if ( !IRemoteConstantDefine.WARNING_TYPE_USER_ARM.equals(op)
				&& !IRemoteConstantDefine.WARNING_TYPE_USER_INHOME_ARM.equals(op)
				&& !IRemoteConstantDefine.WARNING_TYPE_USER_DISARM.equals(op) )
			return false;
		if ( !json.containsKey("partitionid"))
			return false;
		return true;
	}
	
	private int returnSuccess(int armstatus)
	{
		if ( armstatus == IRemoteConstantDefine.PHONEUSER_ARM_STATUS_ARM)
			return ArmStatusSetterResult.arm_success.ordinal();
		else 
			return ArmStatusSetterResult.disarm_success.ordinal();
	}
	
	private int returnFailed(int armstatus)
	{
		if ( armstatus == IRemoteConstantDefine.PHONEUSER_ARM_STATUS_ARM)
			return ArmStatusSetterResult.arm_failed.ordinal();
		else 
			return ArmStatusSetterResult.disarm_failed.ordinal();
	}
	
	@Override
	public String getMessagetype() 
	{
		return IRemoteConstantDefine.WARNING_TYPE_DEVICE_STATUS;
	}

}
