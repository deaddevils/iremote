package com.iremote.action.room;

import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.action.partition.PartitionHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.Utils;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilege;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeType;
import com.iremote.dataprivilege.interceptorchecker.DataPrivileges;
import com.iremote.domain.Camera;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.ZWaveDevice;
import com.iremote.service.CameraService;
import com.iremote.service.PartitionService;
import com.iremote.service.RemoteService;
import com.iremote.service.ZWaveDeviceService;
import com.opensymphony.xwork2.Action;
import java.util.Date;
import java.util.List;

@DataPrivileges( dataprivilege = {
		@DataPrivilege(dataprivilgetype = DataPrivilegeType.MODIFY, domain = "zwavedevice", parameter = "zwavedeviceid"),
		@DataPrivilege(dataprivilgetype = DataPrivilegeType.MODIFY, domain = "camera", parameter = "cameraid"),
		@DataPrivilege(dataprivilgetype = DataPrivilegeType.MODIFY, domain = "partition", parameter = "partitionid")
})
public class DeletePartitionAppliance {
    protected int resultCode = ErrorCodeDefine.SUCCESS;
    protected int partitionid;
    protected int zwavedeviceid;
    protected String zwavedeviceids;
    protected int cameraid;
    protected String cameraids;
    protected PhoneUser phoneuser;
    
    public String execute() {
    	PartitionService partitionService = new PartitionService();
    	ZWaveDeviceService zds = new ZWaveDeviceService();
    	CameraService cs = new CameraService();
    	List<Integer> zwavedevicelist = Utils.jsontoIntList(zwavedeviceids);
    	List<Integer> cameralist = Utils.jsontoIntList(cameraids);
    	
    	RemoteService rs = new RemoteService();
		List<String> ldid = rs.queryDeviceidbyPhoneUserid(phoneuser.getPhoneuserid());		
		List<Camera> lst = cs.querybydeviceid(ldid);
		
        if (partitionService.query(partitionid) ==null) {
            resultCode = ErrorCodeDefine.PARMETER_ERROR;
            return Action.SUCCESS;
        }
        
        if(zwavedeviceid!=0){
        	if(check(zwavedeviceid,false)){
            	return Action.SUCCESS;
            }
    		ZWaveDevice query = zds.query(zwavedeviceid);
    		if(query==null){
    			resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT;
                return Action.SUCCESS;
    		}
    		if ( PhoneUserHelper.checkPrivilege(phoneuser, query) == false ){
    			resultCode = ErrorCodeDefine.NO_PRIVILEGE;
    			return Action.SUCCESS;
    		}
    		query.setPartitionid(null);
        }
        
        if(zwavedevicelist.size()>0){
        	for(int z : zwavedevicelist){
        		if(check(z,false)){
                	return Action.SUCCESS;
                }
        		ZWaveDevice query = zds.query(z);
        		if(query==null){
        			resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT;
                    return Action.SUCCESS;
        		}
        		if ( PhoneUserHelper.checkPrivilege(phoneuser, query) == false ){
        			resultCode = ErrorCodeDefine.NO_PRIVILEGE;
        			return Action.SUCCESS;
        		}
        	}
        	for(int z : zwavedevicelist){
        		ZWaveDevice query = zds.query(z);
        		query.setPartitionid(null);
        	}
        }
        if(cameraid!=0){
    		Camera camera = cs.query(cameraid);
    		if(camera==null){
    			resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT;
                return Action.SUCCESS;
    		}
    		if(!lst.contains(camera)){
				resultCode = ErrorCodeDefine.NO_PRIVILEGE;
    			return Action.SUCCESS;
			}
    		camera.setPartitionid(null);
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
        		camera.setPartitionid(null);
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
	public void setPartitionid(int partitionid) {
		this.partitionid = partitionid;
	}

	public void setZwavedeviceid(int zwavedeviceid) {
		this.zwavedeviceid = zwavedeviceid;
	}

	public void setZwavedeviceids(String zwavedeviceids) {
		this.zwavedeviceids = zwavedeviceids;
	}

	public int getResultCode() {
		return resultCode;
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
