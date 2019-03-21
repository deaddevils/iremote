package com.iremote.thirdpart.wcj.action;

import com.iremote.action.gateway.DeleteGatewayOwnerAction;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilege;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeType;
import com.iremote.domain.ThirdPart;
import com.iremote.service.PhoneUserService;
import com.iremote.service.RemoteService;

@DataPrivilege(dataprivilgetype = DataPrivilegeType.MODIFY, domain = "gateway", parameter = "deviceid")
public class DeleteGatewayAction extends DeleteGatewayOwnerAction {
    private ThirdPart thirdpart;

    @Override
    protected boolean initPhoneUser() {
        RemoteService rs = new RemoteService();
        Integer phoneUserId = rs.queryOwnerId(deviceid);
        if (phoneUserId == null) {
            return false;
        }
        phoneuser = new PhoneUserService().query(phoneUserId);
        return phoneuser != null;
    }

    public void setThirdpart(ThirdPart thirdpart) {
        this.thirdpart = thirdpart;
    }
}
