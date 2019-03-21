package com.iremote.infraredtrans.serianet.dsc;

import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.iremote.action.helper.NotificationHelper;
import com.iremote.action.phoneuser.SetPhoneUserArmStatus;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.jms.JMSUtil;
import com.iremote.common.jms.vo.ZWaveDeviceStatusChange;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.Remote;
import com.iremote.domain.ZWaveDevice;
import com.iremote.domain.ZWaveSubDevice;
import com.iremote.infraredtrans.zwavecommand.notifiy.IZwaveReportConsumer;
import com.iremote.infraredtrans.zwavecommand.notifiy.ZwaveReportNotifyManager;
import com.iremote.service.PhoneUserService;
import com.iremote.service.RemoteService;

public class DSCHelper
{
	private static Log log = LogFactory.getLog(DSCHelper.class);
	private static Byte[][] DSC_RESPOSNE_KEY_FOR_PARTITON_1 = new Byte[][]{toByteArray("9001"), //Code Required
																			toByteArray("501"), //Command Error
																			toByteArray("502"), //System Error
																			toByteArray("6511"),//Partition Not Ready
																			toByteArray("6521"),//Partition Armed - Descriptive Mode
																			toByteArray("6541"),//Partition In Alarm
																			toByteArray("6551"),//Partition Disarmed
																			toByteArray("6561"),//Exit Delay in Progress
																			toByteArray("6571"),//Entry Delay in Progress
																			toByteArray("6701"),//Invalid Access Code
																			toByteArray("6721"),//Fail to Arm
																			toByteArray("6731"),//Partition Busy
																			};

	private static Byte[][] DSC_RESPONSE_KEY_FOR_BYPASS = new Byte[][]{
			toByteArray("500"),
			toByteArray("501"), //Command Error
			toByteArray("502"), //System Error
//			toByteArray("673"), //Partition busy
	};

	private static Byte[][] DSC_RESPONSE_KEY_FOR_BYPASS_RESULT = new Byte[][]{
			toByteArray("901"),
			toByteArray("501"), //Command Error
			toByteArray("502"), //System Error
			toByteArray("673"), //Partition busy
	};

	private static Byte[][] DSC_RESONPSE_KEY_FOR_PARTION_DIS_ARM = new Byte[][]{toByteArray("9001"), //Code Required
			toByteArray("655"),//Partition Disarmed
			toByteArray("501"), //Command Error
			toByteArray("502"), //System Error
	};

    private static Byte[][] DSC_PARTITION_BUSY_OR_NEED_ACCESS_CODE  = new Byte[][]{
            toByteArray("673"),
            toByteArray("90100032Enter Your      Access Code"),
    };

	private static Byte[] DSC_LOCK_KEY_FOR_PARTITON_1 = toByteArray("P1 OPERATING");

	public static Byte[][] getArmCommandResponseKeyforPartion1()
	{
		return DSC_RESPOSNE_KEY_FOR_PARTITON_1;
	}

	public static Byte[][] getDscPartitionBusyOrNeedAccessCode(){
        return DSC_PARTITION_BUSY_OR_NEED_ACCESS_CODE;
    }

	public static Byte[][] getDscResponseKeyForBypassResult()
	{
		return DSC_RESPONSE_KEY_FOR_BYPASS_RESULT;
	}

	public static Byte[][] getBypassComanndResponseKey()
	{
		return DSC_RESPONSE_KEY_FOR_BYPASS;
	}

	public static Byte[][] getPartitionDisarmResponseKey()
	{
		return DSC_RESONPSE_KEY_FOR_PARTION_DIS_ARM;
	}

	public static Byte[] getLockKeyforPartion1()
	{
		return DSC_LOCK_KEY_FOR_PARTITON_1;
	}

	public static Byte[] toByteArray(String command)
	{
		if ( StringUtils.isBlank(command))
			return new Byte[0];

		Byte[] b = new Byte[command.length()];
		byte[] cb = command.getBytes();

		for ( int i = 0 ; i< b.length ; i ++ )
			b[i] = cb[i];
		return b ;
	}

