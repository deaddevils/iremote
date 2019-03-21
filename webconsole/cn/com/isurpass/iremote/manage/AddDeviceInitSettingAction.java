package cn.com.isurpass.iremote.manage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.util.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iremote.domain.DeviceInitSetting;
import com.iremote.service.DeviceInitSettingService;
import com.opensymphony.xwork2.Action;

public class AddDeviceInitSettingAction 
{

	private String resultCode;

    //对应表单的myFile  <input type="file" name="adddeviceInitSettingFile"/>
    private File adddeviceInitSettingFile;

	public String execute() throws Exception {
  
		String data = readFile(adddeviceInitSettingFile);

        JSONObject json = JSON.parseObject(data);
		JSONArray ja = json.getJSONArray("typeArray"); 
		
		for ( int i = 0 ; i < ja.size() ; i ++)   
		{
			JSONObject dt = ja.getJSONObject(i);
			String type = dt.getString("typeId");
			JSONArray jads = dt.getJSONArray("devices");
			
			for ( int j = 0 ; j < jads.size() ; j ++)
			{
				JSONObject jds = jads.getJSONObject(j);
				String mid = jds.getString("mid").toLowerCase().trim();
				
				DeviceInitSetting dls = null;
				DeviceInitSettingService service = new DeviceInitSettingService();
				dls = service.querybymid(mid);
				
				if(dls == null){
					dls = new DeviceInitSetting();
				}

				dls.setMid(mid);
				dls.setManufacture(jds.getString("manufacture"));
				dls.setDevicetype(type);
				
				String cmds = jds.getString("initCmds");
				JSONArray cmdja ;
//				if (StringUtils.isNotBlank(cmds) ){
				if(cmds!=null){
					cmdja = JSON.parseArray(cmds);
				}else{
					cmdja = new JSONArray();					
				}
				cmdja.add("8611");
				dls.setInitcmds(cmdja.toJSONString());
				service.saveOrUpdate(dls);  				
			}
		}
		resultCode = Action.SUCCESS;
		return  Action.SUCCESS;
    }
    
   
	public String readFile(File file) throws IOException {
    	StringBuffer str = new StringBuffer();
		InputStream is = new FileInputStream(file);
		InputStreamReader reader = new InputStreamReader(is,"utf-8");
		BufferedReader br = new BufferedReader(reader);
		String line;
        while ((line = br.readLine()) != null) {
        	str.append(line); 
        }
        br.close();
        return str.toString();
    }
    
    //File类型转换为String类型
    public String readFile(String filePath, String charsetName)
            throws IOException {
        if (TextUtils.isEmpty(filePath))
            return null;
        if (TextUtils.isEmpty(charsetName))
            charsetName = "utf-8";
        File file = new File(filePath);
        StringBuilder fileContent = new StringBuilder("");
        if (file == null || !file.isFile())
            return null;
        BufferedReader reader = null;
        try {
            InputStreamReader is = new InputStreamReader(new FileInputStream(
                    file), charsetName);
            reader = new BufferedReader(is);
            String line = null;
            while ((line = reader.readLine()) != null) {
                if (!fileContent.toString().equals("")) {	
                    fileContent.append("\r\n");
                }
                fileContent.append(line);
            }
            return fileContent.toString();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

	


	public void setAdddeviceInitSettingFile(File adddeviceInitSettingFile) {
		this.adddeviceInitSettingFile = adddeviceInitSettingFile;
	}


	public String getResultCode() {
		return resultCode;
	}
    
    
}

	/*
	private String mid;
	private String manufacture;
	private String devicetype;
	private String initcmds;
	private String version ;
	
	public String execute()
	{
		SystemParameterService sps = new SystemParameterService();
		SystemParameter sp = sps.querybykey("DeviceInitSetting_Version");
		if ( sp != null )
			sp.setStrvalue(version);
		else 
		{
			sp = new SystemParameter();
			sp.setStrvalue(version);
			sp.setKey("DeviceInitSetting_Version");
			sps.save(sp);
		}

		if ( StringUtils.isBlank(mid)
			||StringUtils.isBlank(manufacture)
			||StringUtils.isBlank(devicetype))
		{
			this.resultCode = ErrorCodeDefine.PARMETER_ERROR;
			return Action.SUCCESS;
		}
		
		DeviceInitSettingService diss = new DeviceInitSettingService();
		DeviceInitSetting dis = diss.querybymid(mid);
		
		if ( dis != null )
		{
			resultCode = ErrorCodeDefine.DEVICE_HAS_EXIST;
			return Action.SUCCESS;
		}
		
		dis = new DeviceInitSetting();
		dis.setDevicetype(devicetype);
		dis.setManufacture(manufacture);
		dis.setMid(mid.toLowerCase());
		dis.setInitcmds(initcmds);
		
		diss.save(dis);
		
		if ( StringUtils.isBlank(version))
			return Action.SUCCESS;

		return Action.SUCCESS;
	}
	
	public int getResultCode() {
		return resultCode;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public void setManufacture(String manufacture) {
		this.manufacture = manufacture;
	}
	public void setDevicetype(String devicetype) {
		this.devicetype = devicetype;
	}
	public void setInitcmds(String initcmds) {
		this.initcmds = initcmds;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	*/
	


