package cn.com.isurpass.iremote.manage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.iremote.domain.DeviceInitSetting;
import com.iremote.domain.SystemParameter;
import com.iremote.service.DeviceInitSettingService;
import com.iremote.service.SystemParameterService;
import com.opensymphony.xwork2.Action;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class DumpDeviceInitSettingAction 
{
	private JSONObject setting = new JSONObject();
	
	public String execute()
	{
		SystemParameterService sps = new SystemParameterService();
		SystemParameter sp = sps.querybykey("DeviceInitSetting_Version");
		if ( sp == null )
			return Action.SUCCESS;
		setting.put("version", sp.getStrvalue());
		
		DeviceInitSettingService diss = new DeviceInitSettingService();
		JSONArray ja = new JSONArray();
		
		for ( int i = 1 ; i < 100 ; i ++ )
		{
			List<DeviceInitSetting> lst = diss.query(String.valueOf(i));
			if ( lst == null || lst.size() == 0 )
				continue;
			
			JSONObject typeArray = new JSONObject();
			typeArray.put("typeId", i);
			
			JSONArray devices = new JSONArray();
			
			for ( DeviceInitSetting dis : lst )
			{
				JSONObject d = new JSONObject();
				d.put("manufacture", dis.getManufacture());
				d.put("mid", dis.getMid());
				if ( StringUtils.isNotBlank(dis.getInitcmds()))
					d.put("initCmds", JSONArray.fromObject(dis.getInitcmds()));

				devices.add(d);
			}
			typeArray.put("devices", devices);
			ja.add(typeArray);
		}
		
		setting.put("typeArray", ja);
		
		return Action.SUCCESS;
	}
	
	 public InputStream getData()
	 {
		 InputStream is = new ByteArrayInputStream(setting.toString().getBytes());

		 return is; 
	 }
}
