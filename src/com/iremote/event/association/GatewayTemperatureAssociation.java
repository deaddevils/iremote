package com.iremote.event.association;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.NumberUtil;
import com.iremote.common.jms.ITextMessageProcessor;
import com.iremote.domain.Conditions;
import com.iremote.domain.Remote;
import com.iremote.scene.SceneExecutor;
import com.iremote.service.ConditionsService;
import com.iremote.service.RemoteService;
import com.iremote.vo.RemoteVO;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class GatewayTemperatureAssociation  extends RemoteVO implements ITextMessageProcessor {
    @Override
    public String getTaskKey() {
        return getDeviceid();
    }

    @Override
    public void run() {
        List<Conditions> conditionsList = queryConditions();
        if ( conditionsList != null && conditionsList.size() > 0 ){
            for ( Conditions ct : conditionsList )
            {
                if(!checkRemoteCondition(ct)
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

    private boolean checkRemoteCondition(Conditions conditions) {
        if (StringUtils.isBlank(getLasttemperature())) {
            return false;
        }
        return NumberUtil.compare(Double.valueOf(getLasttemperature()), Double.valueOf(conditions.getStatus()), conditions.getOperator());
    }

    private List<Conditions> queryConditions() {
        return new ConditionsService().queryByDeviceid(getDeviceid());
    }
}
