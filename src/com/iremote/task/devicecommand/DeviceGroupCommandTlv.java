package com.iremote.task.devicecommand;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.iremote.common.ByteUtils;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.TagDefine;
import com.iremote.common.Utils;
import com.iremote.common.ZWaveCommandClass;
import com.iremote.infraredtrans.ConnectionManager;
import com.iremote.infraredtrans.IConnectionContext;
import com.iremote.infraredtrans.XSocketReportProcessor;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.ITLVUnit;
import com.iremote.infraredtrans.tlv.TlvByteUnit;
import com.iremote.infraredtrans.tlv.TlvIntUnit;
import com.iremote.infraredtrans.tlv.TlvWrap;

public class DeviceGroupCommandTlv extends CommandTlv
{
	private static Log log = LogFactory.getLog(DeviceGroupCommandTlv.class);
	
	private byte[] command ;
	private List<Byte> nuidlist = new ArrayList<Byte>();

	public DeviceGroupCommandTlv(String deviceid)
	{
		super(TagDefine.COMMAND_CLASS_DEVICE, TagDefine.COMMAND_SUB_CLASS_DEVICE_GROUP_COMMAND);
		this.deviceid = deviceid ;
	}
	
	@Override
	public int getTotalLength()
	{
		initContent();
		return super.getTotalLength();
	}

	@Override
	public byte[] getByte()
	{
		initContent();
		return super.getByte();
	}
	
	public boolean initCommand(byte[] cmd ,byte[] report ,  String operator)
	{
		if ( StringUtils.isBlank(operator))
			return this.initCommand(cmd ,report , new byte[0]);
		try
		{
			return this.initCommand(cmd , report ,operator.getBytes("UTF-8"));
		}
		catch (UnsupportedEncodingException e)
		{
			log.error(e.getMessage(), e);
			return false ;
		}
	}

	public boolean initCommand(byte[] cmd , byte[] report ,  byte[] operator)
	{
		if ( this.command != null )
			return false ;
		if ( cmd == null || cmd.length < 2 || cmd[0] == ZWaveCommandClass.COMMAND_CLASS_DOOR_LOCK )
			return false ;
		this.command = cmd ;
		this.operator = operator;
		this.report = report ;
		return true;
	}
	
	public boolean initCommand(CommandTlv ct)
	{
		if ( this.command != null )
			return false ;
		byte[] cmd = ct.getByteAryValueByTag(TagDefine.TAG_ZWAVE_COMMAND);
		byte[] operator = ct.getByteAryValueByTag(TagDefine.TAG_OPERATOR);;
		byte[] report = ct.getByteAryValueByTag(TagDefine.TAG_ZWAVE_COMMAND_REPORT);
		Integer operationtype = ct.getIntegerByTag(TagDefine.TAG_OPERATION_TYPE);
		
		boolean rst = this.initCommand(cmd , report, operator);
		
		if ( rst == false )
			return rst ;
		
		Integer nuid = readnuid(ct);
		if ( nuid == null )
			return false ;
		
		nuidlist.add(nuid.byteValue());
		
		this.operatetype = operationtype;
		
		return rst ;
	}
	
	public boolean merge(CommandTlv ct)
	{
		if ( nuidlist.size() >= 64 )
			return false ;
		Integer nuid = readnuid(ct);
		if ( nuid == null || nuid.intValue() > 256 )
			return false ;

		byte[] cmd = ct.getByteAryValueByTag(TagDefine.TAG_ZWAVE_COMMAND);
		if ( cmd == null )
			return false ;
		
		if ( !ByteUtils.isByteAryEqual(cmd, this.command))
			return false ;
		
		nuidlist.add(nuid.byteValue());
		return true;
	}

	@Override
	public void onAckArrive(int ack , byte[] content)
	{
		if ( ack != ErrorCodeDefine.SUCCESS)
			return ;
		
		IConnectionContext cc = ConnectionManager.getConnection(deviceid);
		if ( cc == null )
			return ;
		
		CommandTlv ct = new CommandTlv(TagDefine.COMMAND_CLASS_DEVICE,TagDefine.COMMAND_SUB_CLASS_DEVICE_REPORT);
		ct.addUnit(new TlvByteUnit(TagDefine.TAG_ZWAVE_COMMAND , report));
		ct.addUnit(new TlvIntUnit(TagDefine.TAG_TIME , (int)System.currentTimeMillis()/1000 , 4));
		if ( this.operator != null && this.operator.length > 0 )
			ct.addUnit(new TlvByteUnit(TagDefine.TAG_OPERATOR , this.operator));
		if ( this.operatetype != null )
			ct.addUnit(new TlvIntUnit(TagDefine.TAG_OPERATION_TYPE , this.operatetype , 1));
		
		for ( Byte nuid : nuidlist )
		{
			ct.addOrReplaceUnit(new TlvIntUnit(TagDefine.TAG_NUID , nuid , 1));
			byte[] report = ct.getByte();
			if ( log.isInfoEnabled())
				Utils.printMockData(String.format("Mock Receive data from %s" , deviceid), report);
			
			XSocketReportProcessor.getInstance().processRequest(ct.getByte() ,cc );
		}
	}

	private Integer readnuid(CommandTlv ct)
	{
		ITLVUnit nuidtlv = ct.getUnitbyTag(TagDefine.TAG_NUID);
		if ( nuidtlv == null )
			return null ;
		byte[] nuidba = nuidtlv.getByte();
		return TlvWrap.readInteter(nuidba, TagDefine.TAG_NUID, 0);
	}

	private void initContent()
	{
		super.addOrReplaceUnit(new TlvByteUnit(TagDefine.TAG_ZWAVE_COMMAND , this.command));
		super.addOrReplaceUnit(new TlvByteUnit(TagDefine.TAG_NUIDS , ByteUtils.tobytearray(nuidlist)));
	}

	@Override
	public int getPackageCommandLength()
	{
		int l = 3 ;
		l += nuidlist.size();
		for ( Byte b : nuidlist)
			if ( b == 126)
				l ++ ;

		l += command.length;
		for ( int j = 0 ; j < command.length ; j ++ )
			if ( command[j] == 126 )
				l ++ ;
		return l ;
	}

	@Override
	public byte[] getBytesforPackageCommand()
	{
		byte[] r = new byte[3 + nuidlist.size() + command.length];
		r[1] = (byte)nuidlist.size() ;  // r[0] for sequence .
		r[2] = (byte)command.length;
		
		int index = 3 ;
		for ( int i = 0 ; i < nuidlist.size() ; i ++ , index ++ )
			r[index] = nuidlist.get(i);
		
		System.arraycopy(command, 0, r, index, command.length);
		return r ;
	}

	
}
