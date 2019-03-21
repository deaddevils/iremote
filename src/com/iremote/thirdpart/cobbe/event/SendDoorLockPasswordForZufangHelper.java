package com.iremote.thirdpart.cobbe.event;

import com.iremote.action.helper.DoorlockHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.Hibernate.HibernateUtil;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.encrypt.AES;
import com.iremote.common.jms.JMSUtil;
import com.iremote.common.jms.vo.RemoteEvent;
import com.iremote.domain.Remote;
import com.iremote.domain.ZWaveDevice;
import com.iremote.infraredtrans.ConnectionManager;
import com.iremote.service.RemoteService;
import com.iremote.service.ZWaveDeviceService;
import com.iremote.thirdpart.common.ThirdPartHelper;
import com.iremote.thirdpart.wcj.action.*;
import com.iremote.thirdpart.wcj.domain.DoorlockPassword;
import com.iremote.thirdpart.wcj.service.DoorlockPasswordService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;

public class SendDoorLockPasswordForZufangHelper implements Runnable {
    private String deviceid;
    private Date eventtime;
    private long taskid;
    private boolean sendForZwaveOnly = false;
    private DoorlockPasswordService dps;
    private InnerTempPasswordUtils tempPasswordUtils;
    private static int TYPE_SET_PASSWORD = 1;
    private static int TYPE_SET_CARD_PASSWORD = 2;
    private static int TYPE_SET_FINGER_PASSWORD = 3;
    private static int TEMP_PASSWORD_USER_CODE = 242;
    public static final String TEMP_PASSWORD_VALID_FROM_DATE = "2050-01-01 00:00:00";
    public static final String TEMP_PASSWORD_VALID_THROUGH_DATE = "2050-01-01 00:00:01";
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static Log log = LogFactory.getLog(SendDoorLockPasswordForZufangHelper.class);

    public SendDoorLockPasswordForZufangHelper(String deviceid, Date eventtime, long taskid) {
        this.deviceid = deviceid;
        this.eventtime = eventtime;
        this.taskid = taskid;
    }

    public SendDoorLockPasswordForZufangHelper(boolean sendForZwaveOnly) {
        this.eventtime = new Date();
        this.taskid = System.currentTimeMillis();
        this.sendForZwaveOnly = sendForZwaveOnly;
    }

    @Override
    public void run() {
        if (deviceid == null) {
        	this.sendSleepCommand();
            return;
        }

        RemoteService rs = new RemoteService();
        Remote remote = rs.getIremotepassword(deviceid);
        Integer oid = remote.getPhoneuserid();
        if (oid == null || oid == 0) {
            this.sendSleepCommand();
            return;
        }

        ZWaveDeviceService zds = new ZWaveDeviceService();
        List<ZWaveDevice> zWaveDevices = zds.queryByDevicetypeList(deviceid, IRemoteConstantDefine.DEVICE_TYPE_LIST_LIKE_DOOR_LOCK);

        if (zWaveDevices == null || zWaveDevices.size() == 0) {
        	 this.sendSleepCommand();
            return;
        }

         dps = new DoorlockPasswordService();
        for (ZWaveDevice zWaveDevice : zWaveDevices) {
            List<Integer> doorlockPasswordsIdList = dps.listUnsynchronizedPasswordId(zWaveDevice.getZwavedeviceid());
            HibernateUtil.commit();

//            if (false) {
//                sendPasswordPacket(zWaveDevice, doorlockPasswordsIdList);
//            } else {
                sendPasswordSingly(zWaveDevice, doorlockPasswordsIdList);
//            }
        }
        this.sendSleepCommand();
    }

    public void sendPasswordForZwaveDoorLock(List<Integer> doorlockPasswordIdList ){
        sendPasswordSingly(null, doorlockPasswordIdList);
    }

    private void sendPasswordPacket(ZWaveDevice zWaveDevice, List<DoorlockPassword> doorlockPasswords) {
        ListIterator<DoorlockPassword> passwordListIterator = doorlockPasswords.listIterator();
        HashSet<DoorlockPassword> passwordHashSet = new HashSet<>();
        while (passwordListIterator.hasNext()) {
            while (passwordListIterator.hasNext() && passwordHashSet.size() <= 100) {
                passwordHashSet.add(passwordListIterator.next());
            }
//            sendAll();
        }
    }

