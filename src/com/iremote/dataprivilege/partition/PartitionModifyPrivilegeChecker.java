package com.iremote.dataprivilege.partition;

import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.dataprivilege.interceptorchecker.IURLDataPrivilegeChecker;
import com.iremote.domain.Partition;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.ZWaveDevice;
import com.iremote.service.PartitionService;
import org.apache.commons.lang.StringUtils;

import java.util.Map;

public class PartitionModifyPrivilegeChecker implements IURLDataPrivilegeChecker<PhoneUser> {
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
        if (partitionid == null){
            return true;
        }

        PartitionService ps = new PartitionService();
        Partition p = ps.query(partitionid);
        if (p == null ||p.getZwavedevice()==null) {
            return true;
        }
        ZWaveDevice zd = p.getZwavedevice();
        return zd != null && PhoneUserHelper.checkModifyPrivilege(user, zd.getDeviceid());
    }
}
