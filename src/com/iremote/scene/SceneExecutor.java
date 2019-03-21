package com.iremote.scene;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.NumberUtil;
import com.iremote.common.constant.ArithmeticOperator;
import com.iremote.common.mail.MailInterface;
import com.iremote.common.push.PushMessage;
import com.iremote.common.schedule.ScheduleManager;
import com.iremote.dataprivilege.IUserDataPrivilegeChecker;
import com.iremote.dataprivilege.PhoneUserDataPrivilegeCheckor;
import com.iremote.dataprivilege.ThirdpartDataPrivilegeCheckor;
import com.iremote.device.operate.IOperationTranslator;
import com.iremote.device.operate.OperationTranslatorStore;
import com.iremote.domain.*;
import com.iremote.service.*;
import com.iremote.task.devicecommand.ExecuteDeviceCommands;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

public class SceneExecutor implements Runnable
{
	private static Log log = LogFactory.getLog(SceneExecutor.class);
	private static final int[] INT_TO_WEEK = new int[]{0,64,32,16,8,4,2,1};
	private static final int[] INT_TO_EXECUTOR_SETTING = new int[]{0, 0, 1, 2, 4};

	private int scenedbid ;
	private PhoneUser phoneuser ;
	private ThirdPart thirdpart ;
	private String operator ;
	private int operatetype;
	private IUserDataPrivilegeChecker privilegecheck = null ;
	private List<Command> commandlst = new LinkedList<Command>();
	private Set<Integer> sceneset = new HashSet<Integer>() ;
	private int currentsecond;
	private int currentweek;
	private Scene scene;
	private String phoneUserName;
	private boolean isExecuted;
	private int executeType;

	public SceneExecutor(int scenedbid, PhoneUser phoneuser, ThirdPart thirdpart, String operator, int operatetype, int executeType)
	{
		super();
		this.scenedbid = scenedbid;
		this.phoneuser = phoneuser;
		this.thirdpart = thirdpart;
		this.operator = operator;
		this.operatetype = operatetype;
		this.executeType = executeType;

		if ( phoneuser != null )
			privilegecheck = new PhoneUserDataPrivilegeCheckor(this.phoneuser);
		else if ( thirdpart != null )
			privilegecheck = new ThirdpartDataPrivilegeCheckor(this.thirdpart);

		Calendar cl = Calendar.getInstance();
		currentsecond = ( cl.get(Calendar.HOUR_OF_DAY) * 60 + cl.get(Calendar.MINUTE)) * 60 + cl.get(Calendar.SECOND);
		currentweek = cl.get(Calendar.DAY_OF_WEEK);

		if (phoneuser != null)
			phoneUserName = phoneuser.getPhonenumber();
	}

	@Override
	public void run()
	{
		initCommandList();
		executeCommand();
	}

	private void  executeCommand()
	{
		int second = 0 ;
		List<Command> lst = new ArrayList<Command>();

		for (Command c : commandlst)
		{
			int delay = c.getDelay() ;
			if ( c.getScene() != null && c.getScene().getScenetype() != null
					&& c.getScene().getScenetype() == IRemoteConstantDefine.SCENE_TYPE_ASSOCIATION
					&& delay <= 2 )
				delay = 0 ;

			if ( checkCommandValidtime(c , currentweek , currentsecond) == false )
				continue;

			isExecuted = true;
			if ( delay != 0 && lst.size() > 0 )
			{
				ScheduleManager.excutein(second, new ExecuteDeviceCommands(lst , operatetype , operator, phoneUserName));
				lst.clear();
			}

			second += delay ;
			if ( privilegecheck == null
					|| ( c.getZwavedeviceid() != null && privilegecheck.checkZWaveDeviceOperationPrivilege(c.getDeviceid(), c.getZwavedeviceid()) )
					|| ( c.getInfrareddeviceid() != null && privilegecheck.checkInfraredDeviceOperationPrivilege(c.getDeviceid(), c.getInfrareddeviceid()) )
					|| checkPartitionPrivilege(c) )
				lst.add(c);

//			checkArm(c);
		}
		if ( lst.size() > 0 ){
			appsoundjpush();
			ScheduleManager.excutein(second, new ExecuteDeviceCommands(lst, operatetype , operator, phoneUserName));
		}
	}

	private boolean checkPartitionPrivilege(Command c) {
		if (c.getCommandjson() == null || c.getCommandjson().size() == 0) {
			return true;
		}

		for (int i = 0; i < c.getCommandjson().size(); i++) {
			JSONObject json = c.getCommandjson().getJSONObject(i);
			if (json.containsKey("partitionid")) {
				return privilegecheck.checkPartitionOperationPrivilege(json.getIntValue("partitionid"));
			}
		}
		return false;
	}


