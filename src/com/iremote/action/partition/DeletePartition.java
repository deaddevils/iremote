package com.iremote.action.partition;

import java.util.Date;
import java.util.List;
import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilege;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeType;
import com.iremote.dataprivilege.interceptorchecker.DataPrivileges;
import com.iremote.domain.Camera;
import com.iremote.domain.Partition;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.ZWaveDevice;
import com.iremote.service.CameraService;
import com.iremote.service.DoorlockAssociationService;
import com.iremote.service.PartitionService;
import com.iremote.service.ZWaveDeviceService;
import com.opensymphony.xwork2.Action;

@DataPrivileges( dataprivilege = {
		@DataPrivilege(dataprivilgetype = DataPrivilegeType.MODIFY, domain = "zwavedevice", parameter = "zwavedeviceid"),
		@DataPrivilege(dataprivilgetype = DataPrivilegeType.MODIFY, domain = "camera", parameter = "cameraid"),
		@DataPrivilege(dataprivilgetype = DataPrivilegeType.MODIFY, domain = "partition", parameter = "partitionid")
})
public class DeletePartition {
    protected int resultCode = ErrorCodeDefine.SUCCESS;
    protected int partitionid;
    protected PhoneUser phoneuser;

    public String execute() {
        if (partitionid <= 0) {
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
    	CameraService cs = new CameraService();
    	ZWaveDeviceService zds = new ZWaveDeviceService();
    	
    	List<ZWaveDevice> zwavelist = zds.querybypartitionid(partitionid);
    	for(ZWaveDevice z:zwavelist){
    		if(z!=null && IRemoteConstantDefine.DEVICE_TYPE_DSC.equals(z.getDevicetype())){
	        	resultCode = ErrorCodeDefine.PARTITION_CANNOT_DELETE;
	        	return Action.SUCCESS;
	        }
    		z.setPartitionid(null);
    	}
        
        List<Camera> cameralist = cs.querybypartitionid(partitionid);
        for(Camera c : cameralist){
        	c.setPartitionid(null);
        	cs.saveOrUpdate(c);
        }
        DoorlockAssociationService das = new DoorlockAssociationService();
    	das.deletebyobjtypeandobjid(2, partitionid);
        ps.delete(partition);
        phoneuser.setLastupdatetime(new Date());
        PhoneUserHelper.sendInfoChangeMessage(phoneuser);
        return Action.SUCCESS;
    }


	public void setPartitionid(int partitionid) {
		this.partitionid = partitionid;
	}

	public int getResultCode() {
        return resultCode;
    }
	public void setPhoneuser(PhoneUser phoneuser) {
		this.phoneuser = phoneuser;
	}
}
