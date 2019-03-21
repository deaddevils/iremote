package com.iremote.task.devicecommand;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.TagDefine;
import com.iremote.common.Utils;
import com.iremote.domain.Command;
import com.iremote.infraredtrans.SynchronizeRequestHelper;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvByteUnit;
import com.iremote.infraredtrans.tlv.TlvIntUnit;
import com.iremote.infraredtrans.tlv.TlvWrap;

public class ExecuteDeviceCommand implements Runnable {

	private static Log log = LogFactory.getLog(ExecuteDeviceCommand.class);
	private Command command ;
	private String deviceid ;
	private CommandTlv ct ;
	@SuppressWarnings("unused")
	private long taskid ;
	private int operatetype;
	private int timeoutsecond = IRemoteConstantDefine.DEFAULT_TIME_OUT_SECOND;
	private String operator;
	
	public ExecuteDeviceCommand(Command command , long taskid, int operatetype , String operator) {
		super();
		this.command = command;
		this.taskid = taskid;
		this.deviceid = this.command.getDeviceid();
		this.operatetype = operatetype;
		this.operator = operator ;
	}
	
	public ExecuteDeviceCommand(String deviceid , CommandTlv ct , int timeoutsecond) {
		super();
		this.deviceid = deviceid;
		this.ct = ct;
		this.timeoutsecond = timeoutsecond ;
	}
	
	public ExecuteDeviceCommand(String deviceid , CommandTlv ct) {
		super();
		this.deviceid = deviceid;
		this.ct = ct;
	}

	@Override
	public void run() 
	{
		if ( log.isInfoEnabled())
			log.info(JSON.toJSONString(this));
		
		if ( ct != null )
		{
			SynchronizeRequestHelper.asynchronizeRequest(deviceid, ct, timeoutsecond);
			return ;
		}
		
		List<CommandTlv> lst = createCommandTlv();
		if ( lst != null )
		{
			for ( CommandTlv ct : lst )
				SynchronizeRequestHelper.asynchronizeRequest(deviceid, ct, timeoutsecond);
		}
	}

	private List<CommandTlv> createCommandTlv()
	{
		List<CommandTlv> lst = null ;
		if ( command.getInfraredcode() != null && command.getInfraredcode().length > 0 )
		{
			byte b[] = command.getInfraredcode();
			CommandTlv ct = new CommandTlv(4 , 1);
			
			if ( Utils.isByteMatch(new Byte[]{4 , 1 }, b))
				ct.addUnit(new TlvByteUnit(40 , TlvWrap.getValue(b, TagDefine.TAG_HEAD_LENGTH)));
			else if ( Utils.isByteMatch(new Byte[]{0,40}, b))
				ct.addUnit(new TlvByteUnit(40 , TlvWrap.getValue(b, 0)));
			else 
				ct.addUnit(new TlvByteUnit(40 , b));

			return Arrays.asList(ct) ;
		}
		else if ( command.getZwavecommand() != null && command.getZwavecommand().length > 0 )
			lst = CommandHelper.splitCommand(command.getZwavecommand());
		else if ( command.getZwavecommands() != null && command.getZwavecommands().length > 0 )
			lst = CommandHelper.splitCommand(command.getZwavecommands());
		
		if ( lst != null )
		{
			for ( CommandTlv ct : lst)
			{
				if ( operator != null && operator.length() > 0 )
				{
					ct.addUnit(new TlvByteUnit(12 , operator.getBytes()));
					ct.addOrReplaceUnit(new TlvIntUnit(TagDefine.TAG_OPERATION_TYPE , operatetype,TagDefine.TAG_LENGTH_1 ));
				}
				else 
				{
					ct.addUnit(new TlvByteUnit(12 , IRemoteConstantDefine.ASSOCIATION_SCENE_TASK_OPERATOR.getBytes()));
					ct.addOrReplaceUnit(new TlvIntUnit(TagDefine.TAG_OPERATION_TYPE ,IRemoteConstantDefine.OPERATOR_TYPE_ASSCIATION , TagDefine.TAG_LENGTH_1));
				}
			}
		}
		return lst ;
	}

}