    private void  sendPasswordSingly(ZWaveDevice zWaveDevice, List<Integer> doorlockPasswordsIdList) {
        ListIterator<Integer> idListIterator = doorlockPasswordsIdList.listIterator();
        int errorCount = 0;

        if (sendForZwaveOnly && dps == null) {
            dps = new DoorlockPasswordService();
        }

        while (idListIterator.hasNext()) {
            Integer doorlockPasswordId = idListIterator.next();
            HibernateUtil.beginTransaction();
            DoorlockPassword doorlockPassword = dps.getForUpdate(doorlockPasswordId);
            if (doorlockPassword == null) {
                continue;
            }
            
            tempPasswordUtils = new InnerTempPasswordUtils(doorlockPassword);

            zWaveDevice = getZwavedeviceInfo(zWaveDevice, doorlockPassword);
            if (zWaveDevice == null || deviceid == null){
                return;
            }
            if (sendForZwaveOnly && !ConnectionManager.isOnline(zWaveDevice.getDeviceid())) {
                log.info("gateway offline");
            }

            try {
                if (doorlockPassword.getStatus() == IRemoteConstantDefine.DOOR_LOCK_PASSWORD_STATUS_ACTIVE) {
                    errorCount = sendAddPasswordOrDate(zWaveDevice, errorCount, doorlockPassword);
                }else if (doorlockPassword.getStatus() == IRemoteConstantDefine.DOOR_LOCK_PASSWORD_STATUS_DELETE){
                    errorCount = sendDeletePassword(errorCount, doorlockPassword);
                }
                doorlockPassword.setSendtime(new Date());
            } catch (Exception e) {
                HibernateUtil.rollback();
                log.error(e.getMessage() , e);
                continue;
            }

            HibernateUtil.commit();

            if (errorCount >= 3) {
                log.info("Send  password to door lock failed three times consecutively");
                break;
            }
        }
        HibernateUtil.beginTransaction();
    }

    private ZWaveDevice getZwavedeviceInfo(ZWaveDevice zd, DoorlockPassword doorlockPassword) {
        if (sendForZwaveOnly) {
            ZWaveDevice zWaveDevice = new ZWaveDeviceService().query(doorlockPassword.getZwavedeviceid());
            if (zWaveDevice != null) {
                deviceid = zWaveDevice.getDeviceid();
            }
            return zWaveDevice;
        } else {
            return zd;
        }
    }

    private int sendDeletePassword(int errorCount, DoorlockPassword doorlockPassword) {
        boolean isSuccess = true;
        if (doorlockPassword.getUsertype() == IRemoteConstantDefine.PASSWORD_USER_TYPE_CARD_USER) {
            isSuccess = deleteCardpassword(doorlockPassword);

        } else if (doorlockPassword.getUsertype() == IRemoteConstantDefine.PASSWORD_USER_TYPE_PASSWORD_USER) {
            isSuccess = deletePassword(doorlockPassword);

        } else if (doorlockPassword.getUsertype() == IRemoteConstantDefine.PASSWORD_USER_TYPE_FINGERPRINT_USER) {
            isSuccess = deleteFingerPrintPassword(doorlockPassword);
        }

        if (!isSuccess) {
            errorCount++;
            doorlockPassword.setErrorcount(doorlockPassword.getErrorcount() + 1);

            processErrorCount(doorlockPassword, IRemoteConstantDefine.DOOR_LOCK_PASSWORD_SYN_STATUS_FAILED,
                    IRemoteConstantDefine.DOOR_LOC_SYN_DELETE_FAILED, false);
        } else {
            errorCount = 0;
            doorlockPassword.setSynstatus(IRemoteConstantDefine.DOOR_LOCK_PASSWORD_SYN_STATUS_SENT);
            doorlockPassword.setSendtime(new Date());

            ThirdPartHelper.sendDeleteThirdpartMessage(IRemoteConstantDefine.DOOR_LOCK_SYN_DELETE_SUCCESS, doorlockPassword.getZwavedeviceid(),
                    doorlockPassword.getUsercode(), deviceid, doorlockPassword.getTid(), eventtime);
        }
        return errorCount;
    }

