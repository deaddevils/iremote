package com.iremote.event.association;

import java.util.HashSet;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.iremote.action.helper.DeviceHelper;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.TagDefine;
import com.iremote.common.Utils;
import com.iremote.common.jms.ITextMessageProcessor;
import com.iremote.common.jms.vo.ZWaveDeviceStatusChange;
import com.iremote.domain.Associationscene;
import com.iremote.domain.Conditions;
import com.iremote.domain.ZWaveDevice;
import com.iremote.infraredtrans.tlv.TlvWrap;
import com.iremote.scene.SceneExecutor;
import com.iremote.service.AssociationsceneService;
import com.iremote.service.ConditionsService;
import com.iremote.service.ZWaveDeviceService;

public class DeviceReportAssociation extends ZWaveDeviceStatusChange implements ITextMessageProcessor {

	private ZWaveDevice device;
	private int statuses[];
	private boolean pythonCondition;
	protected Integer associationstatus ;
	protected Integer associationoldstatus ;

	@Override
	public void run()
	{
		Integer ot = TlvWrap.readInteter(getReport(), TagDefine.TAG_OPERATION_TYPE, 4);

		if ( ot != null
				&& ot != IRemoteConstantDefine.OPERATOR_TYPE_APP_USER
				&& ot != IRemoteConstantDefine.OPERATOR_TYPE_DEVICE_REPORT
				&& ot != IRemoteConstantDefine.OPERATOR_TYPE_THIRD_PARTER)
			return ;

		if ( IRemoteConstantDefine.DEVICE_TYPE_AIR_QUALITY.equals(super.getDevicetype()))
			return ;

		ZWaveDeviceService zds = new ZWaveDeviceService();
		device = zds.query(getZwavedeviceid());

		if ( device == null )
			return ;

		if (IRemoteConstantDefine.DEVICE_TYPE_ELECTRIC_FENCE.equals(device.getDevicetype())
				|| DeviceHelper.isUsedSubDeviceStatus(device.getDevicetype())) {
			initAssocationStatusForSubDevice();
		} else {
			initAssocationStatus();
		}

		if (checkStatus())
			return ;

		int status = 0 ;
		if ( this.associationstatus != null )
			status = this.associationstatus;

		statuses = mapstatus(status);
		HashSet<Integer> sceneDbIdSet = new HashSet<>();
		List<Associationscene> lst = queryAssociationscene();
		if ( lst != null && lst.size() > 0 ){
			for (Associationscene as : lst )
			{
				if(as.getScene().getEnablestatus() == IRemoteConstantDefine.SCENE_ENABLESTATUS_YES){
					SceneExecutor se = new SceneExecutor(as.getScenedbid() , null , null ,
							IRemoteConstantDefine.ASSOCIATION_SCENE_TASK_OPERATOR,
							IRemoteConstantDefine.OPERATOR_TYPE_ASSCIATION,
							IRemoteConstantDefine.SCENE_EXECUTE_TYPE_ASSOCIATION);
					se.run();
					if (se.isExecuted()) {
						sceneDbIdSet.add(as.getScenedbid());
					}
				}
			}
		}

		List<Conditions> conditionsList = queryConditions();
		if ( conditionsList != null && conditionsList.size() > 0 ){
			for ( Conditions ct : conditionsList )
			{
			    processConditionChannel(ct);
				if(!SceneExecutor.checkConditionStatus(ct, Float.valueOf(oldstatus), oldstatuses, device.getDevicetype())
						&& !sceneDbIdSet.contains(ct.getScene().getScenedbid())
						&& ct.getScene().getEnablestatus() == IRemoteConstantDefine.SCENE_ENABLESTATUS_YES){
					SceneExecutor se = new SceneExecutor(ct.getScene().getScenedbid() , null , null ,
							IRemoteConstantDefine.ASSOCIATION_SCENE_TASK_OPERATOR,
							IRemoteConstantDefine.OPERATOR_TYPE_ASSCIATION,
							IRemoteConstantDefine.SCENE_EXECUTE_TYPE_SCENE_CONDITIONS);
					se.run();
				}
			}
		}
	}

	private void initAssocationStatusForSubDevice() {
		if ( super.getChannel() == 0 )
		{
			this.associationstatus = super.getStatus();
			this.associationoldstatus = super.getOldstatus();

			if ( this.associationstatus != null
					&& ( this.associationstatus == IRemoteConstantDefine.DEVICE_STATUS_TAMPLE_ALARM
					|| this.associationstatus == IRemoteConstantDefine.LOCK_PASSWORD_ERROR_5_TIMES
					|| this.associationstatus == IRemoteConstantDefine.LOCK_KEY_ERROR))
			{
				this.associationstatus = super.getShadowstatus();
				this.associationoldstatus = super.getOldshadowstatus();
			}
		}
		else
		{
			this.associationstatus = super.getStatus();
			this.associationoldstatus = super.getOldstatus();
		}

		if ( IRemoteConstantDefine.DEVICE_TYPE_SOS_ALARM.equals(super.getDevicetype()) )
			this.associationoldstatus = null ;
	}

