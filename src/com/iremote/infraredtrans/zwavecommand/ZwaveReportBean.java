package com.iremote.infraredtrans.zwavecommand;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.iremote.action.helper.DeviceHelper;
import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.TagDefine;
import com.iremote.common.Utils;
import com.iremote.common.commandclass.CommandParser;
import com.iremote.common.commandclass.CommandValue;
import com.iremote.common.jms.JMSUtil;
import com.iremote.common.jms.vo.InfoChange;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.Remote;
import com.iremote.domain.ZWaveDevice;
import com.iremote.infraredtrans.INotificationSender;
import com.iremote.infraredtrans.NormaUserNotificationSender;
import com.iremote.infraredtrans.NorthAmericanUserNotificationSender;
import com.iremote.infraredtrans.Remoter;
import com.iremote.infraredtrans.tlv.TlvWrap;
import com.iremote.service.IremotepasswordService;
import com.iremote.service.PhoneUserService;
import com.iremote.service.ZWaveDeviceService;

public class ZwaveReportBean {
	
	private static Log log = LogFactory.getLog(ZwaveReportBean.class);
	
	private Remote remote;
	private Remoter remoter;
	private String deviceid ;
	private int phoneuserid;
	private PhoneUser phoneuser;
	private int nuid ;
	private byte[] cmd ;
	private Date reporttime;
	private int intreportime;
	private ZWaveDevice device ;
	private String operator ;	
	private Integer apptokenid ;
	private CommandValue commandvalue;
	private int operatesource;
	private byte[] report;
	@Deprecated
	private INotificationSender notificationsender;
	private long reportid = System.currentTimeMillis();
	private int reportsequence ;
	
	public void setReport(String deviceid , byte[] report)
	{
		this.deviceid = deviceid ;
		this.report = report;
		
		nuid = TlvWrap.readInt(report, TagDefine.TAG_NUID, TagDefine.TAG_HEAD_LENGTH);
		cmd = TlvWrap.readTag(report, TagDefine.TAG_ZWAVE_COMMAND, TagDefine.TAG_HEAD_LENGTH);
		operator = TlvWrap.readString(report, TagDefine.TAG_OPERATOR, TagDefine.TAG_HEAD_LENGTH);
		apptokenid = TlvWrap.readInteter(report, TagDefine.TAG_APP_USER_TOKEN_ID, TagDefine.TAG_HEAD_LENGTH);
		operatesource = TlvWrap.readInt(report, TagDefine.TAG_OPERATION_TYPE, TagDefine.TAG_HEAD_LENGTH);
		reportsequence = TlvWrap.readInt(report, TagDefine.TAG_SEQUENCE, TagDefine.TAG_HEAD_LENGTH);
		
		reporttime = parseReportTime(report);
		intreportime = TlvWrap.readInt(report, TagDefine.TAG_TIME, TagDefine.TAG_HEAD_LENGTH);
		commandvalue = CommandParser.parse(cmd);
	}
	
	public Integer getApptokenid() {
		return apptokenid;
	}

	public boolean init()
	{
		try
		{
			if ( nuid == Integer.MIN_VALUE || cmd == null )
				return false;
			if ( commandvalue == null )
				return false ;

			queryremote();
			readPhoneuserid();
			device = queryZWaveDevice();
			
			if ( device == null && (nuid == 1 || nuid >= 10000 ) )
				if ( createSelfdefineDevice() == false )
					return false ;
			
			if( device == null || device.getEnablestatus() == IRemoteConstantDefine.DEVICE_ENABLE_STATUS_DISENABLE ) 
				return false;
						
			initnotificationsender();

			return true;
		}
		catch(Throwable t )
		{
			log.error(t.getMessage() , t);
		}
		return false ;
	}
	
	private ZWaveDevice queryZWaveDevice()
	{
		ZWaveDeviceService svr = new ZWaveDeviceService();
		return svr.querybydeviceid(deviceid , nuid);
	}
	
	private boolean createSelfdefineDevice()
	{
		device = DeviceHelper.createSelfdefineDevice(deviceid, nuid);
		if ( device == null )
			return false ;

		if ( this.phoneuser != null )
			this.phoneuser.setLastupdatetime(new Date());
		
		PhoneUserHelper.sendInfoChangeMessage(phoneuser);

		JMSUtil.sendmessage(IRemoteConstantDefine.NOTIFICATION_INFO_CHANGED, new InfoChange(deviceid , new Date() , reportid) );
		return true;
	}
	
	private void initnotificationsender()
	{
		if ( Utils.getRemotePlatform(deviceid) == IRemoteConstantDefine.PLATFORM_NORTH_AMERICAN )
			notificationsender = new NorthAmericanUserNotificationSender();
		else 
			notificationsender = new NormaUserNotificationSender();
	}
	
	private void queryremote()
	{
		IremotepasswordService svr = new IremotepasswordService();
		remote = svr.getIremotepassword(deviceid);
	}
	
	protected Date parseReportTime(byte[] request)
	{
		return TlvWrap.readTime(request, 104, 4);
	}
	
	
	private void readPhoneuserid()
	{
		if ( this.remote == null || this.remote.getPhoneuserid() == null)
			return  ;
		this.phoneuserid = this.remote.getPhoneuserid();
		PhoneUserService pus = new PhoneUserService();
		phoneuser = pus.query(phoneuserid);
	}

	public Remote getRemote() {
		return remote;
	}

	public String getDeviceid() {
		return deviceid;
	}

	public int getPhoneuserid() {
		return phoneuserid;
	}

	public PhoneUser getPhoneuser() {
		return phoneuser;
	}

	public int getNuid() {
		return nuid;
	}

	public byte[] getCmd() {
		return cmd;
	}

	public Date getReporttime() {
		return reporttime;
	}

	public ZWaveDevice getDevice() {
		return device;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public CommandValue getCommandvalue() {
		return commandvalue;
	}

	public int getOperatesource() {
		return operatesource;
	}

	public byte[] getReport() {
		return report;
	}
	
	@Deprecated
	public INotificationSender getNotificationsender() {
		return notificationsender;
	}

	public long getReportid() {
		return reportid;
	}

	public int getReportsequence() {
		return reportsequence;
	}

	public Remoter getRemoter() {
		return remoter;
	}

	public void setRemoter(Remoter remoter) {
		this.remoter = remoter;
	}

	public int getIntreportime()
	{
		return intreportime;
	}

}
