package com.iremote.infraredtrans.tlv;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.TagDefine;
import com.iremote.common.Utils;
import com.iremote.common.commandclass.CommandUtil;
import com.iremote.infraredtrans.ConnectionManager;
import com.iremote.infraredtrans.IConnectionContext;
import com.iremote.infraredtrans.XSocketReportProcessor;

public class CommandTlv extends TlvArrayUnit 
{
	private static Log log = LogFactory.getLog(CommandTlv.class);
	
	private int commandclass ;
	private int commandsubclass ;
	protected byte[] operator ;
	protected byte[] report ;
	protected String deviceid ;
	protected int nuid ;
	protected Integer operatetype;
	protected Integer apptokenid;
	
	public CommandTlv(int commandclass , int command , String deviceid)
	{
		super(commandclass * 256 + command);
		this.commandclass = commandclass ;
		this.commandsubclass = command ;
		this.deviceid = deviceid;
	}
	
	public CommandTlv(int commandclass , int command) 
	{
		super(commandclass * 256 + command);
		this.commandclass = commandclass ;
		this.commandsubclass = command ;
	}

	public int getTotalLength()
	{
		initData();
		return super.getTotalLength() + 1 ;  // a last byte for check byte. 
	}
	
	@Override
	public byte[] getByte() 
	{
		initData();
		
		int l = getTotalLength();
		byte[] b = new byte[l];
		int index = TlvWrap.TAGLENGTH_LENGTH;
		for ( ITLVUnit t : lst )
		{
			byte[] ub = t.getByte();
			int ul = t.getTotalLength();
			System.arraycopy(ub, 0, b, index, ul);
			index += ul ;
		}
		TlvWrap.writeTag(b, tag);
		TlvWrap.writeLength(b, l - 1 - TlvWrap.TAGLENGTH_LENGTH);
		return b;
	}
	
	public int getPackageCommandLength()
	{
		if ( this.commandclass != TagDefine.COMMAND_CLASS_DEVICE || this.commandsubclass != TagDefine.COMMAND_SUB_CLASS_DEVICE_COMMAND)
			return 0;

		if ( StringUtils.isBlank(deviceid))
			return 0;

		initData();
		if ( this.nuid > 255 )
			return 0 ;

		int l = 4 ;
		if ( this.nuid == 126 )
			l ++ ;
		byte[] cmd = this.getByteAryValueByTag(TagDefine.TAG_ZWAVE_COMMAND);
		if ( cmd == null )
			return 0 ;

		l += cmd.length;
		for ( int j = 0 ; j < cmd.length ; j ++ )
			if ( cmd[j] == 126 )
				l ++ ;
		return l ;
	}
	
	public byte[] getBytesforPackageCommand()
	{
		if ( this.commandclass != TagDefine.COMMAND_CLASS_DEVICE || this.commandsubclass != TagDefine.COMMAND_SUB_CLASS_DEVICE_COMMAND)
			return null;
		if ( StringUtils.isBlank(deviceid))
			return null;

		initData();
		if ( this.nuid == 0 || this.nuid > 255 )
			return null ;
		byte[] cmd = this.getByteAryValueByTag(TagDefine.TAG_ZWAVE_COMMAND);
		if ( cmd == null )
			return null ;
		byte[] r = new byte[4 + cmd.length];
		r[1] = 1 ;  // r[0] for sequence .
		r[2] = (byte)cmd.length;
		r[3] = (byte)this.nuid;
		System.arraycopy(cmd, 0, r, 4, cmd.length);
		return r ;
	}
	
	public void onAckArrive(int ack , byte[] content)
	{
		if ( ack != ErrorCodeDefine.SUCCESS)
			return ;
		
		if ( StringUtils.isBlank(deviceid))
			return ;
		
		if ( this.nuid == 0 || this.report == null )
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
		if ( this.apptokenid != null )
			ct.addUnit(new TlvIntUnit(TagDefine.TAG_APP_USER_TOKEN_ID , this.apptokenid , 4));
		ct.addUnit(new TlvIntUnit(TagDefine.TAG_NUID , nuid , CommandUtil.getnuIdLenght(nuid)));
		
		byte[] report = ct.getByte();
		if ( log.isInfoEnabled())
			Utils.printMockData(String.format("Mock Receive data from %s" , deviceid), report);

		XSocketReportProcessor.getInstance().processRequest(ct.getByte() ,cc );

	}
	
	public int getAckcount()
	{
		return 1 ;
	}
	
	private void initData()
	{
		if ( this.commandclass != TagDefine.COMMAND_CLASS_DEVICE || this.commandsubclass != TagDefine.COMMAND_SUB_CLASS_DEVICE_COMMAND)
			return ;
		if ( StringUtils.isBlank(deviceid))
			return ;
		
		Integer nuid = this.getIntegerByTag(TagDefine.TAG_NUID);
		byte[] report = this.getByteAryValueByTag(TagDefine.TAG_ZWAVE_COMMAND_REPORT);
		if ( nuid == null )
			return ;
		this.nuid = nuid ;

		if ( report == null )
		{
			this.removeUnitByTag(TagDefine.TAG_APP_USER_TOKEN_ID);
			this.removeUnitByTag(TagDefine.TAG_OPERATOR);
			this.removeUnitByTag(TagDefine.TAG_OPERATION_TYPE);
			return ;
		}

		this.operatetype = this.getIntegerByTag(TagDefine.TAG_OPERATION_TYPE);
		this.operator = this.getByteAryValueByTag(TagDefine.TAG_OPERATOR);
		this.apptokenid = this.getIntegerByTag(TagDefine.TAG_APP_USER_TOKEN_ID);
		
		if ( report != null )
		{
			this.report = report ;
			this.removeUnitByTag(TagDefine.TAG_ZWAVE_COMMAND_REPORT);
			this.removeUnitByTag(TagDefine.TAG_APP_USER_TOKEN_ID);
			this.removeUnitByTag(TagDefine.TAG_OPERATOR);
			this.removeUnitByTag(TagDefine.TAG_OPERATION_TYPE);
		}
	}
	
	
	public int getCommandclass() {
		return commandclass;
	}

	public int getCommandsubclass() {
		return commandsubclass;
	}

	public void setDeviceid(String deviceid)
	{
		this.deviceid = deviceid;
	}

}
