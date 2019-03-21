package cn.com.isurpass.iremote.manage;

import com.iremote.domain.AppVersion;
import com.iremote.service.AppVersionService;
import com.opensymphony.xwork2.Action;

public class EditApkVersionAction 
{
	private int resultCode ;
	private int appversionid;
	private int platform;
	private String latestversion;
	private int latestiversion;
	private String downloadurl;
	private String description;
	
	public String execute()
	{
		AppVersionService avs = new AppVersionService();
		
		AppVersion av = avs.query(platform);
		if ( av == null )
			av = new AppVersion();
		av.setPlatform(platform);
		av.setLatestiversion(latestiversion);
		av.setLatestversion(latestversion);
		av.setDescription(description);
		av.setDownloadurl(downloadurl);
		
		avs.saveOrUpdate(av);
		
		return Action.SUCCESS;
	}

	public int getResultCode() {
		return resultCode;
	}

	public void setAppversionid(int appversionid) {
		this.appversionid = appversionid;
	}

	public void setLatestversion(String latestversion) {
		this.latestversion = latestversion;
	}

	public void setLatestiversion(int latestiversion) {
		this.latestiversion = latestiversion;
	}

	public void setDownloadurl(String downloadurl) {
		this.downloadurl = downloadurl;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setPlatform(int platform) {
		this.platform = platform;
	}
}
