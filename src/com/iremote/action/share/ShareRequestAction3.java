package com.iremote.action.share;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.ServerRuntime;
import com.iremote.common.message.MessageManager;
import com.iremote.common.message.MessageParser;
import com.iremote.common.push.PushMessage;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilege;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeType;
import com.iremote.domain.*;
import com.iremote.service.CommunityAdministratorService;
import com.iremote.service.PhoneUserService;
import com.iremote.service.UserShareService;
import com.opensymphony.xwork2.Action;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.*;

@DataPrivilege(dataprivilgetype = DataPrivilegeType.MODIFY, domain = "sharedevice", parameters = {"sharedevice", "sharedevicetype"})
public class ShareRequestAction3 {
    private int resultCode = ErrorCodeDefine.SUCCESS;
    private String countrycode = IRemoteConstantDefine.DEFAULT_COUNTRYCODE;
    private String phonenumber;

    private PhoneUser touser;
    private PhoneUser phoneuser;
    private int shareid;
    private int type;

    private int sharedevicetype;
    private String sharedevice;

    private UserShareService svr = new UserShareService();

    public String execute() {
        PhoneUserService pus = new PhoneUserService();
        touser = pus.query(countrycode, phonenumber, phoneuser.getPlatform());

        if (touser == null) {
            resultCode = ErrorCodeDefine.USER_HAS_NOT_REGISTED_2;
            return Action.SUCCESS;
        }

        if (touser.getStatus() != null && IRemoteConstantDefine.USER_STATUS_ENABLED != touser.getStatus()) {
            resultCode = ErrorCodeDefine.USER_HAS_NOT_REGISTED_2;
            return Action.SUCCESS;
        }

        if (checkThirdpartPrivilege() == false) {
            resultCode = ErrorCodeDefine.NO_PRIVILEGE;
            return Action.SUCCESS;
        }

        if (type == IRemoteConstantDefine.USER_SHARE_TYPE_FAMILY) {
            shareDeviceForFamily();
        } else if (type == IRemoteConstantDefine.USER_SHARE_TYPE_NORMAL) {
            if (sharedevicetype == IRemoteConstantDefine.USER_SHARE_DEVICE_TYPE_ALL) {
                shareAllDeviceForFriend();
            } else {
                shareSpecifyDeviceForFriend();
            }
        }
        return Action.SUCCESS;
    }

    private void shareSpecifyDeviceForFriend() {
        if (hasSharedBetweenTheir(IRemoteConstantDefine.USER_SHARE_TYPE_FAMILY, false)) {
            resultCode = ErrorCodeDefine.ALREADY_HAS_HIGHER_LEVER_SHARING;
            return;
        }
        if (hasSharedBetweenTheir(IRemoteConstantDefine.USER_SHARE_TYPE_NORMAL, IRemoteConstantDefine.USER_SHARE_DEVICE_TYPE_ALL, false)) {
            resultCode = ErrorCodeDefine.ALREADY_HAS_HIGHER_LEVER_SHARING;
            return;
        }

        JSONArray jsonArray = JSONArray.parseArray(sharedevice);
        ArrayList<Integer> zwavedeviceidList0 = new ArrayList<>();
        ArrayList<Integer> infrareddeviceList0 = new ArrayList<>();
        ArrayList<Integer> cameraList0 = new ArrayList<>();

        getIdsFromShareDevice(jsonArray, zwavedeviceidList0, infrareddeviceList0, cameraList0);

        UserShare sameSharing = getSameSharing(zwavedeviceidList0, infrareddeviceList0, cameraList0);
        if (sameSharing != null) {
            if (sameSharing.getStatus() == IRemoteConstantDefine.USER_SHARE_STATUS_WAIT_FOR_RESPONSE) {
                sendSharingRequestMessage();
            }
            return;
        }

        List<UserShare> userShares = svr.queryWhichNotResponse(phoneuser.getPhoneuserid(), touser.getPhoneuserid(),
                IRemoteConstantDefine.USER_SHARE_TYPE_NORMAL, IRemoteConstantDefine.USER_SHARE_DEVICE_TYPE_SPECIFY);
        if (userShares != null && userShares.size() != 0) {
            addToExistSharing(zwavedeviceidList0, infrareddeviceList0, cameraList0, userShares);
        } else {
            addSpecifySharing(zwavedeviceidList0, infrareddeviceList0, cameraList0);
        }
        sendSharingRequestMessage();
    }