	private boolean checkTime(Scene scene){
		if(scene == null){
			return false;
		}
		if(StringUtils.isEmpty(scene.getStarttime()) || StringUtils.isEmpty(scene.getEndtime())
				|| scene.getWeekday() == null){
			return true;
		}
		Calendar cl = Calendar.getInstance();
		int currentsecond = ( cl.get(Calendar.HOUR_OF_DAY) * 60 + cl.get(Calendar.MINUTE)) * 60 + cl.get(Calendar.SECOND);
		int weekday = cl.get(Calendar.DAY_OF_WEEK);

		if ( scene.getStartsecond() <= currentsecond  && currentsecond <= scene.getEndsecond()
				&& (scene.getWeekday() == null || (INT_TO_WEEK[weekday] & scene.getWeekday()) != 0 ) )
			return true ;

		if ( scene.getEndsecond() < scene.getStartsecond() 	&&  currentsecond <= scene.getEndsecond()   )// start time > end time
		{
			if ( weekday != 1 )
				return scene.getWeekday() == null || ((INT_TO_WEEK[weekday - 1 ] & scene.getWeekday()) != 0);
			else
				return scene.getWeekday() == null || ((INT_TO_WEEK[7] & scene.getWeekday()) != 0);
		}
		return false;
	}

	private void appsoundjpush(){
		if(scene == null){
			return;
		}
		SceneNotification sceneNotification = scene.getScenenotification();
		if(sceneNotification == null || sceneNotification.getApp() == IRemoteConstantDefine.APP_JPUSH_NO){
			return;
		}
		PhoneUserService pus = new PhoneUserService();
		PhoneUser user = pus.query(scene.getPhoneuserid());

		PushMessage.pushSoundMessage(user.getAlias() , user.getPlatform(), sceneNotification.getMessage(), sceneNotification.getBuilder_id(), sceneNotification.getSound());
		MailInterface.sendUserMail(sceneNotification.getMail(),sceneNotification.getMessage(),sceneNotification.getMessage());

	}

	private boolean checkCondition(Scene scene) {
		if(scene == null){
			return false;
		}
		List<Conditions> conditionsList = scene.getConditionlist();
		if(conditionsList == null || conditionsList.size() == 0){
			return true;
		}
		for(Conditions conditions : conditionsList){
			if (StringUtils.isBlank(conditions.getDeviceid())) {
				if (!checkZWaveCondition(conditions)) {
					return false;
				}
			} else if (!checkRemoteCondition(conditions)){
				return false;
			}
		}
		return true;
	}

	private boolean checkRemoteCondition(Conditions conditions) {
		Remote remote = new RemoteService().querybyDeviceid(conditions.getDeviceid());
		if (remote == null) {
			return true;
        }
		if (!NumberUtil.compare(Double.valueOf(remote.getTemperature()), Double.valueOf(conditions.getStatus()),
                conditions.getOperator())) {
            return false;
        }
		return true;
	}

	private boolean checkZWaveCondition(Conditions conditions) {
		ZWaveDeviceService zds = new ZWaveDeviceService();
		if(conditions.getZwavedeviceid() == null || conditions.getZwavedeviceid() == 0){
            return true;
        }
		ZWaveDevice zd = zds.query(conditions.getZwavedeviceid());
		if (zd == null)
            return true;

		if (!convertDeviceStatus(conditions, zd)) {
            return false;
        }
		if (conditions.getChannelid() != null && conditions.getChannelid() > 0) {
            try {
                if (!checkConditionStatus(conditions, getStatus(zd, conditions.getChannelid()), zd.getStatuses(), null)) {
                    return false;
                }
            } catch (Exception e) {
                log.error("", e);
                return false;
            }
        } else if (!checkConditionStatus(conditions, Float.valueOf(zd.getStatus()), zd.getStatuses(), zd.getDevicetype())){
            return false;
        }
		return true;
	}

	private float getStatus(ZWaveDevice zd, int channelId) {
		ZWaveSubDevice subDevice = new ZWaveSubDeviceService().queryByZwavedeviceidAndChannelid(zd.getZwavedeviceid(), channelId);
		if (subDevice != null && subDevice.getStatus() != null) {
			return subDevice.getStatus();
		}
		List<Float> list = JSONArray.parseArray(zd.getStatuses(), Float.class);
		return list.get(channelId - 1);
	}

	private boolean convertDeviceStatus(Conditions conditions, ZWaveDevice zd) {
		if (StringUtils.isNotBlank(conditions.getDevicestatus())) {
			IOperationTranslator translator = OperationTranslatorStore.getInstance().getOperationTranslator(zd.getMajortype(), zd.getDevicetype());
			if (translator == null) {
				return false;
			}
			translator.setDeviceStatus(conditions.getDevicestatus());
			translator.setZWavedevice(zd);
			Integer value = translator.getValue();
			if (value == null) {
				return false;
			}
			conditions.setStatus(value.floatValue());

			if (IRemoteConstantDefine.DEVICE_TYPE_AIR_QUALITY.equals(zd.getDevicetype())) {
				return convertAirQualityOperator(conditions, translator);
			}
		}
		return true;
	}

	private boolean convertAirQualityOperator(Conditions conditions, IOperationTranslator translator) {
		String operator = translator.getOperator();
		if (operator == null) {
			return false;
		}
		if (ArithmeticOperator.le.name().equals(operator)) {
			conditions.setOperator("<=");
		} else {
			conditions.setOperator(">=");
		}
		return true;
	}