    private int sendAddPasswordOrDate(ZWaveDevice zWaveDevice, int errorCount, DoorlockPassword doorlockPassword) {
        if (doorlockPassword.getSynstatus() == IRemoteConstantDefine.DOOR_LOCK_PASSWORD_SYN_STATUS_WAITING) {
            if (doorlockPassword.getUsertype() == IRemoteConstantDefine.PASSWORD_USER_TYPE_CARD_USER) {
                errorCount = processSendPassword(errorCount, doorlockPassword, TYPE_SET_CARD_PASSWORD);

            } else if (doorlockPassword.getUsertype() == IRemoteConstantDefine.PASSWORD_USER_TYPE_PASSWORD_USER) {
                errorCount = processSendPassword(errorCount, doorlockPassword, TYPE_SET_PASSWORD);

            } else if (doorlockPassword.getUsertype() == IRemoteConstantDefine.PASSWORD_USER_TYPE_FINGERPRINT_USER) {
                errorCount = processSendPassword(errorCount, doorlockPassword, TYPE_SET_FINGER_PASSWORD);

            }
        } else if (doorlockPassword.getSynstatus() == IRemoteConstantDefine.DOOR_LOCK_PASSWORD_SYN_STATUS_VALIDTIME_RESET) {
            errorCount = processSendTimeConfigure(zWaveDevice, errorCount, doorlockPassword);

        }
        return errorCount;
    }

    private boolean deleteFingerPrintPassword(DoorlockPassword doorlockPassword) {
        DeleteFingerprint deleteFingerprint = new DeleteFingerprint();
        deleteFingerprint.setZwavedeviceid(doorlockPassword.getZwavedeviceid());
        deleteFingerprint.setAsynch(IRemoteConstantDefine.SEND_PASSWORD_TYPE_SYNCH);
        deleteFingerprint.setUsercode(doorlockPassword.getUsercode());
        deleteFingerprint.setTid(doorlockPassword.getTid());

        deleteFingerprint.execute();

        log.info("send delete fingerprint password result:" + deleteFingerprint.getResultCode());
        return deleteFingerprint.getResultCode() == ErrorCodeDefine.SUCCESS;
    }

    private boolean deletePassword(DoorlockPassword doorlockPassword) {
        DeletePasswordAction deletePasswordAction = new DeletePasswordAction();
        deletePasswordAction.setZwavedeviceid(doorlockPassword.getZwavedeviceid());
        deletePasswordAction.setAsynch(IRemoteConstantDefine.SEND_PASSWORD_TYPE_SYNCH);
        deletePasswordAction.setUsercode(doorlockPassword.getUsercode());
        deletePasswordAction.setTid(doorlockPassword.getTid());

        deletePasswordAction.execute();

        log.info("send delete password result:" + deletePasswordAction.getResultCode());
        return deletePasswordAction.getResultCode() == ErrorCodeDefine.SUCCESS;
    }

    private boolean deleteCardpassword(DoorlockPassword doorlockPassword) {
        DeleteCardAction deleteCardAction = new DeleteCardAction();

        deleteCardAction.setUsercode(doorlockPassword.getUsercode());
        deleteCardAction.setTid(doorlockPassword.getTid());
        deleteCardAction.setZwavedeviceid(doorlockPassword.getZwavedeviceid());
        deleteCardAction.setAsynch(IRemoteConstantDefine.SEND_PASSWORD_TYPE_SYNCH);

        deleteCardAction.execute();

        log.info("send delete card password result:" + deleteCardAction.getResultCode());
        return deleteCardAction.getResultCode() == ErrorCodeDefine.SUCCESS;
    }

