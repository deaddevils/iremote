package cn.com.isurpass.iremote.opt.qrcode;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.Utils;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilege;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeType;
import com.iremote.domain.GatewayInfo;
import com.iremote.domain.PhoneUser;
import com.iremote.service.GatewayInfoService;
import com.opensymphony.xwork2.Action;

@DataPrivilege(dataprivilgetype = DataPrivilegeType.MODIFY, domain = "admin", parameter = "noparameter")
public class CreateOneGatewayQrcodeAction
{
	private static Log log = LogFactory.getLog(CreateOneGatewayQrcodeAction.class);
	private static Logger logqrcode = Logger.getLogger("gatewayqrcode");
	
	private static String GETWAY_TYPE_NBLOCK_GATEWAY = "nblockgateway";
	private static String GETWAY_TYPE_FINGER_PRINT = "fingerprintreader";
	private static String GETWAY_TYPE_LOCK_GATEWAY = "lockgateway";
	
	
	private int resultCode = ErrorCodeDefine.SUCCESS;
	private String deviceid = ""; 
	private String gatewaytype;
	private String qid ;
	private boolean saveindb = false ;
	private boolean exists = false ;
	private String qrstring ;
	private String qrsql ;
	private PhoneUser phoneuser ;
	private String filename ;
	private String description;
		
	public String execute()
	{
		if ( StringUtils.isBlank(deviceid))
			return Action.SUCCESS;
		if ( phoneuser == null )
		{
			resultCode = ErrorCodeDefine.NO_PRIVILEGE;
			return Action.SUCCESS;
		}

		GatewayInfoService gis = new GatewayInfoService();
		GatewayInfo gi = gis.querybydeviceid(deviceid);
		if ( gi != null )
		{
			if ( StringUtils.isNotBlank(gi.getGatewaytype()) && !gi.getGatewaytype().equals(gatewaytype) )
			{
				resultCode = ErrorCodeDefine.NO_PRIVILEGE;
				return Action.SUCCESS;
			}
			qid = gi.getQrcodekey(); 
			exists = true;
		}
		
		if ( StringUtils.isBlank(qid))
			qid = Utils.createsecuritytoken(20);
		
		JSONObject json = new JSONObject();
		
		String gt = gatewaytype ;
		if ( GETWAY_TYPE_NBLOCK_GATEWAY.equals(gt) || GETWAY_TYPE_FINGER_PRINT.equals(gt) )
			gt = GETWAY_TYPE_LOCK_GATEWAY;
		json.put("type", gt);
		json.put("qid", qid);
		
		//PowerFreeDeviceQrcodeCreator.createCode(json.toJSONString() , String.format("e:\\qrcode\\%s.png", deviceid));
		qrstring = json.toJSONString();
		qrsql = String.format("insert into gatewayinfo (deviceid ,gatewaytype, qrcodekey) values ( '%s' , '%s', '%s');" ,deviceid ,gatewaytype,json.getString("qid"));

		if ( gi == null && this.saveindb)
		{
			gi = new GatewayInfo();
			gi.setDeviceid(deviceid);
			gi.setQrcodekey(qid);
			gi.setGatewaytype(gatewaytype);
			gis.save(gi);
		}
		
		filename = String.format("%s.png", deviceid);
		
		if ( !exists )
			logqrcode.error(String.format("%s,%s,%s,%b,%s,%s", deviceid , gatewaytype,qid,saveindb,phoneuser.getPhonenumber() ,description));
		
		return Action.SUCCESS;
	}
	
	public String forword()
	{
		return Action.SUCCESS;
	}

	public String getQrstring()
	{
		return qrstring;
	}
	
	public String getEncodeqrstring()
	{
		try {
			return URLEncoder.encode(qrstring , "UTF-8");
		} catch (UnsupportedEncodingException e) {
			log.error(e.getMessage() , e);
		}
		return "" ;
	}

	public void setDeviceid(String deviceid)
	{
		this.deviceid = deviceid;
	}

	public void setGatewaytype(String gatewaytype)
	{
		this.gatewaytype = gatewaytype;
	}

	public void setQid(String qid)
	{
		this.qid = qid;
	}

	public void setSaveindb(boolean saveindb)
	{
		this.saveindb = saveindb;
	}

	public String getQrsql()
	{
		return qrsql;
	}

	public void setPhoneuser(PhoneUser phoneuser)
	{
		this.phoneuser = phoneuser;
	}

	public int getResultCode()
	{
		return resultCode;
	}

	public String getDeviceid()
	{
		return deviceid;
	}

	public String getGatewaytype()
	{
		return gatewaytype;
	}

	public boolean isSaveindb()
	{
		return saveindb;
	}

	public String getQid()
	{
		return qid;
	}

	public String getFilename()
	{
		return filename;
	}

	public boolean isExists()
	{
		return exists;
	}

	public String getDescription()
	{
		return description;
	}
	
	
}
