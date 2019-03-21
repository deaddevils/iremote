package com.iremote.advert;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.ServerRuntime;
import com.iremote.common.Utils;
import com.iremote.domain.AdvertBanner;
import com.iremote.domain.AdvertBannerPic;
import com.iremote.domain.OemProductor;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.PhoneUserAttribute;
import com.iremote.service.AdvertBannerPicService;
import com.iremote.service.AdvertBannerService;
import com.iremote.service.OemProductorService;
import com.iremote.service.PhoneUserAttributeService;

public class AdvertAction {

	@SuppressWarnings("unused")
	private static Log log = LogFactory.getLog(AdvertAction.class);
	
	private String os ;
	private int height = 200;
	private int width = 500 ;
	private int platform = ServerRuntime.getInstance().getSystemcode() ;
	private String language = IRemoteConstantDefine.DEFAULT_LANGUAGE;
	private long time;
	private String ip ;
	private int longitude;
	private int latitude;
	private PhoneUser phoneuser;
	private List<String> picurllist = new ArrayList<String>();

	private List<String> adverpicname;
	
	public String execute(){
		if(phoneuser!=null){
			platform = phoneuser.getPlatform();
		}
		OemProductorService ops = new OemProductorService();
		OemProductor op = ops.querybyplatform(platform);
		if(phoneuser!=null){
			PhoneUserAttributeService puas = new PhoneUserAttributeService();
			PhoneUserAttribute userattribute = puas.querybyphoneuseridandcode(phoneuser.getPhoneuserid(), "advertbannerid");
			if(userattribute!=null){
				AdvertBannerService abs = new AdvertBannerService();
				AdvertBanner ab = abs.queryById(Integer.parseInt(userattribute.getValue()));
				if(ab.getIsdefault()==0){
					AdvertBannerPicService abps = new AdvertBannerPicService();
					List<AdvertBannerPic> piclist = abps.queryByAdvertbannerid(ab.getAdvertbannerid());
					if(piclist!=null){
						for(AdvertBannerPic abp:piclist){
							picurllist.add(abp.getUrl());
						}
					}
				}
			}
		}
		if ( op != null && StringUtils.isNotBlank(op.getAdverpicname())){
			JSONArray ja = JSON.parseArray(op.getAdverpicname());
			
			if ( ja.size() > 0 ){
				adverpicname = new ArrayList<String>();
				for ( int i = 0 ; i < ja.size() ; i ++ )
					adverpicname.add(ja.getString(i));
			}
			
		}
		
		time = System.currentTimeMillis() / 3600000;
		if(picurllist!=null&&picurllist.size()>0){
			return "selfadvert";
		}
		if ( StringUtils.isBlank(language))
			language = Utils.getUserLanguage(platform, language);
		if ( platform == 0 && IRemoteConstantDefine.DEFAULT_LANGUAGE.equalsIgnoreCase(language) )
			return "JWZH_ZH_CH";
		else if ( platform == 0 && !IRemoteConstantDefine.DEFAULT_LANGUAGE.equalsIgnoreCase(language) )
			return "JWZH_EN_US";
		else if ( platform != 0 && IRemoteConstantDefine.DEFAULT_LANGUAGE.equalsIgnoreCase(language) )
			return "OEM_ADVERT_ZH_CN";
		else 
			return "OEM_ADVERT_EN_US";
	}
	
	public List<String> getAdverpicname(){
		return adverpicname;
	}

	public String getOs(){
		return os;
	}
	public void setOs(String os){
		this.os = os;
	}
	public int getHeight(){
		return height;
	}
	public void setHeight(int height){
		this.height = height;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width){
		this.width = width;
	}

	public void setPlatform(int platform){
		this.platform = platform;
	}

	public String getLanguage(){
		if ( language == null )
			return IRemoteConstantDefine.DEFAULT_LANGUAGE.toLowerCase();
		return language.toLowerCase();
	}

	public void setLanguage(String language){
		this.language = language;
	}

	public long getTime(){
		return time;
	}

	public String getIp(){
		return ip;
	}

	public void setIp(String ip){
		this.ip = ip;
	}

	public void setLongitude(int longitude){
		this.longitude = longitude;
	}

	public void setLatitude(int latitude){
		this.latitude = latitude;
	}

	public int getLongitude(){
		return longitude;
	}

	public int getLatitude(){
		return latitude;
	}
	
	public int getPlatform() {
		return platform;
	}
	public void setPhoneuser(PhoneUser phoneuser) {
		this.phoneuser = phoneuser;
	}

	public List<String> getPicurllist() {
		return picurllist;
	}

}