    private int processSendPassword(int errorcount, DoorlockPassword doorlockPassword, int type) {
        int resultCode = 0;

        if (type == TYPE_SET_PASSWORD) {
            resultCode = sendPassword(doorlockPassword);
        } else if (type == TYPE_SET_CARD_PASSWORD) {
            resultCode = sendCardPassword(doorlockPassword);
        } else if (type == TYPE_SET_FINGER_PASSWORD) {
            resultCode = sendFingerPrintPassword(doorlockPassword);
        }

        if (resultCode == ErrorCodeDefine.DOORLOCK_PASSWORDSUCCESS_VALIDTIMEFAILED) {
            doorlockPassword.setSynstatus(IRemoteConstantDefine.DOOR_LOCK_PASSWORD_SYN_STATUS_VALIDTIME_RESET);
            doorlockPassword.setErrorcount(doorlockPassword.getErrorcount() + 1);
            return ++errorcount;
        }

        if (resultCode != ErrorCodeDefine.SUCCESS) {
            doorlockPassword.setErrorcount( doorlockPassword.getErrorcount() + 1);
            errorcount ++;

            processErrorCount(doorlockPassword, IRemoteConstantDefine.DOOR_LOCK_PASSWORD_SYN_STATUS_FAILED,
                    IRemoteConstantDefine.DOOR_LOCK_SYN_FAIED, true);
        } else{
            errorcount = 0;
            doorlockPassword.setSynstatus(IRemoteConstantDefine.DOOR_LOCK_PASSWORD_SYN_STATUS_SENT);
            doorlockPassword.setSendtime(new Date());

            if (tempPasswordUtils.isTempPassword()) {
                doorlockPassword.setStatus(IRemoteConstantDefine.DOOR_LOCK_PASSWORD_STATUS_ECLIPSED);
                if (tempPasswordUtils.isDeleted()) {
                    doorlockPassword.setStatus(IRemoteConstantDefine.DOOR_LOCK_PASSWORD_STATUS_DELETE);
                    ThirdPartHelper.sendDeleteThirdpartMessage(IRemoteConstantDefine.DOOR_LOCK_SYN_DELETE_SUCCESS, doorlockPassword.getZwavedeviceid(),
                            doorlockPassword.getUsercode(), deviceid, doorlockPassword.getTid(), eventtime);
                    return errorcount;
                }
            }

            ThirdPartHelper.sendAddThirdpartMessage(IRemoteConstantDefine.DOOR_LOCK_SYN_PASSWORD_AND_DATE_SUCCESS, doorlockPassword.getZwavedeviceid(),
                    doorlockPassword.getUsercode(), deviceid, doorlockPassword.getTid(), eventtime);
        }
        return errorcount;
    }

    private int sendPassword(DoorlockPassword doorlockPassword) {
        String validfromString = sdf.format(doorlockPassword.getValidfrom());
        String validthroughString = sdf.format(doorlockPassword.getValidthrough());
        SetPasswordBase setPasswordBase = new SetPasswordBase();

        setPasswordBase.setZwavedeviceid(doorlockPassword.getZwavedeviceid());
        setPasswordBase.setPassword(AES.decrypt2Str(doorlockPassword.getPassword()));
        setPasswordBase.setValidfrom(validfromString);
        setPasswordBase.setValidthrough(validthroughString);
        if (doorlockPassword.getWeekday() != null) {
            setPasswordBase.setWeekday(doorlockPassword.getWeekday());
        }
        setPasswordBase.setStarttime(doorlockPassword.getStarttime());
        setPasswordBase.setEndtime(doorlockPassword.getEndtime());
        setPasswordBase.setAsynch(IRemoteConstantDefine.SEND_PASSWORD_TYPE_SYNCH);
        setPasswordBase.setUsercode(doorlockPassword.getUsercode());
        setPasswordBase.setUsername(doorlockPassword.getUsername());

        setPasswordBase.execute();

        if (setPasswordBase.getResultCode() == ErrorCodeDefine.SUCCESS
                || setPasswordBase.getResultCode() == ErrorCodeDefine.DOORLOCK_PASSWORDSUCCESS_VALIDTIMEFAILED) {
            doorlockPassword.setUsercode(setPasswordBase.getUsercode());
        }

        log.info("send password result:" + setPasswordBase.getResultCode());
        return setPasswordBase.getResultCode();
    }

