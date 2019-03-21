package com.iremote.action.phoneuser;

import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.imageio.ImageIO;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.SystemParameter;
import com.iremote.service.PhoneUserService;
import com.iremote.service.SystemParameterService;
import com.opensymphony.xwork2.Action;

public class SetAvatarAction {

	private int resultCode = ErrorCodeDefine.SUCCESS ;
	private String imageFileName; 
    private static String[] TYPE = { ".jpg", ".jpeg", ".png", ".bmp", ".gif" };
    private File image;
    private String avatar= "";
    private PhoneUser phoneuser;
    private static Log log = LogFactory.getLog(SetAvatarAction.class);
	public String execute(){
		try {
			String fileName = imageFileName;
			String suffix = fileName.substring(fileName.lastIndexOf('.'));
			boolean flag = false;
			for (String type : TYPE) {
				if (StringUtils.endsWithIgnoreCase(fileName, type)) {
					flag = true;
					break;
				}
			}
			if (!flag) {
				resultCode = ErrorCodeDefine.PARMETER_ERROR;
				return Action.SUCCESS;
			}
			InputStream is = new FileInputStream(image);
			flag = isImage(is);
			if (!flag) {
				resultCode = ErrorCodeDefine.PARMETER_ERROR;
				return Action.SUCCESS;
			}
			Date date = new Date();
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmss");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMM");
			String xx = Utils.createsecuritytoken(2);
	        String dir = sdf2.format(date);
	        String newfilename = sdf1.format(date)+xx;
	        String dbavatar = phoneuser.getAvatar();

	        if(!StringUtils.isEmpty(dbavatar)){
	        	//https://127.0.0.1:8080/iremotestatic/uploadfile/avatar/201809/310_20180920132455xx.jpg
	        	String dir1 = dbavatar.substring(0, dbavatar.lastIndexOf("/"));
	        	dir = dir1.substring(dir1.lastIndexOf("/")+1);
	        	delFile("/opt/tools/iremotestatic/uploadfile/avatar/"+dir+"/" + dbavatar.substring(dbavatar.lastIndexOf("/")+1));
	        }
	        String newFileName = phoneuser.getPhoneuserid()+"_"+newfilename+ suffix;
			SystemParameterService sps = new SystemParameterService();
			SystemParameter urldomain = sps.querybykey(IRemoteConstantDefine.SYSTEMPARAMETER_DOMAIN_NAME);
			String url = urldomain.getStrvalue();
			url = "https:"+url.substring(url.indexOf(":")+1);
			File file = new File("/opt/tools/iremotestatic/uploadfile/avatar/"+dir+"/" + newFileName);
			FileUtils.copyFile(image, file);
			avatar = url+"/iremotestatic/uploadfile/avatar/"+dir+"/" + newFileName;
			phoneuser.setAvatar(avatar);
			
	    } catch (Exception e) {
	    	log.error(e.getMessage() , e);
	    	resultCode = ErrorCodeDefine.UNKNOW_ERROR;
			return Action.SUCCESS;
	    }

		return  Action.SUCCESS;
    }
    
	private boolean isImage(InputStream inputStream) {
        if (inputStream == null) {
            return false;
        }
        Image img;
        try {
            img = ImageIO.read(inputStream);
            return !(img == null || img.getWidth(null) <= 0 || img.getHeight(null) <= 0);
        } catch (Exception e) {
            return false;
        }
    }
	
	private boolean delFile(String path) {
	    boolean flag = false;
	    File file = new File(path);
	    if (!file.exists()) {
	        return flag;
	    }
	    try{
	        flag = file.delete();
	    }catch (Exception e){
	        e.printStackTrace();
	    }
	    return flag;
	}
	public int getResultCode() {
		return resultCode;
	}

	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}

	public void setImage(File image) {
		this.image = image;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setPhoneuser(PhoneUser phoneuser) {
		this.phoneuser = phoneuser;
	}
	
}