	private void processConditionChannel(Conditions ct) {
	    if (DeviceHelper.isUsedSubDeviceStatus(device.getDevicetype())){
	    	//this type device's status find from zwavesubdevice table
		    return;
	    }
        if (ct.getStatusesindex() == null && ct.getChannelid() != null) {
            ct.setStatusesindex(ct.getChannelid() - 1);
        }
    }

    private boolean checkStatus() {
		if (this.associationoldstatus == null || !this.associationoldstatus.equals(this.associationstatus)) {
			return false;
		}

		pythonCondition = true;
		if (IRemoteConstantDefine.DEVICE_TYPE_DEHUMIDIFY.equals(getDevicetype())) {
			return compare(9) && compare(10) && compare(11);
		}
		if (IRemoteConstantDefine.DEVICE_TYPE_FRESH_AIR_IN.equals(getDevicetype())) {
			return compare(3) && compare(4) && compare(5);
		}
		if (IRemoteConstantDefine.DEVICE_TYPE_AIR_CONDITIONER.equals(getDevicetype())) {
			return compare(4) && compare(5);
		}
		if (IRemoteConstantDefine.DEVICE_TYPE_HEATING_CONTROLLER.equals(getDevicetype())) {
			return compare(2) && compare(3);
		}
		if (IRemoteConstantDefine.DEVICE_TYPE_MOVE.equals(getDevicetype())) {
			return compare(0) && compare(2) && compare(4);
		}
	    if (IRemoteConstantDefine.DEVICE_TYPE_DRESS_HELPER.equals(getDevicetype())) {
		    return compare(1) && compare(2) && compare(3);
	    }
	    if (IRemoteConstantDefine.DEVICE_TYPE_PLASMA_DRYER.equals(getDevicetype())) {
		    return compare(2) && compare(3);
	    }
		if (IRemoteConstantDefine.DEVICE_TYPE_AIR_QUALITY.equals(getDevicetype())) {
			JSONArray jsonArray = JSONArray.parseArray(getStatuses());
			jsonArray.remove(0);
			JSONArray jsonArray1 = JSONArray.parseArray(getOldstatuses());
			jsonArray1.remove(0);
			return jsonArray.toJSONString().equals(jsonArray1.toJSONString());
		}
		return true;
	}

	private boolean compare(int index) {
		JSONArray oldJsonArray = DeviceHelper.getZWaveDeviceStatusesJSONArray(getOldstatuses());
		if (oldJsonArray.size() < index + 1) {
			return false;
		}
		Integer status = JSONArray.parseArray(getStatuses()).getInteger(index);
		Integer oldStatus = oldJsonArray.getInteger(index);
		return status.equals(oldStatus);
	}

	protected void initAssocationStatus()
	{
		if ( super.getChannel() == 0 )
		{
			this.associationstatus = super.getStatus();
			this.associationoldstatus = super.getOldstatus();
			
			if ( this.associationstatus != null 
					&& ( this.associationstatus == IRemoteConstantDefine.DEVICE_STATUS_TAMPLE_ALARM
							|| this.associationstatus == IRemoteConstantDefine.LOCK_PASSWORD_ERROR_5_TIMES
							|| this.associationstatus == IRemoteConstantDefine.LOCK_KEY_ERROR))
			{
				this.associationstatus = super.getShadowstatus();
				this.associationoldstatus = super.getOldshadowstatus();
			}
		}
		else 
		{
			this.associationstatus = Utils.getJsonArrayValue(getStatuses(), getChannel() - 1 );
			this.associationoldstatus = Utils.getJsonArrayValue(super.getOldstatuses(), getChannel() - 1 );
		}
		
		if ( IRemoteConstantDefine.DEVICE_TYPE_SOS_ALARM.equals(super.getDevicetype()) )
			this.associationoldstatus = null ;  
		
	}
	
	@Override
	public String getTaskKey() 
	{
		return getDeviceid();
	}
	
	private List<Associationscene> queryAssociationscene()
	{
		AssociationsceneService svr = new AssociationsceneService();
		return svr.query(device, getChannel(), statuses);
	}

	private List<Conditions> queryConditions()
	{
		ConditionsService cs = new ConditionsService();

		return pythonCondition
				? cs.querybyZwavedeviceid(device.getZwavedeviceid())
				: cs.query(device, getChannel(), statuses);
	}
	
	protected int[] mapstatus(int status)
	{
		if ( IRemoteConstantDefine.DEVICE_TYPE_LIST_LIKE_DOOR_LOCK.contains(device.getDevicetype())
				|| IRemoteConstantDefine.DEVICE_TYPE_LOCK_CYLINDER.equals(device.getDevicetype()))
		{
			if ( status != 255 && status != 280)
				return new int[]{0 , 1} ;
		}
		
		return new int[]{status};
	}
}
