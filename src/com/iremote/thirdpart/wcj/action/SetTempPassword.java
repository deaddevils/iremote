package com.iremote.thirdpart.wcj.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.iremote.action.helper.DoorlockHelper;
import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.common.encrypt.AES;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilege;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeType;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.ThirdPart;
import com.iremote.domain.ZWaveDevice;
import com.iremote.infraredtrans.ConnectionManager;
import com.iremote.service.ZWaveDeviceService;
import com.iremote.thirdpart.cobbe.event.SendDoorLockPasswordForZufangHelper;
import com.iremote.thirdpart.common.SendThirdpartHelper;
import com.iremote.thirdpart.common.ThirdPartHelper;
import com.iremote.thirdpart.wcj.domain.DoorlockPassword;
import com.iremote.thirdpart.wcj.service.ComunityRemoteService;
import com.iremote.thirdpart.wcj.service.DoorlockPasswordService;
import com.opensymphony.xwork2.Action;

@DataPrivilege(dataprivilgetype = DataPrivilegeType.MODIFY, domain = "device", parameters = { "zwavedeviceid",
		"lockid" })
public class SetTempPassword {

	@SuppressWarnings("unused")
	private static Log log = LogFactory.getLog(SetTempPassword.class);

	private int resultCode = ErrorCodeDefine.SUCCESS;
	private int zwavedeviceid;
	private int asynch = 0;
	protected String superpw;
	private String superpwtimes;
	private String superpwtimee;
	private Integer weekday;
	private String starttime;
	private String endtime;

	private String username;
	protected ZWaveDevice lock;
	private ThirdPart thirdpart;
	private PhoneUser phoneuser;
	protected int usercode = 0xF2;

	private DoorlockPasswordService dpsvr = new DoorlockPasswordService();
	private DoorlockPassword doorlockpassword;
	private List<DoorlockPassword> activepassword;
	private String encryptedpassword;
	private String tid;

	public String execute() {

		ZWaveDeviceService zds = new ZWaveDeviceService();
		lock = zds.query(zwavedeviceid);

		if (check() == false)
			return Action.SUCCESS;

		usercode = (this.getCommandUsercode() & 0xff);

		createpassword();
		if(lock.getNuid()<256){
			doorlockpassword.setLocktype(1);
		}else{
			doorlockpassword.setLocktype(2);
		}

		if (asynch == 0) {
			doorlockpassword.setTid(tid);
			dpsvr.save(doorlockpassword);			
			if("2050-01-01 00:00:00".equals(superpwtimes)&&"2050-01-01 00:00:01".equals(superpwtimee)){
				int resultCode = sendPassword(doorlockpassword);
				if(resultCode==ErrorCodeDefine.SUCCESS){
					doorlockpassword.setSynstatus(IRemoteConstantDefine.DOOR_LOCK_PASSWORD_SYN_STATUS_SENT);
					doorlockpassword.setStatus(IRemoteConstantDefine.DOOR_LOCK_PASSWORD_STATUS_DELETE);
					doorlockpassword.setSendtime(new Date());
					new SendThirdpartHelper(IRemoteConstantDefine.DOOR_LOCK_SYN_PASSWORD_AND_DATE_SUCCESS,
							zwavedeviceid, usercode & 0xff, lock.getDeviceid(), tid,
							IRemoteConstantDefine.DELETE_LOCK_USER_RESULT).sendThirdpartMsg();
				}
			}else{
				int resultCode = sendPassword(doorlockpassword);
				if(resultCode==ErrorCodeDefine.SUCCESS){
					doorlockpassword.setSynstatus(IRemoteConstantDefine.DOOR_LOCK_PASSWORD_SYN_STATUS_SENT);
					doorlockpassword.setStatus(IRemoteConstantDefine.DOOR_LOCK_PASSWORD_STATUS_ECLIPSED);
					doorlockpassword.setSendtime(new Date());
					new SendThirdpartHelper(IRemoteConstantDefine.DOOR_LOCK_SYN_PASSWORD_AND_DATE_SUCCESS,
							zwavedeviceid, usercode & 0xff, lock.getDeviceid(), tid,
							IRemoteConstantDefine.ADD_LOCK_USER_RESULT).sendThirdpartMsg();
				}else if(resultCode==ErrorCodeDefine.DOORLOCK_PASSWORDSUCCESS_VALIDTIMEFAILED){
					doorlockpassword.setSynstatus(IRemoteConstantDefine.DOOR_LOCK_PASSWORD_SYN_STATUS_VALIDTIME_RESET);
					new SendThirdpartHelper(IRemoteConstantDefine.DOOR_LOCK_SYN_DATE_SUCCESS,
							zwavedeviceid, usercode & 0xff, lock.getDeviceid(), tid,
							IRemoteConstantDefine.ADD_LOCK_USER_RESULT).sendThirdpartMsg();
				}
			}
			//DoorlockPasswordTaskManager.addTask(lock.getDeviceid(),new SetPasswordThread(lock.getZwavedeviceid(), (byte) usercode));
		} else {
			SetPasswordThread pt = new SetPasswordThread(doorlockpassword);
			pt.sendpassword();
			this.resultCode = pt.getResultCode();

			if (this.resultCode == ErrorCodeDefine.SUCCESS) {
				dpsvr.save(doorlockpassword);
				updateActivePassword();
				usercode = doorlockpassword.getUsercode();
				//ThirdPartHelper.sendDeleteThirdpartMessage(IRemoteConstantDefine.DOOR_LOCK_SYN_PASSWORD_AND_DATE_SUCCESS, zwavedeviceid, usercode, lock.getDeviceid(), tid, new Date());
			}
		}

		return Action.SUCCESS;
	}
    private int sendPassword(DoorlockPassword doorlockPassword) {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String validfromString = sdf.format(doorlockPassword.getValidfrom());
        String validthroughString = sdf.format(doorlockPassword.getValidthrough());
        SetPasswordAction setPasswordAction = new SetPasswordAction();

        setPasswordAction.setZwavedeviceid(doorlockPassword.getZwavedeviceid());
        setPasswordAction.setPassword(AES.decrypt2Str(doorlockPassword.getPassword()));
        setPasswordAction.setValidfrom(validfromString);
        setPasswordAction.setValidthrough(validthroughString);
        if (doorlockPassword.getWeekday() != null) {
            setPasswordAction.setWeekday(doorlockPassword.getWeekday());
        }
        setPasswordAction.setStarttime(doorlockPassword.getStarttime());
        setPasswordAction.setEndtime(doorlockPassword.getEndtime());
        setPasswordAction.setAsynch(IRemoteConstantDefine.SEND_PASSWORD_TYPE_SYNCH);
        setPasswordAction.setUsercode(doorlockPassword.getUsercode());

        setPasswordAction.execute();

        if (setPasswordAction.getResultCode() == ErrorCodeDefine.SUCCESS) {
            doorlockPassword.setUsercode(setPasswordAction.getUsercode());
        }

        log.info("send password result:" + setPasswordAction.getResultCode());
        return setPasswordAction.getResultCode();
    }
	private boolean check() {
		if (lock == null) {
			resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT;
			return false;
		}

		if (asynch != 0 && lock.getStatus() != null && lock.getStatus() == -1) {
			resultCode = ErrorCodeDefine.DEVICE_OFFLINE;
			return false;
		}

		if (asynch != 0 && ConnectionManager.isOnline(lock.getDeviceid()) == false) {
			resultCode = ErrorCodeDefine.DEVICE_OFFLINE;
			return false;
		}

		encryptedpassword = AES.encrypt2Str(superpw);

		if (checkpassword() == false) {
			resultCode = ErrorCodeDefine.DOORLOCK_SETPASSWORD_CLASH;
			return false;
		}
		return true;
	}

