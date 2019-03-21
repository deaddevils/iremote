package com.iremote.action.partition;

import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.Utils;
import com.iremote.common.md5.MD5Util;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilege;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeType;
import com.iremote.dataprivilege.interceptorchecker.DataPrivileges;
import com.iremote.domain.Camera;
import com.iremote.domain.Partition;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.ZWaveDevice;
import com.iremote.service.CameraService;
import com.iremote.service.PartitionService;
import com.iremote.service.RemoteService;
import com.iremote.service.ZWaveDeviceService;
import com.opensymphony.xwork2.Action;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.StringUtils;

@DataPrivileges( dataprivilege = {
		@DataPrivilege(dataprivilgetype = DataPrivilegeType.MODIFY, domain = "zwavedevice", parameter = "zwavedeviceid"),
		@DataPrivilege(dataprivilgetype = DataPrivilegeType.MODIFY, domain = "camera", parameter = "cameraid")
})
public class AddPartition {
    protected int resultCode = ErrorCodeDefine.SUCCESS;
    protected int partitionid;
    protected String name;
    protected int delay;
    protected String password;
    protected int zwavedeviceid;
    protected String zwavedeviceids;
    protected int cameraid;
    protected String cameraids;
    protected PhoneUser phoneuser;

    public String execute() {
        if (StringUtils.isBlank(name) || delay < 0) {
            resultCode = ErrorCodeDefine.PARMETER_ERROR;
            return Action.SUCCESS;
        }
        PartitionService ps = new PartitionService();
        Partition p = new Partition();
        p.setName(name.trim());
        p.setDelay(delay);
        p.setPhoneuser(phoneuser);
        partitionid = ps.save(p);
        
        Partition query = ps.query(partitionid);
        if(!StringUtils.isEmpty(password)){
        	query.setPassword(MD5Util.MD5(String.valueOf(partitionid)+password));
        }
        
    	ZWaveDeviceService zds = new ZWaveDeviceService();
    	CameraService cs = new CameraService();
    	List<Integer> zwavedevicelist = Utils.jsontoIntList(zwavedeviceids);
    	List<Integer> cameralist = Utils.jsontoIntList(cameraids);
        
    	RemoteService rs = new RemoteService();
		List<String> ldid = rs.queryDeviceidbyPhoneUserid(phoneuser.getPhoneuserid());		
		List<Camera> lst = cs.querybydeviceid(ldid);
        if(zwavedeviceid!=0){
        	if(check(zwavedeviceid,false)){
            	return Action.SUCCESS;
            }
    		ZWaveDevice zd = zds.query(zwavedeviceid);
    		if(zd==null){
    			resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT;
                return Action.SUCCESS;
    		}
    		if ( PhoneUserHelper.checkIsMyZWaveDevice(phoneuser, zd) == false ){
    			ps.delete(query);
    			resultCode = ErrorCodeDefine.NO_PRIVILEGE;
    			return Action.SUCCESS;
    		}
    		zd.setPartitionid(partitionid);
        }
        
        if(zwavedevicelist.size()>0){
        	for(int z : zwavedevicelist){
        		if(check(z,false)){
                	return Action.SUCCESS;
                }
        		ZWaveDevice zd = zds.query(z);
        		if(query==null){
        			resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT;
                    return Action.SUCCESS;
        		}
        		if ( PhoneUserHelper.checkIsMyZWaveDevice(phoneuser, zd) == false ){
        			ps.delete(query);
        			resultCode = ErrorCodeDefine.NO_PRIVILEGE;
        			return Action.SUCCESS;
        		}
        	}
        	for(int z : zwavedevicelist){
        		ZWaveDevice zd = zds.query(z);
        		zd.setPartitionid(partitionid);
        	}
        }
        
        if(cameraid!=0){    		       	
        	Camera camera = cs.query(zwavedeviceid);
    		if(camera==null){
    			resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT;
                return Action.SUCCESS;
    		}
			if(!lst.contains(camera)){
				resultCode = ErrorCodeDefine.NO_PRIVILEGE;
    			return Action.SUCCESS;
			}
    		camera.setPartitionid(partitionid);
        }
        
        if(cameralist.size()>0){
        	for(int c : cameralist){
        		Camera camera = cs.query(c);
        		if(camera==null){
        			resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT;
                    return Action.SUCCESS;
        		}
        		if(!lst.contains(camera)){
    				resultCode = ErrorCodeDefine.NO_PRIVILEGE;
        			return Action.SUCCESS;
    			}
        	}
        	for(int c : cameralist){
        		Camera camera = cs.query(c);       		
        		camera.setPartitionid(partitionid);
        	}
        }
        phoneuser.setLastupdatetime(new Date());
        PhoneUserHelper.sendInfoChangeMessage(phoneuser);
        return Action.SUCCESS;
    }

    private boolean check(int zwavedeviceid,boolean flag){
    	if(PartitionHelper.checkisdsc(zwavedeviceid)){
			resultCode = ErrorCodeDefine.DSC_CANNOT_BE_ADDED_TO_PARTITION;
			flag = true;
		}
		if(!PartitionHelper.checkissensor(zwavedeviceid)){
			resultCode = ErrorCodeDefine.NON_SENSOR_CANNOT_BE_ADDED_TO_PARTITION;
			flag = true;
		}
		return flag;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public int getPartitionid() {
		return partitionid;
	}

	public int getResultCode() {
        return resultCode;
    }

	public void setDelay(int delay) {
		this.delay = delay;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setZwavedeviceid(int zwavedeviceid) {
		this.zwavedeviceid = zwavedeviceid;
	}

	public void setZwavedeviceids(String zwavedeviceids) {
		this.zwavedeviceids = zwavedeviceids;
	}

	public void setCameraid(int cameraid) {
		this.cameraid = cameraid;
	}

	public void setCameraids(String cameraids) {
		this.cameraids = cameraids;
	}
	public void setPhoneuser(PhoneUser phoneuser) {
		this.phoneuser = phoneuser;
	}
}