    private void getIdsFromShareDevice(JSONArray jsonArray, ArrayList<Integer> zwavedeviceidList0, ArrayList<Integer> infrareddeviceList0, ArrayList<Integer> cameraList0) {
        for (int i = 0; jsonArray != null && i < jsonArray.size(); i++) {
            JSONObject json = jsonArray.getJSONObject(i);
            Integer zwavedeivceid = json.getInteger("zwavedeviceid");
            Integer infrareddeviceid = json.getInteger("infrareddeviceid");
            Integer cameraid = json.getInteger("cameraid");
            if (zwavedeivceid != null && zwavedeivceid != 0) {
                zwavedeviceidList0.add(zwavedeivceid);
            } else if (infrareddeviceid != null && infrareddeviceid != 0) {
                infrareddeviceList0.add(infrareddeviceid);
            } else if (cameraid != null && cameraid != 0) {
                cameraList0.add(cameraid);
            }
        }
    }

    private void addToExistSharing(ArrayList<Integer> zwavedeviceidList0, ArrayList<Integer> infrareddeviceList0, ArrayList<Integer> cameraList0, List<UserShare> userShares) {
        List<UserShareDevice> userShareDevices = userShares.get(0).getUserShareDevices();

        ArrayList<Integer> zwavedeviceidList = new ArrayList<>(Arrays.asList(new Integer[zwavedeviceidList0.size()]));
        ArrayList<Integer> infrareddeviceList = new ArrayList<>(Arrays.asList(new Integer[infrareddeviceList0.size()]));
        ArrayList<Integer> cameraList = new ArrayList<>(Arrays.asList(new Integer[cameraList0.size()]));

        Collections.copy(zwavedeviceidList, zwavedeviceidList0);
        Collections.copy(infrareddeviceList, infrareddeviceList0);
        Collections.copy(cameraList, cameraList0);

        for (int i = 0; userShareDevices != null && i < userShareDevices.size(); i++) {
            UserShareDevice userShareDevice = userShareDevices.get(i);
            if (userShareDevice.getZwavedeviceid() != null && zwavedeviceidList.contains(userShareDevice.getZwavedeviceid())) {
                zwavedeviceidList.remove(userShareDevice.getZwavedeviceid());
            } else if (userShareDevice.getInfrareddeviceid() != null && infrareddeviceList.contains(userShareDevice.getInfrareddeviceid())) {
                infrareddeviceList.remove(userShareDevice.getInfrareddeviceid());
            } else if (userShareDevice.getCameraid() != null && cameraList.contains(userShareDevice.getCameraid())) {
                cameraList.remove(userShareDevice.getCameraid());
            }
        }

        addZwavedeviceForExistSpecifySharing(zwavedeviceidList, userShareDevices, userShares.get(0));
        addInfrareddeviceForExistSpecifySharing(infrareddeviceList, userShareDevices, userShares.get(0));
        addCameraForExistSpecifySharing(cameraList, userShareDevices, userShares.get(0));
    }

    private UserShare getSameSharing(ArrayList<Integer> zwavedeviceidList0, ArrayList<Integer> infrareddeviceList0, ArrayList<Integer> cameraList0) {

        ArrayList<Integer> zwavedeviceidList = new ArrayList<>(Arrays.asList(new Integer[zwavedeviceidList0.size()]));
        ArrayList<Integer> infrareddeviceList = new ArrayList<>(Arrays.asList(new Integer[infrareddeviceList0.size()]));
        ArrayList<Integer> cameraList = new ArrayList<>(Arrays.asList(new Integer[cameraList0.size()]));
        Collections.copy(zwavedeviceidList, zwavedeviceidList0);
        Collections.copy(infrareddeviceList, infrareddeviceList0);
        Collections.copy(cameraList, cameraList0);

        List<UserShare> userShareList = svr.query(phoneuser.getPhoneuserid(), touser.getPhoneuserid());
        for (int i = 0; userShareList != null && i < userShareList.size(); i++) {
            List<UserShareDevice> usdList = userShareList.get(i).getUserShareDevices();
            if (usdList.size() != (zwavedeviceidList.size() + infrareddeviceList.size() + cameraList.size())) {
                return null;
            }

            UserShare userShare = null;
            for (int j = 0; usdList != null && j < usdList.size(); j++) {
                UserShareDevice usd = usdList.get(j);
                userShare = usd.getUserShare();

                if (usd.getZwavedeviceid() != null) {
                    if (!zwavedeviceidList.contains(usd.getZwavedeviceid())) {
                        break;
                    }
                    zwavedeviceidList.remove(usd.getZwavedeviceid());
                } else if (usd.getInfrareddeviceid() != null) {
                    if (!infrareddeviceList.contains(usd.getZwavedeviceid())) {
                        break;
                    }
                    infrareddeviceList.remove(usd.getInfrareddeviceid());
                } else if (usd.getCameraid() != null) {
                    if (!cameraList.contains(usd.getZwavedeviceid())) {
                        break;
                    }
                    cameraList.remove(usd.getCameraid());
                }
            }

            if ((zwavedeviceidList.size() + infrareddeviceList.size() + cameraList.size()) == 0) {
                return userShare;
            }
        }
        return null;
    }

