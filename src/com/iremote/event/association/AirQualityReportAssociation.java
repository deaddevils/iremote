package com.iremote.event.association;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import com.iremote.domain.Conditions;
import com.iremote.service.ConditionsService;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.TagDefine;
import com.iremote.common.constant.ArithmeticOperator;
import com.iremote.common.jms.ITextMessageProcessor;
import com.iremote.common.jms.vo.ZWaveDeviceStatusChange;
import com.iremote.domain.Associationscene;
import com.iremote.infraredtrans.tlv.TlvWrap;
import com.iremote.scene.SceneExecutor;
import com.iremote.service.AssociationsceneService;

public class AirQualityReportAssociation extends ZWaveDeviceStatusChange implements ITextMessageProcessor
{

	@Override
	public void run()
	{
		Integer ot = TlvWrap.readInteter(getReport(), TagDefine.TAG_OPERATION_TYPE, 4);
		
		if ( ot != null 
				&& ot != IRemoteConstantDefine.OPERATOR_TYPE_APP_USER 
				&& ot != IRemoteConstantDefine.OPERATOR_TYPE_DEVICE_REPORT 
				&& ot != IRemoteConstantDefine.OPERATOR_TYPE_THIRD_PARTER)
			return ;
		
		if ( !IRemoteConstantDefine.DEVICE_TYPE_AIR_QUALITY.equals(super.getDevicetype()))
			return ;

		if ( StringUtils.isBlank( super.getOldstatuses() ) || StringUtils.isBlank(super.getStatuses()) )
			return ;
		
		AssociationsceneService svr = new AssociationsceneService();
		List<Associationscene> lst = svr.querybyZwaveDeviceid(Arrays.asList(new Integer[]{super.getZwavedeviceid()}));
		
		JSONArray os = JSON.parseArray(super.getOldstatuses());
		JSONArray ns = JSON.parseArray(super.getStatuses());
		HashSet<Integer> sceneDbIdSet = new HashSet<>();
		for ( Associationscene as : lst )
		{
			if ( as.getOperator() == null )
				continue;
			ArithmeticOperator ao = ArithmeticOperator.valueOf(as.getOperator());
			if ( ao == null )
				continue ;
			if ( !ao.compare(os.getIntValue(as.getChannelid()), as.getStatus())
					&&  ao.compare(ns.getIntValue(as.getChannelid()), as.getStatus()))
			{
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

		associationConditions(sceneDbIdSet);

	}

	private void associationConditions(HashSet<Integer> sceneDbIdSet) {
		if (!checkStatuses(getStatuses(), getOldstatuses())) {
		List<Conditions> conditionsList = queryConditions();
		if (conditionsList != null && conditionsList.size() > 0 ){
			for ( Conditions ct : conditionsList ){
				if(!SceneExecutor.checkConditionStatus(ct, Float.valueOf(oldstatus), oldstatuses, getDevicetype())
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
	}

	private boolean checkStatuses(String statuses, String oldstatuses) {
		if (IRemoteConstantDefine.DEVICE_TYPE_AIR_QUALITY.equals(getDevicetype())) {
			JSONArray jsonArray = JSONArray.parseArray(statuses);
			jsonArray.remove(0);
			JSONArray jsonArray1 = JSONArray.parseArray(oldstatuses);
			jsonArray1.remove(0);
			return jsonArray.toJSONString().equals(jsonArray1.toJSONString());
		}
		return true;
	}

	private List<Conditions> queryConditions() {
		ConditionsService cs = new ConditionsService();
		return cs.querybyZwavedeviceid(getZwavedeviceid());
	}

	@Override
	public String getTaskKey()
	{
		return super.getDeviceid();
	}

}
