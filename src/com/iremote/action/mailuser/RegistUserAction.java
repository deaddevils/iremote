package com.iremote.action.mailuser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.iremote.action.helper.PageHelper;
import com.iremote.common.Utils;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.Remote;
import com.iremote.service.IremotepasswordService;
import com.iremote.service.PhoneUserService;
import com.iremote.vo.RegistUserVO;
import com.opensymphony.xwork2.Action;

public class RegistUserAction{
	private PageHelper<RegistUserVO> pager;
	private int currentPage =1;
	private int pageSize = 25;
	private String name;
	private String phonenumber;
	private String deviceid;
	private Date validfrom;
	private Date validthrough;
	
    public String execute(){
    	
    	/*if(du.getValidfrom() != null)
			validfrom =Utils.formatTime(du.getValidfrom());
		if(du.getValidthrough() != null )
			validthrough =Utils.formatTime(du.getValidthrough());*/
    	
    	PhoneUserService pus = new PhoneUserService();
    	IremotepasswordService rs = new IremotepasswordService();
    	int recordTotal = pus.queryTotalCount(name,phonenumber,deviceid,validfrom,validthrough);
    	List<PhoneUser> queryPagePhoneUser = pus.queryPagePhoneUser(name,phonenumber,deviceid,validfrom,validthrough,(currentPage-1)*pageSize,pageSize);
    	pager = new PageHelper<RegistUserVO>();
    	List<RegistUserVO> registUserList = new ArrayList<>();
        for(PhoneUser p:queryPagePhoneUser){
        	RegistUserVO registUserVO = new RegistUserVO();
        	List<Remote> querybyPhoneUserid = rs.querybyPhoneUserid(p.getPhoneuserid());
        	registUserVO.setPhoneuser(p);
        	registUserVO.setDevices(querybyPhoneUserid);
        	registUserList.add(registUserVO);
        }
        
        pager.setCurrentPage(currentPage);
        pager.setPageSize(pageSize);
        pager.setRecordTotal(recordTotal);
        pager.setContent(registUserList);

        return Action.SUCCESS;
    }

	public PageHelper<RegistUserVO> getPager() {
		return pager;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public String getDeviceid() {
		return deviceid;
	}

	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}

	public Date getValidfrom() {
		return validfrom;
	}

	public void setValidfrom(Date validfrom) {
		this.validfrom = validfrom;
	}

	public Date getValidthrough() {
		return validthrough;
	}

	public void setValidthrough(Date validthrough) {
		this.validthrough = validthrough;
	}

}