    private void shareAllDeviceForFriend() {
        if (hasSharedBetweenTheir(IRemoteConstantDefine.USER_SHARE_TYPE_NORMAL, IRemoteConstantDefine.USER_SHARE_DEVICE_TYPE_ALL, true)) {
            return;
        }

        if (hasSharedBetweenTheir(IRemoteConstantDefine.USER_SHARE_TYPE_FAMILY, false)) {
            resultCode = ErrorCodeDefine.ALREADY_HAS_HIGHER_LEVER_SHARING;
            return;
        }

        addSharing();
        sendSharingRequestMessage();
    }

    private boolean hasSharedAllDeivceBetweenTheir(int sharetype, int sharedevicetype) {
        List<UserShare> userShares = svr.queryShared(phoneuser.getPhoneuserid(),
                touser.getPhoneuserid(), sharetype, sharedevicetype);
        if (userShares != null && userShares.size() != 0) {
            return true;
        }

        List<UserShare> userShares1 = svr.queryShared(touser.getPhoneuserid(),
                phoneuser.getPhoneuserid(), sharetype, sharedevicetype);
        if (userShares1 != null && userShares1.size() != 0) {
            return true;
        }

        return false;
    }

    private void shareDeviceForFamily() {
        if (hasSharedBetweenTheir(IRemoteConstantDefine.USER_SHARE_TYPE_FAMILY, true)) {
            return;
        }

        if (hasReceivedSharingFromOthers(phoneuser.getPhoneuserid())) {
            resultCode = ErrorCodeDefine.USER_HAD_RECEIVED_FAMILI_SHARING;
            return;
        }

        if (hasReceivedSharingFromOthers(touser.getPhoneuserid())) {
            resultCode = ErrorCodeDefine.OTHER_HAD_RECEIVED_FAMILI_SHARING;
            return;
        }

        addSharing();
        sendSharingRequestMessage();
    }

    private void addSharing() {
        UserShare us = new UserShare();
        us.setShareuserid(phoneuser.getPhoneuserid());
        us.setShareuser(phoneuser.getPhonenumber());
        us.setTouserid(touser.getPhoneuserid());
        us.setTouser(touser.getPhonenumber());
        us.setTousercountrycode(touser.getCountrycode());
        us.setStatus(IRemoteConstantDefine.USER_SHARE_STATUS_WAIT_FOR_RESPONSE);
        us.setSharetype(this.type);
        us.setCreatetime(new Date());
        us.setSharedevicetype(sharedevicetype);

        shareid = svr.save(us);
    }

    private void addSpecifySharing(ArrayList<Integer> zwavedeviceidList, ArrayList<Integer> infraredeviceidList, ArrayList<Integer> cameraidList) {
        UserShare us = new UserShare();
        us.setShareuserid(phoneuser.getPhoneuserid());
        us.setShareuser(phoneuser.getPhonenumber());
        us.setTouserid(touser.getPhoneuserid());
        us.setTouser(touser.getPhonenumber());
        us.setTousercountrycode(touser.getCountrycode());
        us.setStatus(IRemoteConstantDefine.USER_SHARE_STATUS_WAIT_FOR_RESPONSE);
        us.setSharetype(this.type);
        us.setCreatetime(new Date());
        us.setSharedevicetype(sharedevicetype);

        if (type == IRemoteConstantDefine.USER_SHARE_TYPE_NORMAL && sharedevicetype == IRemoteConstantDefine.USER_SHARE_DEVICE_TYPE_SPECIFY) {
            us.setUserShareDevices(saveUserShareDevice(zwavedeviceidList, infraredeviceidList, cameraidList, us));
        }

        shareid = svr.save(us);
    }

    private List<UserShareDevice> saveUserShareDevice(ArrayList<Integer> zwavedeivceidList, ArrayList<Integer> infraredeviceidList, ArrayList<Integer> cameraidList, UserShare us) {
        ArrayList<UserShareDevice> userShareDeviceArrayList = new ArrayList<>();
        for (Integer zwavedeviceid : zwavedeivceidList) {
            UserShareDevice usd = new UserShareDevice();
            usd.setZwavedeviceid(zwavedeviceid);
            usd.setUserShare(us);
            userShareDeviceArrayList.add(usd);
        }

        for (Integer infrareddeviceid : infraredeviceidList) {
            UserShareDevice usd = new UserShareDevice();
            usd.setInfrareddeviceid(infrareddeviceid);
            usd.setUserShare(us);
            userShareDeviceArrayList.add(usd);
        }

        for (Integer cameraid : cameraidList) {
            UserShareDevice usd = new UserShareDevice();
            usd.setCameraid(cameraid);
            usd.setUserShare(us);
            userShareDeviceArrayList.add(usd);
        }

        return userShareDeviceArrayList;
    }

