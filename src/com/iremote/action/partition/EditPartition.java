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
import com.iremote.service.ZWaveDeviceService;
import com.opensymphony.xwork2.Action;
import org.apache.commons.lang.StringUtils;

import java.util.Date;
import java.util.List;

@DataPrivileges( dataprivilege = {
		@DataPrivilege(dataprivilgetype = DataPrivilegeType.MODIFY, domain = "zwavedevice", parameter = "zwavedeviceid"),
		@DataPrivilege(dataprivilgetype = DataPrivilegeType.MODIFY, domain = "camera", parameter = "cameraid"),
		@DataPrivilege(dataprivilgetype = DataPrivilegeType.MODIFY, domain = "partition", parameter = "partitionid")
})
public class EditPartition {
    protected int resultCode = ErrorCodeDefine.SUCCESS;
    protected int partitionid;
    protected PhoneUser phoneuser;
    protected String name;
    protected int delay;
    protected String password;
    protected int zwavedeviceid;
    protected String zwavedeviceids;
    protected int cameraid;
    protected String cameraids;

    public String execute() {
        if (StringUtils.isBlank(name) || partitionid == 0 || delay < 0) {
            resultCode = ErrorCodeDefine.PARMETER_ERROR;
            return Action.SUCCESS;
        }
        PartitionService ps = new PartitionService();
        Partition partition = ps.query(partitionid);
        if (partition == null) {
            resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT;
            return Action.SUCCESS;
        }
        if(partition.getPhoneuser()!=null&&phoneuser!=null){
        	int phoneuserid = partition.getPhoneuser().getPhoneuserid();
        	if(phoneuserid!=0 && phoneuserid!=phoneuser.getPhoneuserid()){
        		resultCode = ErrorCodeDefine.NO_PRIVILEGE;
    			return Action.SUCCESS;
        	}
        }
        partition.setName(name.trim());
        partition.setDelay(delay);
        if(!StringUtils.isEmpty(password)){
        	partition.setPassword(MD5Util.MD5(String.valueOf(partitionid)+password));
        }
        ZWaveDeviceService zds = new ZWaveDeviceService();
    	CameraService cs = new CameraService();
        List<ZWaveDevice> zdlist = zds.querybypartitionid(partitionid);
        List<Camera> calist = cs.querybypartitionid(partitionid);
        List<Integer> zwavedevicelist = Utils.jsontoIntList(zwavedeviceids);
    	List<Integer> cameralist = Utils.jsontoIntList(cameraids);
        
        if(zwavedeviceid!=0){
        	ZWaveDevice zd = zds.query(zwavedeviceid);
        	if(zd!=null){
        		List<ZWaveDevice> zwavepartitionlist = zds.querybypartitionid(partitionid);
        		for(ZWaveDevice z : zwavepartitionlist){
        			if(z.getZwavedeviceid()!=zwavedeviceid){
        				z.setPartitionid(null);
        			}else{
        				z.setPartitionid(partitionid);
        			}
        		}
        		zd.setPartitionid(partitionid);
        	}
        }else{
	        if(zwavedevicelist.size()>0){
	        	for(ZWaveDevice z : zdlist){
	        		z.setPartitionid(null);
	        	}
	        	for(Integer zid : zwavedevicelist){
	        		ZWaveDevice zd = zds.query(zid);
	        		if(zd!=null){
	        			zd.setPartitionid(partitionid);
	        		}
	        	}
	        }
        }
        if(cameraid!=0){
        	Camera camera = cs.query(cameraid);
        	if(camera!=null){
        		List<Camera> camerapartitionlist = cs.querybypartitionid(partitionid);
        		for(Camera c : camerapartitionlist){
        			if(c.getCameraid()!=cameraid){
        				c.setPartitionid(null);//delete
        			}else{
        				c.setPartitionid(partitionid);//edit
        			}
        			cs.saveOrUpdate(c);
        		}
        		camera.setPartitionid(partitionid);//new
        	}
        }else{
	        if(cameralist.size()>0){
	        	for(Camera c: calist){
	        		c.setPartitionid(null);
	        		cs.saveOrUpdate(c);
	        	}
	        	for(Integer cid:cameralist){
	        		Camera ca = cs.query(cid);
	        		if(ca!=null){
	        			ca.setPartitionid(partitionid);
	        		}
	        	}
	        }
        }
        phoneuser.setLastupdatetime(new Date());
        PhoneUserHelper.sendInfoChangeMessage(phoneuser);
        ps.saveOrUpdate(partition);
        return Action.SUCCESS;
    }

    public void setPartitionid(int partitionid) {
        this.partitionid = partitionid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneuser(PhoneUser phoneuser) {
        this.phoneuser = phoneuser;
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
	
}