	private boolean checkpassword() {
		activepassword = dpsvr.queryActivePassword(zwavedeviceid);
		if (activepassword == null || activepassword.size() == 0)
			return true;
		for (DoorlockPassword dp : activepassword) {
			if (dp.getPassword().equals(encryptedpassword))
				return false;
		}
		return true;
	}

	private void createpassword() {
		doorlockpassword = new DoorlockPassword();
		doorlockpassword.setZwavedeviceid(zwavedeviceid);
		doorlockpassword.setPassword(encryptedpassword);
		doorlockpassword.setValidfrom(Utils.parseTime(superpwtimes));
		doorlockpassword.setValidthrough(Utils.parseTime(superpwtimee));
		doorlockpassword.setStatus(IRemoteConstantDefine.DOOR_LOCK_PASSWORD_STATUS_ACTIVE);
		doorlockpassword.setSynstatus(IRemoteConstantDefine.DOOR_LOCK_PASSWORD_SYN_STATUS_WAITING);
		doorlockpassword.setUsercode(usercode & 0xff);
		doorlockpassword.setWeekday(weekday);
		doorlockpassword.setStarttime(starttime);
		doorlockpassword.setEndtime(endtime);
	}

	private void updateActivePassword() {
		if (activepassword == null || activepassword.size() == 0)
			return;
		for (DoorlockPassword dp : activepassword) {
			if (dp.getUsercode() == this.usercode)
				dp.setStatus(IRemoteConstantDefine.DOOR_LOCK_PASSWORD_STATUS_ECLIPSED);
		}
	}

	protected byte getCommandUsercode() {
		return (byte) 0xF2;
	}

	public int getUsercode() {
		return this.getCommandUsercode() & 0xff;
	}

	public int getResultCode() {
		return resultCode;
	}

	public void setLockid(int lockid) {
		this.zwavedeviceid = lockid;
	}

	public void setSuperpw(String superpw) {
		this.superpw = superpw;
	}

	public void setSuperpwtimes(String superpwtimes) {
		this.superpwtimes = superpwtimes;
	}

	public void setSuperpwtimee(String superpwtimee) {
		this.superpwtimee = superpwtimee;
	}

	public void setZwavedeviceid(int zwavedeviceid) {
		this.zwavedeviceid = zwavedeviceid;
	}

	public void setThirdpart(ThirdPart thirdpart) {
		this.thirdpart = thirdpart;
	}

	public void setPhoneuser(PhoneUser phoneuser) {
		this.phoneuser = phoneuser;
	}

	public void setPassword(String password) {
		this.superpw = password;
	}

	public void setValidfrom(String validfrom) {
		this.superpwtimes = validfrom;
	}

	public void setValidthrough(String validthrough) {
		this.superpwtimee = validthrough;
	}

	public void setAsynch(int asynch) {
		this.asynch = asynch;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setWeekday(Integer weekday) {
		this.weekday = weekday;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

}