    private void addZwavedeviceForExistSpecifySharing(ArrayList<Integer> deviceidList, List<UserShareDevice> userShareDevices, UserShare userShare) {
        for (Integer zwavedeviceid : deviceidList) {
            UserShareDevice usd = new UserShareDevice();
            usd.setZwavedeviceid(zwavedeviceid);
            usd.setUserShare(userShare);
            userShareDevices.add(usd);
        }
    }

    private void addInfrareddeviceForExistSpecifySharing(ArrayList<Integer> deviceidList, List<UserShareDevice> userShareDevices, UserShare userShare) {
        for (Integer id : deviceidList) {
            UserShareDevice usd = new UserShareDevice();
            usd.setInfrareddeviceid(id);
            usd.setUserShare(userShare);
            userShareDevices.add(usd);
        }
    }

    private void addCameraForExistSpecifySharing(ArrayList<Integer> deviceidList, List<UserShareDevice> userShareDevices, UserShare userShare) {
        for (Integer id : deviceidList) {
            UserShareDevice usd = new UserShareDevice();
            usd.setCameraid(id);
            usd.setUserShare(userShare);
            userShareDevices.add(usd);
        }
    }


    private boolean hasReceivedSharingFromOthers(int phoneuserid) {
        List<UserShare> userShares = svr.querybytoUserForFalimySharing(phoneuserid);
        if (userShares != null && userShares.size() != 0) {
            return true;
        }
        return false;
    }

    private boolean hasSharedBetweenTheir(int sharetype, int sharedevicetype, boolean chechResponse) {
        boolean hasShared = false;
        List<UserShare> userShares = svr.queryShared(phoneuser.getPhoneuserid(), touser.getPhoneuserid(), sharetype, sharedevicetype);
        if (userShares != null && userShares.size() != 0) {
            hasShared = true;
            if (chechResponse) {
                if (checkSharingResponse(userShares)) {
                    return hasShared;
                }
            }
        }
        return hasShared;
    }

    private boolean hasSharedBetweenTheir(int sharetype, boolean checkResponse) {
        boolean hasShared = false;

        List<UserShare> userShares = svr.queryShared(phoneuser.getPhoneuserid(), touser.getPhoneuserid(), sharetype);
        if (userShares != null && userShares.size() != 0) {
            hasShared = true;
            if (checkResponse) {
                if (checkSharingResponse(userShares)) {
                    return hasShared;
                }
            }
        }

        List<UserShare> userShares1 = svr.queryShared(touser.getPhoneuserid(), phoneuser.getPhoneuserid(), sharetype);
        if (userShares1 != null && userShares1.size() != 0) {
            hasShared = true;
            if (checkResponse) {
                if (checkSharingResponse(userShares1)) {
                    return hasShared;
                }
            }
        }

        return hasShared;
    }

    private boolean checkSharingResponse(List<UserShare> userShares) {
        for (int i = 0; userShares != null &&  i < userShares.size(); i++) {
            if (userShares.get(i).getStatus() == IRemoteConstantDefine.USER_SHARE_STATUS_WAIT_FOR_RESPONSE) {
                sendSharingRequestMessage();
                return true;
            }
        }
        return false;
    }

    private void sendSharingRequestMessage() {
        MessageParser mp = MessageManager.getMessageParser(IRemoteConstantDefine.NOTIFICATION_SHARE_REQUEST, phoneuser.getPlatform(), touser.getLanguage());
        mp.getParameter().put("phonenumber", phoneuser.getPhonenumber());
        String message = mp.getMessage();

        int r = PushMessage.pushShareRequestMessage(touser.getAlias(), phoneuser.getPlatform(), message);
        if (r != 0)
            resultCode = ErrorCodeDefine.PUSH_MESSAGE_SEND_FAILED;
    }

    private boolean checkThirdpartPrivilege() {
        CommunityAdministratorService cas = new CommunityAdministratorService();
        CommunityAdministrator ca1 = cas.querybyphoneuserid(phoneuser.getPhoneuserid());
        CommunityAdministrator ca2 = cas.querybyphoneuserid(touser.getPhoneuserid());

        if (ca1 == null && ca2 == null) {
            return true;
        }
        if (ca1 == null || ca2 == null) {
            return false;
        }
        return (ca1.getThirdpartid() == ca2.getThirdpartid() && ca1.getCommunityid() == ca2.getCommunityid());
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public int getShareid() {
        return shareid;
    }

    public void setCountrycode(String countrycode) {
        this.countrycode = countrycode;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setSharedevicetype(int sharedevicetype) {
        this.sharedevicetype = sharedevicetype;
    }

    public void setSharedevice(String sharedevice) {
        this.sharedevice = sharedevice;
    }

    public void setPhoneuser(PhoneUser phoneuser) {
        this.phoneuser = phoneuser;
    }


}