    private int sendFingerPrintPassword(DoorlockPassword doorlockPassword) {
        String validfromString = sdf.format(doorlockPassword.getValidfrom());
        String validthroughString = sdf.format(doorlockPassword.getValidthrough());
        SetFingerprintBase setFingerprintBase = new SetFingerprintBase();

        setFingerprintBase.setZwavedeviceid(doorlockPassword.getZwavedeviceid());
        setFingerprintBase.setFingerprint(doorlockPassword.getPassword());
        setFingerprintBase.setValidfrom(validfromString);
        setFingerprintBase.setValidthrough(validthroughString);
        setFingerprintBase.setUsercode(doorlockPassword.getUsercode());
        if (doorlockPassword.getWeekday() != null) {
            setFingerprintBase.setWeekday(doorlockPassword.getWeekday());
        }
        setFingerprintBase.setStarttime(doorlockPassword.getStarttime());
        setFingerprintBase.setEndtime(doorlockPassword.getEndtime());
        setFingerprintBase.setAsynch(IRemoteConstantDefine.SEND_PASSWORD_TYPE_SYNCH);
        setFingerprintBase.setUsername(doorlockPassword.getUsername());

        setFingerprintBase.execute();

        if (setFingerprintBase.getResultCode() == ErrorCodeDefine.SUCCESS
                || setFingerprintBase.getResultCode() == ErrorCodeDefine.DOORLOCK_PASSWORDSUCCESS_VALIDTIMEFAILED) {
            doorlockPassword.setUsercode(setFingerprintBase.getUsercode());
        }

        log.info("send Fingerprint password result:" + setFingerprintBase.getResultCode());
        return setFingerprintBase.getResultCode();
    }

    private int processSendTimeConfigure(ZWaveDevice zWaveDevice, int errorcount, DoorlockPassword doorlockPassword) {
        if (!sendTimeConfigure(zWaveDevice, doorlockPassword)) {
            doorlockPassword.setErrorcount(doorlockPassword.getErrorcount() + 1);
            errorcount++;

            processErrorCount(doorlockPassword, IRemoteConstantDefine.DOOR_LOCK_PASSWORD_SYN_DATE_FAILED_MANY_TIMES,
                    IRemoteConstantDefine.DOOR_LOCK_PASSWORD_SYN_DATE_FAILED_MANY_TIMES, true);
        } else {
            errorcount = 0;
            if (tempPasswordUtils.isTempPassword()) {
                if (tempPasswordUtils.isDeleted()) {
                    doorlockPassword.setStatus(IRemoteConstantDefine.DOOR_LOCK_PASSWORD_STATUS_DELETE);
                    doorlockPassword.setSynstatus(IRemoteConstantDefine.DOOR_LOCK_PASSWORD_SYN_STATUS_SENT);
                    doorlockPassword.setSendtime(new Date());

                    ThirdPartHelper.sendDeleteThirdpartMessage(IRemoteConstantDefine.DOOR_LOCK_SYN_DELETE_SUCCESS, doorlockPassword.getZwavedeviceid(),
                            doorlockPassword.getUsercode(), deviceid, doorlockPassword.getTid(), eventtime);
                    return errorcount;
                } else {
                    doorlockPassword.setStatus(IRemoteConstantDefine.DOOR_LOCK_PASSWORD_STATUS_ECLIPSED);
                }
            }

            doorlockPassword.setSynstatus(IRemoteConstantDefine.DOOR_LOCK_PASSWORD_SYN_STATUS_SENT);
            doorlockPassword.setSendtime(new Date());

            ThirdPartHelper.sendAddThirdpartMessage(IRemoteConstantDefine.DOOR_LOCK_SYN_DATE_SUCCESS, zWaveDevice.getZwavedeviceid(),
                   doorlockPassword.getUsercode(), deviceid, doorlockPassword.getTid(), eventtime);
        }
        return errorcount;
    }

/*
    private int processSendPassword(ZWaveDevice zWaveDevice, int errorcount, DoorlockPassword doorlockPassword) {
        if (!sendPassword(zWaveDevice, doorlockPassword)) {
            errorcount++;
            doorlockPassword.setErrorcount(doorlockPassword.getErrorcount() + 1);

            processErrorCount(doorlockPassword, IRemoteConstantDefine.DOOR_LOCK_PASSWORD_SYN_STATUS_FAILED,
                    IRemoteConstantDefine.DOOR_LOCK_SYN_FAIED, true);

        } else {
            errorcount = 0;

            if (sendTimeConfigure(zWaveDevice, doorlockPassword)) {
                doorlockPassword.setSynstatus(IRemoteConstantDefine.DOOR_LOCK_PASSWORD_SYN_STATUS_SENT);

                ThirdPartHelper.sendAddThirdpartMessage(IRemoteConstantDefine.DOOR_LOCK_SYN_PASSWORD_AND_DATE_SUCCESS, zWaveDevice.getZwavedeviceid(),
                        doorlockPassword.getUsercode(),deviceid, doorlockPassword.getTid(), eventtime);
            } else {
                errorcount ++;
                doorlockPassword.setErrorcount(doorlockPassword.getErrorcount() + 1);
                doorlockPassword.setSynstatus(IRemoteConstantDefine.DOOR_LOCK_PASSWORD_SYN_STATUS_VALIDTIME_RESET);

                if (processErrorCount(doorlockPassword, IRemoteConstantDefine.DOOR_LOCK_PASSWORD_SYN_DATE_FAILED_MANY_TIMES,
                        IRemoteConstantDefine.DOOR_LOCK_PASSWORD_SYN_DATE_FAILED_MANY_TIMES, true)) {
                    return errorcount;
                }

                ThirdPartHelper.sendAddThirdpartMessage(IRemoteConstantDefine.DOOR_LOCK_SYN_PASSWORD_SUCCESS_DATE_FAIL, zWaveDevice.getZwavedeviceid(),
                        doorlockPassword.getUsercode(), deviceid, doorlockPassword.getTid(), eventtime);
            }
        }
        return errorcount;
    }
*/