	public static boolean checkConditionStatus(Conditions conditions, Float deviceStatus,String deviceStatuses, String deviceType){
		if (conditions == null || deviceStatus == null) {
			return true;
		}
		if (StringUtils.isBlank(conditions.getOperator())) {
			if (!deviceStatus.equals(conditions.getStatus())) {
				Float aFloat = getSubStatus(deviceStatus, deviceType);
				return aFloat != null && aFloat.equals(conditions.getStatus());
			}
			return true;
		}
		if (deviceStatuses == null) {
			deviceStatuses = "[]";
		}

        Float status;
        if (conditions.getStatusesindex() == null || conditions.getStatusesindex() == -1) {
            status = deviceStatus;
        } else {
            if (deviceStatuses == null) {
                deviceStatuses = "[]";
            }
            JSONArray jsonArray = JSONArray.parseArray(deviceStatuses);
            if (jsonArray.size() < conditions.getStatusesindex() + 1) {
                return false;
            }
            status = jsonArray.getFloat(conditions.getStatusesindex());
        }
		if (!NumberUtil.compare(status, conditions.getStatus(), conditions.getOperator())) {
			Float aFloat = getSubStatus(status, deviceType);
			return aFloat != null && NumberUtil.compare(aFloat, conditions.getStatus(), conditions.getOperator());
		}
		return true;
	}

	private static Float getSubStatus(Float status, String deviceType) {
		if ((!IRemoteConstantDefine.DEVICE_TYPE_LIST_LIKE_DOOR_LOCK.contains(deviceType) && !IRemoteConstantDefine.DEVICE_TYPE_LOCK_CYLINDER.equals(deviceType))
				|| (status != 1 && status != 0)) {
			return null;
		}
		return status == 1 ? 0f : 1f;
	}

	private void initCommandList()
	{
		SceneService ss = new SceneService();
		scene = ss.query(scenedbid);
		initCommandList(scene, false);
	}

	private void initCommandList(Scene s, boolean isSceneLaunchedByScene)
	{
		if ( s == null )
			return ;

		if ( this.sceneset.contains(s.getScenedbid()))
			return ;
		this.sceneset.add(s.getScenedbid());

		if(!isSceneLaunchedByScene && !checkAssociationAndTimerSetting()){
			if(checkConditionSetting() || !checkCondition(s)){
				return;
			}
			if(!checkTime(s)){
				return;
			}
		}

		for ( Command c : s.getCommandlist())
		{
			if ( c.getLaunchscenedbid() == null || c.getLaunchscenedbid() == 0 )
				this.commandlst.add(c);
			else if ( checkCommandValidtime(c , currentweek , currentsecond) )
				initCommandList(c.getLaunchscenedbid());
		}
	}

	private boolean checkConditionSetting() {
		if (executeType != IRemoteConstantDefine.SCENE_EXECUTE_TYPE_SCENE_CONDITIONS) {
			return false;
		}
		return (scene.getExecutorsetting() & INT_TO_EXECUTOR_SETTING[executeType]) != 0;
	}

	private boolean checkAssociationAndTimerSetting() {
		if (executeType == IRemoteConstantDefine.SCENE_EXECUTE_TYPE_SCENE_CONDITIONS) {
			return false;
		}
		if (executeType == IRemoteConstantDefine.SCENE_EXECUTE_TYPE_USER_TRIGGER
				|| executeType == IRemoteConstantDefine.SCENE_EXECUTE_TYPE_TRIGGER_BY_SCENE) {
			return true;
		}
		return (scene.getExecutorsetting() & INT_TO_EXECUTOR_SETTING[executeType]) == 0;
	}

	private void initCommandList(int subscenedbid)
	{
		SceneService ss = new SceneService();

		Scene s = ss.query(subscenedbid);
		if ( s != null )
			this.initCommandList(s, true);
	}

	private boolean checkCommandValidtime(Command c , int weekday , int currentsecond)
	{
		if ( this.operatetype != IRemoteConstantDefine.OPERATOR_TYPE_ASSCIATION )
			return true;

		if ( c.getWeekday() == null || c.getStartsecond() == null || c.getEndsecond() == null )
			return true ;

		if ( c.getStartsecond() <= currentsecond  && currentsecond <= c.getEndsecond() && (INT_TO_WEEK[weekday] & c.getWeekday()) != 0 )
			return true ;

		if ( c.getEndsecond() < c.getStartsecond()  && c.getStartsecond() <= currentsecond && (INT_TO_WEEK[weekday] & c.getWeekday()) != 0 )
			return true;

		if ( c.getEndsecond() < c.getStartsecond() 	&&  currentsecond <= c.getEndsecond()   )// start time > end time
		{
			if ( weekday != 1 )
				return ((INT_TO_WEEK[weekday - 1 ] & c.getWeekday()) != 0);
			else
				return ((INT_TO_WEEK[7] & c.getWeekday()) != 0);
		}

		return false ;
	}

	public boolean isExecuted() {
		return isExecuted;
	}
}
