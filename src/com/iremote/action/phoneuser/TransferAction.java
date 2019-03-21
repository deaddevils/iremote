package com.iremote.action.phoneuser;

import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.push.PushMessage;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.UserShare;
import com.iremote.service.*;
import com.opensymphony.xwork2.Action;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

public class TransferAction {
    private String countrycode;
    private String phonenumber;
    private PhoneUser phoneuser;
    private int resultCode;
    private PhoneUser toUser;

    public String execute() {
        PhoneUserService phoneUserService = new PhoneUserService();
        if (check(phoneUserService)) {
            return Action.SUCCESS;
        }
        UserShareService userShareService = new UserShareService();
        HashSet<Integer> affectedPhoneUserIds = new HashSet<>();

        affectedPhoneUserIds.add(phoneuser.getPhoneuserid());
        affectedPhoneUserIds.add(toUser.getPhoneuserid());

        addFamilyAndFriendsPhoneUserId(affectedPhoneUserIds, phoneuser, phoneUserService, userShareService);
        addFamilyAndFriendsPhoneUserId(affectedPhoneUserIds, toUser, phoneUserService, userShareService);

        deleteDeviceLevelShare(affectedPhoneUserIds, userShareService);
        transferDeviceGroupOwner();
        transferRoomOwner();
        transferPartitionOwner();
        transferGatewayOwner();
        transferSceneOwner();

        phoneuser.setLastupdatetime(new Date());
        toUser.setLastupdatetime(new Date());
        List<String> alias = phoneUserService.queryAlias(affectedPhoneUserIds);
        PushMessage.pushInfoChangedMessage(alias.toArray(new String[alias.size()]), phoneuser.getPlatform());
        return Action.SUCCESS;
    }

    private void transferGatewayOwner() {
        RemoteService service = new RemoteService();
        service.changeOwner(toUser.getPhoneuserid(), toUser.getPhonenumber(), phoneuser.getPhoneuserid());
    }

    private void addFamilyAndFriendsPhoneUserId(HashSet<Integer> affectedPhoneUserIds, PhoneUser phoneUser,
             PhoneUserService phoneUserService, UserShareService userShareService) {
        if (phoneUser.getFamilyid() != null && phoneUser.getFamilyid() != 0) {
            affectedPhoneUserIds.addAll(phoneUserService.queryldListByFamilyId(phoneUser.getFamilyid()));
        }
        affectedPhoneUserIds.addAll(userShareService.queryToUserId(phoneUser.getPhoneuserid(),
                IRemoteConstantDefine.USER_SHARE_TYPE_NORMAL, IRemoteConstantDefine.USER_SHARE_DEVICE_TYPE_ALL));
    }

    private void transferPartitionOwner() {
        PartitionService service = new PartitionService();
        service.changeOwner(toUser.getPhoneuserid(), phoneuser.getPhoneuserid());
    }

    private void transferRoomOwner() {
        RoomService service = new RoomService();
        service.changeOwner(toUser.getPhoneuserid(), toUser.getPhonenumber(), phoneuser.getPhoneuserid());
    }

    private void transferDeviceGroupOwner() {
        DeviceGroupService service = new DeviceGroupService();
        service.changeOwner(toUser.getPhoneuserid(), phoneuser.getPhoneuserid());
    }

    private void deleteDeviceLevelShare(HashSet<Integer> affectedPhoneUserIds, UserShareService userShareService) {
        List<UserShare> userShares = userShareService.queryDeviceLevelShare(phoneuser.getPhoneuserid());
        if (userShares == null) {
            return;
        }
        for (UserShare userShare : userShares) {
            affectedPhoneUserIds.add(userShare.getTouserid());
            if (userShare.getUserShareDevices() == null) {
                continue;
            }
            userShareService.delete(userShare);
        }
    }

    private void transferSceneOwner() {
        SceneService sceneService = new SceneService();
        sceneService.changeOwner(toUser.getPhoneuserid(), phoneuser.getPhoneuserid());
    }

    private boolean check(PhoneUserService service) {
        if (StringUtils.isBlank(countrycode) || StringUtils.isBlank(phonenumber)) {
            resultCode = ErrorCodeDefine.PARMETER_ERROR;
            return true;
        }
        toUser = service.query(countrycode, phonenumber, phoneuser.getPlatform());
        if (toUser == null) {
            resultCode = ErrorCodeDefine.USER_HAS_NOT_REGISTED_2;
            return true;
        }
        if (toUser.getPhoneuserid() == phoneuser.getPhoneuserid()) {
            return true;
        }
        return false;
    }

    public void setPhoneuser(PhoneUser phoneuser) {
        this.phoneuser = phoneuser;
    }

    public void setCountrycode(String countrycode) {
        this.countrycode = countrycode;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public int getResultCode() {
        return resultCode;
    }
}
