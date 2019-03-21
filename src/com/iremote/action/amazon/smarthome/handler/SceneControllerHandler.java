package com.iremote.action.amazon.smarthome.handler;

import com.alibaba.fastjson.JSONObject;
import com.iremote.action.amazon.smarthome.AwsSmartHomeRequestCause;
import com.iremote.action.amazon.smarthome.AwsSmartHomeRequestContext;
import com.iremote.action.amazon.smarthome.AwsSmartHomeRequestEvent;
import com.iremote.action.amazon.smarthome.EventChangeCauseEnum;
import com.iremote.action.scene.LanchSceneAction;
import com.iremote.domain.PhoneUser;

import java.util.Date;

public class SceneControllerHandler extends OperationHandler {
    private static final String ACTIVATE = "Activate";
    private static final String Deactivate = "Deactivate";
    @Override
    public void process(String name, int deviceid, int channelid, JSONObject json, PhoneUser phoneuser) {
        if (ACTIVATE.equals(name)) {
            LanchSceneAction lsa = new LanchSceneAction();
            lsa.setPhoneuser(phoneuser);
            lsa.setScenedbid(deviceid);
            lsa.execute();
        } else if (Deactivate.equals(name)) {
            // 情景暂只支持开启
        }
    }

    @Override
    protected void setHeader(AwsSmartHomeRequestEvent directive) {
//        super.setHeader();
        header.setCorrelationToken(directive.getHeader().getCorrelationToken());
        header.setMessageId(directive.getHeader().getMessageId());
        header.setNamespace("Alexa.SceneController");
        header.setName("ActivationStarted");
    }

    @Override
    protected void setPayload() {
        AwsSmartHomeRequestCause cause = new AwsSmartHomeRequestCause();
        cause.setType(EventChangeCauseEnum.VOICE_INTERACTION);
        payload.setCause(cause);
    }

    @Override
    public AwsSmartHomeRequestContext getContext() {
        return null;
    }
}