    private boolean processErrorCount(DoorlockPassword doorlockPassword, Integer toSynStatus, int toThirdPartMessageType, boolean isAddAction){
        if (doorlockPassword.getErrorcount() >= 5) {
            if (toSynStatus != null && !tempPasswordUtils.isDeleted()) {
                doorlockPassword.setSynstatus(toSynStatus);
            }

            if (isAddAction && !tempPasswordUtils.isDeleted()) {
                ThirdPartHelper.sendAddThirdpartMessage(toThirdPartMessageType, doorlockPassword.getZwavedeviceid(),
                        doorlockPassword.getUsercode(), deviceid, doorlockPassword.getTid(), eventtime);
            } else {
                ThirdPartHelper.sendDeleteThirdpartMessage(toThirdPartMessageType, doorlockPassword.getZwavedeviceid(),
                        doorlockPassword.getUsercode(), deviceid, doorlockPassword.getTid(), eventtime);
            }
        }

        return doorlockPassword.getErrorcount() >= 5;
    }

    private int sendCardPassword(DoorlockPassword doorlockPassword) {
        String validfromString = sdf.format(doorlockPassword.getValidfrom());
        String validthroughString = sdf.format(doorlockPassword.getValidthrough());
        SetCardBase setCardBase = new SetCardBase();

        setCardBase.setCardinfo(doorlockPassword.getPassword());
        setCardBase.setZwavedeviceid(doorlockPassword.getZwavedeviceid());
        setCardBase.setValidfrom(validfromString);
        setCardBase.setValidthrough(validthroughString);
        setCardBase.setStarttime(doorlockPassword.getStarttime());
        setCardBase.setEndtime(doorlockPassword.getEndtime());
        setCardBase.setUsercode(doorlockPassword.getUsercode());
        setCardBase.setAsynch(IRemoteConstantDefine.SEND_PASSWORD_TYPE_SYNCH);
        setCardBase.setUsername(doorlockPassword.getUsername());
        if (doorlockPassword.getWeekday() != null) {
            setCardBase.setWeekday(doorlockPassword.getWeekday());
        }
        if (doorlockPassword.getPasswordtype() == IRemoteConstantDefine.CARD_TYPE_MF) {
            setCardBase.setCardtype(0x1);
        } else if (doorlockPassword.getPasswordtype() == IRemoteConstantDefine.CARD_TYPE_ID) {
            setCardBase.setCardtype(0x2);
        } else {
            setCardBase.setCardtype(0xf);
        }

        setCardBase.execute();

        if (setCardBase.getResultCode() == ErrorCodeDefine.SUCCESS
                || setCardBase.getResultCode() == ErrorCodeDefine.DOORLOCK_PASSWORDSUCCESS_VALIDTIMEFAILED) {
            doorlockPassword.setUsercode(setCardBase.getUsercode());
        }

        log.info("send Card password result:" + setCardBase.getResultCode());
        return setCardBase.getResultCode();
    }
/*

    private boolean sendPassword(ZWaveDevice zWaveDevice, DoorlockPassword doorlockPassword) {
        byte[] b = DoorlockPasswordHelper.createSetLockUserPasswordCommand((byte) doorlockPassword.getUsercode(), AES.decrypt2Str(doorlockPassword.getPassword()), null);
        CommandTlv ct = DoorlockPasswordHelper.createCommandTlv(b, zWaveDevice.getNuid());
        ZwaveReportRequestWrap rst = ZwaveReportRequestManager.sendRequest(zWaveDevice.getDeviceid(),
                zWaveDevice.getNuid(), ct, getAddPasswordReport(doorlockPassword.getUsercode()), 5, 0);

        Integer resultCode = null;
        Integer status = null;

        if (rst.getResponse() != null) {
            status = onReport(rst.getResponse());
        } else {
            resultCode = rst.getAckresult();
        }

        if (resultCode == null || resultCode != ErrorCodeDefine.SUCCESS) {
            return false;
        }

        return (status != null && status == ErrorCodeDefine.SUCCESS);
    }
*/

