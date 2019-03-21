package cn.com.isurpass.iremote.opt.gateway;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.Utils;
import com.iremote.common.encrypt.AES;
import com.iremote.domain.ThirdPart;
import com.iremote.service.BaseService;
import com.iremote.service.ThirdPartService;
import com.opensymphony.xwork2.Action;

public class CreateThirdPartAction{
	private int resultCode = ErrorCodeDefine.SUCCESS ;
	private String code;
	private String name;
	private int platform;
	private String adminprefix;
	private String password;
	private ThirdPartService sps = new ThirdPartService();
	
	public static void main(String[] args) {
		String a = null;
		System.out.println(StringUtils.isNotBlank(a));
	}
	
	public String execute() throws Exception{
		if(StringUtils.isBlank(code)||StringUtils.isBlank(name)){//data verification is not done on web
			resultCode = ErrorCodeDefine.PARMETER_ERROR;
			return Action.ERROR;
		}
		if(StringUtils.isNotBlank(adminprefix)){
			List<ThirdPart> thirdlist = sps.query();
			if(thirdlist!=null&&thirdlist.size()>0){
				for(ThirdPart t : thirdlist){
					if(StringUtils.isNotBlank(t.getAdminprefix())&&(t.getAdminprefix().startsWith(adminprefix)||adminprefix.startsWith(t.getAdminprefix()))){
						resultCode = ErrorCodeDefine.PARMETER_ERROR;
						return Action.ERROR;
					}
				}
			}
		}
		ThirdPart third = sps.query(code);
		if(third==null){
			third = new ThirdPart();
			third.setCode(code);
		}
        third.setName(name);
        third.setPlatform(platform);
        third.setAdminprefix(adminprefix);
        
        third.setPassword(CreatePassWord(code));
        third.setCreatetime(new Date());
        BaseService<ThirdPart> baseservice = new BaseService<ThirdPart>();
        baseservice.saveOrUpdate(third);
		return Action.SUCCESS;	
	}
	
	private String CreatePassWord(String code){
		password = Utils.createsecuritytoken(24);
		String enpw = sps.encryptPassword(code, password);
		return enpw;
	}
	
	public int getResultCode() {
		return resultCode;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setPlatform(int platform) {
		this.platform = platform;
	}
	public void setAdminprefix(String adminprefix) {
		this.adminprefix = adminprefix;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCode() {
		return code;
	}
	public String getAdminprefix() {
		return adminprefix;
	}
	
}
