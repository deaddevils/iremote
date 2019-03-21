package com.iremote.action.scene;

import java.util.List;

import com.iremote.common.encrypt.AES;
import com.iremote.domain.*;
import com.iremote.service.*;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iremote.action.helper.TimerHelper;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.device.operate.IOperationTranslator;
import com.iremote.device.operate.OperationTranslatorStore;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SceneHelper{
	private Log log = LogFactory.getLog(SceneHelper.class);

	public void saveAssociationscene(Scene scene,String associationlist){
		if(associationlist != null && associationlist.length() > 0){
			JSONArray associationJson = (JSONArray)JSON.parse(associationlist);
			for(int i = 0; i < associationJson.size(); i++){
				JSONObject jsonObject = associationJson.getJSONObject(i);
				Associationscene associationscene = JSON.toJavaObject(jsonObject, Associationscene.class);
				associationscene.setAssociationsceneid(0);
				associationscene.setScenetype(scene.getScenetype());
				String zwavedeviceid = jsonObject.getString("zwavedeviceid");
				String cameraid = jsonObject.getString("cameraid");
				
				if ( StringUtils.isNotBlank(zwavedeviceid) && Integer.parseInt(zwavedeviceid) != 0 )
				{	
					ZWaveDevice zwavedevice = new ZWaveDeviceService().query(Integer.parseInt(zwavedeviceid));
					
					if ( zwavedevice == null )
						continue ;
					associationscene.setZwavedevice(zwavedevice);
				
					IOperationTranslator ot = OperationTranslatorStore.getInstance().getOperationTranslator(zwavedevice.getMajortype(), zwavedevice.getDevicetype());
					if ( ot != null )
					{
						ot.setZWavedevice(zwavedevice);
						ot.setDeviceStatus(associationscene.getDevicestatus());
						associationscene.setOperator(ot.getOperator());
						associationscene.setStatus(ot.getValue());
					}
				}
				else if ( StringUtils.isNotBlank(cameraid))
					associationscene.setCameraid(Integer.valueOf(cameraid));

				if(scene.getScenetype() == IRemoteConstantDefine.SCENE_TYPE_ASSOCIATION)
					deltAssociationscene2(scene ,associationscene);

				associationscene.setScene(scene);
				scene.getAssociationscenelist().add(associationscene);
			}
		}
	}
	
	public void saveCommand(Scene scene,String commandlist){
		if(commandlist != null && commandlist.length() > 0){
			JSONArray commandJson = JSON.parseArray(commandlist);
			for(int i = 0; i < commandJson.size(); i++){
				JSONObject jsonObject = commandJson.getJSONObject(i);
				Command command = JSON.toJavaObject(jsonObject, Command.class);
				command.setCommandid(0);
				command.setScene(scene);
				String zwavedeviceid = jsonObject.getString("zwavedeviceid");
				String infrareddeviceid = jsonObject.getString("infrareddeviceid");
				if(zwavedeviceid != null && zwavedeviceid.length()>0 && !zwavedeviceid.equals("0")){
					ZWaveDevice zwavedevice = new ZWaveDeviceService().query(Integer.parseInt(zwavedeviceid));
					command.setZwavedevice(zwavedevice);
					command.setDeviceid(zwavedevice.getDeviceid());
					command.setApplianceid(zwavedevice.getApplianceid());
					
					IOperationTranslator op = getOperationTranslator(scene , command);
					if ( op != null )
					{
						command.setZwavecommand(op.getCommand());
						command.setZwavecommands(op.getCommands());
					}
					
				}else if(infrareddeviceid != null && infrareddeviceid.length()>0 && !infrareddeviceid.equals("0")){
					InfraredDevice infrareddevice = new InfraredDeviceService().query(Integer.parseInt(infrareddeviceid));
					if(infrareddevice != null){
						command.setInfrareddevice(infrareddevice);
						command.setDeviceid(infrareddevice.getDeviceid());
						command.setApplianceid(infrareddevice.getApplianceid());
						command.setInfraredcode(getInfraredcode(command));
					}
				}else{
					saveLaunchscenedb(command);
				}
				if(scene.getScenetype() == IRemoteConstantDefine.SCENE_TYPE_ASSOCIATION){
					//deltAssociationscene2(scene.getAssociationscenelist().get(0));
					command.setAssociationscene(scene.getAssociationscenelist().get(0));
				}
				
				if ( StringUtils.isNotBlank(command.getStarttime()))
					command.setStartsecond(Utils.time2second(command.getStarttime()));
				if ( StringUtils.isNotBlank(command.getEndtime()))
					command.setEndsecond(Utils.time2second(command.getEndtime()));

                if (command.getCommandjson() != null) {
                    for (int j = 0; j < command.getCommandjson().size(); j++) {
                        JSONObject jo = command.getCommandjson().getJSONObject(j);
                        if (jo.containsKey("password")) {
                            String password = jo.getString("password");
							try {
								jo.put("encryptpassword", AES.encrypt(password));
								jo.put("password", null);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}

						if (jo.containsKey(IRemoteConstantDefine.DEVICE_ARMSTATUS)) {
							int armstatus = jo.getIntValue(IRemoteConstantDefine.DEVICE_ARMSTATUS);
							if (armstatus == IRemoteConstantDefine.PARTITION_STATUS_DIS_ARM) {
								jo.put(IRemoteConstantDefine.OPERATION, IRemoteConstantDefine.WARNING_TYPE_USER_DISARM);
							}
							if (armstatus == IRemoteConstantDefine.PARTITION_STATUS_OUT_HOME) {
								jo.put(IRemoteConstantDefine.OPERATION, IRemoteConstantDefine.WARNING_TYPE_USER_ARM);
							}
							if (armstatus == IRemoteConstantDefine.PARTITION_STATUS_IN_HOME) {
								jo.put(IRemoteConstantDefine.OPERATION, IRemoteConstantDefine.WARNING_TYPE_USER_INHOME_ARM);
							}

                            jo.put("zwavedeviceid", zwavedeviceid);
						}
                    }
                    command.setCommandjsonstr(command.getCommandjson().toJSONString());
                }

				scene.getCommandlist().add(command);
			}
		}
	}
	
	public void saveTimer(Scene scene,String timerlist){
		if(timerlist != null && timerlist.length() > 0){
			JSONArray timerJson = JSON.parseArray(timerlist);
			TimerService ts = new TimerService();
			for(int i = 0; i < timerJson.size(); i++){
				JSONObject jsonObject = timerJson.getJSONObject(i);
				Timer timer = JSON.toJavaObject(jsonObject, Timer.class);
				timer.setTime(Utils.excutetime2time(timer.getExcutetime()));
				timer.setScenetype(scene.getScenetype());
				if(scene.getScenetype() == IRemoteConstantDefine.SCENE_TYPE_TIMER && scene.getCommandlist() != null && scene.getCommandlist().size() == 1){
					Command command = scene.getCommandlist().get(0);
					timer.setZwavedevice(command.getZwavedevice());
					timer.setZwavecommandbase64(command.getZwavecommandbase64() != null ? command.getZwavecommandbase64() : command.getZwavecommandsbase64());
					//timer.setZwavecommand(command.getZwavecommand());
					timer.setInfrareddevice(command.getInfrareddevice());
					timer.setInfraredcodebase64(command.getInfraredcodebase64());
					//timer.setInfraredcode(command.getInfraredcode());
					timer.setCommandjsonstr(command.getCommandjsonstr());
					timer.setDescription(command.getDescription());
				}
				timer.setScene(scene);
				timer.setExecutor(1);
				if ((timer.getTimetype() == IRemoteConstantDefine.SCENE_TIME_TYPE_SUNRISE
						|| timer.getTimetype() == IRemoteConstantDefine.SCENE_TIME_TYPE_SUNSET)
						&& timer.getExcutetime() == null)
						timer.setExcutetime("00:00");
				scene.getTimerlist().add(timer);
			}
		}
	}
	
	public void resetTimer(List<Timer> list)
	{
		if(list != null && list.size() > 0){
			TimerHelper.cancelTimer(list);
			TimerHelper.addTimer(list);
		}
	}

	public void saveCondition(Scene scene,String conditionlist){
		if(conditionlist != null && conditionlist.length() > 0){
			JSONArray conditionjson = (JSONArray)JSON.parse(conditionlist);
			for(int i = 0; i < conditionjson.size(); i++){
				JSONObject jsonObject = conditionjson.getJSONObject(i);
				Conditions conditions = JSON.toJavaObject(jsonObject, Conditions.class);
				conditions.setScene(scene);
				if(conditions.getChannelid() == null)
					conditions.setChannelid(0);
				conditions.setStatus(getStatus(conditions));
				scene.getConditionlist().add(conditions);
			}
		}
	}

	public void saveNotification(Scene scene,String notification){
		if(StringUtils.isNotEmpty(notification)){
			SceneNotification sceneNotification = JSON.parseObject(notification,SceneNotification.class);
			sceneNotification.ringToBuilderAndSound();
			sceneNotification.setScene(scene);
			scene.setScenenotification(sceneNotification);
		}
	}

	public Float getStatus(Conditions conditions){
		if (conditions.getStatus() != null) {
			return conditions.getStatus();
		}
		if(conditions.getZwavedeviceid() == null || conditions.getZwavedeviceid() == 0 || StringUtils.isEmpty(conditions.getDevicestatus())){
			return null;
		}
		ZWaveDeviceService zds = new ZWaveDeviceService();
		ZWaveDevice zwavedevice = zds.query(conditions.getZwavedeviceid());
		IOperationTranslator operationTranslator = OperationTranslatorStore.getInstance().getOperationTranslator(zwavedevice.getMajortype(), zwavedevice.getDevicetype());
		operationTranslator.setDeviceStatus(conditions.getDevicestatus());
		return Float.valueOf(operationTranslator.getValue());
	}
	
	public Integer getStatus(Associationscene associationscene){
		ZWaveDevice zwavedevice = associationscene.getZwavedevice();
		IOperationTranslator operationTranslator = OperationTranslatorStore.getInstance().getOperationTranslator(zwavedevice.getMajortype(), zwavedevice.getDevicetype());
		operationTranslator.setDeviceStatus(associationscene.getDevicestatus());
		return operationTranslator.getValue();
	}
	
	public byte[] getZwavecommand(Scene scene,Command command){
		IOperationTranslator operationTranslator = getOperationTranslator ( scene , command ); 
		if(operationTranslator != null)
			return operationTranslator.getCommand();
		return null ;
	};
	
	public byte[] getZwavecommands(Scene scene,Command command){
		IOperationTranslator operationTranslator = getOperationTranslator ( scene , command ); 
		if(operationTranslator != null)
			return operationTranslator.getCommands();
		return null ;
	};
	
	public IOperationTranslator getOperationTranslator(Scene scene,Command command)
	{
		ZWaveDevice zwavedevice = command.getZwavedevice();
		IOperationTranslator operationTranslator = OperationTranslatorStore.getInstance().getOperationTranslator(zwavedevice.getMajortype(), zwavedevice.getDevicetype());
		if(operationTranslator != null)
		{
			operationTranslator.setCommandjson(command.getCommandjsonstr());
			operationTranslator.setZWavedevice(zwavedevice);	
			if ( scene != null && scene.getScenetype() != null )
			{
				if ( scene.getScenetype() == IRemoteConstantDefine.SCENE_TYPE_ASSOCIATION)
					operationTranslator.setOperationType(IRemoteConstantDefine.OPERATOR_TYPE_ASSCIATION);
				else if ( scene.getScenetype() == IRemoteConstantDefine.SCENE_TYPE_SCENE )
					operationTranslator.setOperationType(IRemoteConstantDefine.OPERATOR_TYPE_SCENE);
				else if ( scene.getScenetype() == IRemoteConstantDefine.SCENE_TYPE_TIMER )
					operationTranslator.setOperationType(IRemoteConstantDefine.OPERATOR_TYPE_TIMER);
			}
		}	
		return operationTranslator;
	}
	
	public byte[] getInfraredcode(Command command){
		InfraredDevice infrareddevice = command.getInfrareddevice();
		IOperationTranslator operationTranslator = OperationTranslatorStore.getInstance().getOperationTranslator(infrareddevice.getMajortype(), infrareddevice.getDevicetype());
		if(operationTranslator!=null){
			operationTranslator.setInfrareddevice(infrareddevice);
			operationTranslator.setCommandjson(command.getCommandjsonstr());
		}
		return operationTranslator.getCommand();
	};
	
	public void saveLaunchscenedb(Command command){
		JSONArray jsonarray = command.getCommandjson();
		if(jsonarray == null)
			return;
		for(int i = 0; i < jsonarray.size(); i++){
			JSONObject json = jsonarray.getJSONObject(i);
			if(json.getString("operation").equals("launchscene"))
				command.setLaunchscenedbid(json.getIntValue("value"));
		}
	}
	
	public void deltAssociationscene2(Scene scene,Associationscene associationscene )
	{
		AssociationsceneService associationsceneService = new AssociationsceneService();
		SceneService ss = new SceneService();
		List<Associationscene> assList = associationsceneService.queryAssociationscene2(associationscene);
		if(assList == null || assList.size() == 0)
			return ;
		
		for(Associationscene ass : assList)
		{
			associationsceneService.delete(ass);
			if ( ass.getScene() != null && ass.getScene().getScenedbid() != scene.getScenedbid())
				ss.delete(ass.getScene());
		}
		
	}

	public static boolean checkCity(List<Timer> timers){
		if (timers == null) {
			return true;
		}

		for (Timer timer : timers) {
			if (timer.getTimetype() == IRemoteConstantDefine.SCENE_TIME_TYPE_SUNSET
					|| timer.getTimetype() == IRemoteConstantDefine.SCENE_TIME_TYPE_SUNRISE) {
				Integer phoneuserid = getSceneOwnerByTimer(timer);

				if (phoneuserid == null) {
					continue;
				}

				AddressService addressService = new AddressService();
				Address address = addressService.queryByPhoneuserid(phoneuserid);

				if (address == null || address.getCityid() == null) {
					return false;
				}
			}
		}
		return true;
	}

	public static boolean checkCity(String timerlist){
		if(timerlist != null && timerlist.length() > 0) {
			List<Timer> timers = JSON.parseArray(timerlist, Timer.class);

			return checkCity(timers);
		}
		return true;
	}

	public static Integer getSceneOwnerByTimer(Timer timer) {
		Integer phoneuserid = null;
		if (timer.getScene().getScenetype() == IRemoteConstantDefine.SCENE_TYPE_SCENE) {
			phoneuserid = timer.getScene().getPhoneuserid();
		}

		if (timer.getScene().getScenetype() == IRemoteConstantDefine.SCENE_TYPE_TIMER) {
			if (timer.getZwavedevice() != null) {
				RemoteService rs = new RemoteService();
				phoneuserid = rs.queryOwnerId(timer.getZwavedevice().getDeviceid());
			} else if (timer.getInfrareddevice() != null) {
				RemoteService rs = new RemoteService();
				phoneuserid = rs.queryOwnerId(timer.getInfrareddevice().getDeviceid());
			}
		}

		return phoneuserid;
	}
}
