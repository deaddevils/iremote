package com.iremote.dataprivilege.partition;

import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.dataprivilege.interceptorchecker.IURLDataPrivilegeChecker;
import com.iremote.domain.Partition;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.ZWaveDevice;
import com.iremote.service.PartitionService;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.Map;

public class PartitionOperationPrivilegeChecker implements IURLDataPrivilegeChecker<PhoneUser> {
    protected PhoneUser user;
    protected Integer partitionid;

    @Override
    public void setUser(PhoneUser user) {
        this.user = user;
    }

    public void setParameter(String parameter) {
        if (StringUtils.isNotBlank(parameter)) {
            partitionid = Integer.valueOf(parameter);
        }
    }

    @Override
    public void SetParameters(Map<String, String> parameters) {
    }

    @Override
    public boolean checkprivilege() {
        if (partitionid == null) {
            return true;
        }
        PartitionService ps = new PartitionService();
        Partition partition = ps.query(partitionid);
        if (partition == null) {
            return true;
        }
        
        if (partition.getZwavedevice() == null) {
            List<Integer> list = PhoneUserHelper.querybySharetoPhoneuserid(user.getPhoneuserid());
            return list.contains(partition.getPhoneuser().getPhoneuserid());
        } else {
            ZWaveDevice zd = partition.getZwavedevice();
            if (PhoneUserHelper.checkPrivilege(user, zd)) {
                return true;
            }
        }
        return false;
    }
}