	public static void synArmStatus(String deviceid , int dscpartitionid , int armstatus)
	{
		if ( dscpartitionid != 1 )
			return ;
		if ( isReportofUserOperate(deviceid) )
			return ;

		SetPhoneUserArmStatus action = new SetPhoneUserArmStatus();

		action.setArmstatus(armstatus);

		RemoteService rs = new RemoteService();
		Remote r = rs.getIremotepassword(deviceid);

		Integer puid = r.getPhoneuserid();
		if ( puid == null )
			return ;

		PhoneUserService pus = new PhoneUserService();
		PhoneUser pu = pus.query(puid);
		action.setPhoneuser(pu);
		action.setSavenotification(false);
		action.execute();

		if ( action.getResultCode() != ErrorCodeDefine.SUCCESS )
		{
			List<ZWaveDevice> lst = action.getNotreadyappliance();

			StringBuffer sb = new StringBuffer();
			for ( ZWaveDevice zd : lst )
			{
				if ( sb.length() > 0 )
					sb.append(",");
				sb.append(zd.getName());
			}

			NotificationHelper.pushArmFailedMessage(pu , sb.toString(),"dscsuccessdevicefailed");
		}
	}

	private static boolean isReportofUserOperate(String deviceid)
	{
		byte[] b = new byte[DSCHelper.getLockKeyforPartion1().length];
		for ( int i = 0 ; i < b.length ; i ++ )
			b[i] = DSCHelper.getLockKeyforPartion1()[i];

		List<IZwaveReportConsumer> lst = ZwaveReportNotifyManager.getInstance().getConsumerList(deviceid , IRemoteConstantDefine.DEVICE_NUID_DSC,b);
		return ( lst != null && lst.size() > 0 );
	}


	public static void pushDscAlarmMessage(ZWaveDevice zd , int channelid , Integer warningstatus)
	{
		pushDscAlarmMessage(IRemoteConstantDefine.WARNING_TYPE_DSC_ALARM , zd , channelid , warningstatus);
	}

	public static void pushDscUnalarmMessage(ZWaveDevice zd , int channelid , Integer warningstatus)
	{
		pushDscAlarmMessage("unalarm" + IRemoteConstantDefine.WARNING_TYPE_DSC_ALARM , zd , channelid , warningstatus);
	}

	public static boolean isWarning(String warningstatuses) {
		return StringUtils.isNotBlank(warningstatuses) && !"[]".equals(warningstatuses);
	}

	private static void pushDscAlarmMessage(String msg , ZWaveDevice zd , int channelid , Integer warningstatus)
	{
		ZWaveDeviceStatusChange zde = new ZWaveDeviceStatusChange();
		try {
			PropertyUtils.copyProperties(zde, zd);
		} catch (Throwable t) {
			log.error(t.getMessage() , t);
		}
		zde.setName(createname(zd, channelid));
		zde.setWarningstatus(warningstatus);
		zde.setEventtime(new Date());
		zde.setEventtype(msg);
		zde.setDevicetype(IRemoteConstantDefine.DEVICE_TYPE_DSC);
		zde.setTaskIndentify(System.currentTimeMillis());
		zde.setChannel(channelid);

		JMSUtil.sendmessage(msg, zde);
	}

	private static String createname(ZWaveDevice zd , int channelid)
	{
		String name = zd.getName();
		if ( name == null )
			name = "";
		if ( zd.getzWaveSubDevices() == null )
			return name;
		for ( ZWaveSubDevice zds : zd.getzWaveSubDevices())
		{
			if ( zds.getChannelid() == channelid)
			{
				if ( StringUtils.isNotBlank(zds.getName() ))
				{
					return String.format("%s %s", name , zds.getName());
				}
			}
		}
		return name;
	}
}