    private void sendSleepCommand() {
        RemoteEvent re = new RemoteEvent(deviceid, eventtime, taskid);
        JMSUtil.sendmessage(IRemoteConstantDefine.DOOR_LOCK_SLEEP, re);
    }

    private Byte[] getAddPasswordReport(int usercode) {
        return new Byte[]{(byte) 0x80, 0x07, 0x00, (byte) 0x80, 0x10, 0x01, 0x03, (byte) usercode};
    }

    private Integer onReport(byte[] report) {
        Integer status = null;

        if (report[8] == 1) {
            status = ErrorCodeDefine.SUCCESS;
        } else if (report[8] == 2)
            status = ErrorCodeDefine.DOORLOCK_PASSWORD_EXHAUST;
        else if (report[3] == 0)
            status = ErrorCodeDefine.DOORLOCK_SETPASSWORD_FAILED;

        return status;
    }

    private boolean sendTimeConfigure(ZWaveDevice zWaveDevice, DoorlockPassword doorlockPassword){
        return sendTimeConfigure(zWaveDevice.getDeviceid(), zWaveDevice.getNuid(), doorlockPassword.getUsertype(),
                doorlockPassword.getUsercode(), doorlockPassword.getValidfrom(), doorlockPassword.getValidthrough());
    }

    private boolean sendTimeConfigure(String deviceid, int nuid, int usertype, int usercode, Date validfrom, Date validthrough) {
        String validfromString = sdf.format(validfrom);
        String validthroughString = sdf.format(validthrough);

        int resultcode = DoorlockHelper.sendTimeConfigure(deviceid, nuid, (byte) usertype, (byte) usercode, validfromString, validthroughString, true);
        log.info("send time configure result:" + resultcode);
        return resultcode == ErrorCodeDefine.SUCCESS;
    }

    class InnerTempPasswordUtils{
        private DoorlockPassword doorlockPassword;

        public InnerTempPasswordUtils(DoorlockPassword doorlockPassword) {
            this.doorlockPassword = doorlockPassword;
        }

        public boolean isTempPassword(){
            return doorlockPassword.getUsercode() == TEMP_PASSWORD_USER_CODE;
        }

        public boolean isDeleted(){
            String validfromString = sdf.format(doorlockPassword.getValidfrom());
            String validthroughString = sdf.format(doorlockPassword.getValidthrough());
            return isTempPassword()
                    && TEMP_PASSWORD_VALID_FROM_DATE.equals(validfromString)
                    && TEMP_PASSWORD_VALID_THROUGH_DATE.equals(validthroughString);
        }
    }
}

