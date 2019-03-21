package com.iremote.action.amazon.smarthome.handler;

import com.alibaba.fastjson.JSONObject;
import com.iremote.action.amazon.smarthome.*;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.JSONUtil;
import com.iremote.domain.PhoneUser;

import java.time.Instant;
import java.util.Arrays;
import java.util.Date;

public abstract class OperationHandler {

    protected static final String ENDPOINTID_PATH = "directive.endpoint.endpointId";
    protected static final String SCOPE_TYPE_PATH = "directive.endpoint.scope.type";
    protected static final String SCOPE_TOKEN_PATH = "directive.endpoint.scope.token";
    protected static final String NAME_PATH = "directive.header.name";
    protected Integer resultCode = ErrorCodeDefine.SUCCESS;

    protected AwsSmartHomeRequestContext context = new AwsSmartHomeRequestContext();
    protected AwsSmartHomeRequestEvent event = new AwsSmartHomeRequestEvent();
    protected AwsSmartHomeRequestHeader header = new AwsSmartHomeRequestHeader();
    protected AwsSmartHomeRequestEndpoint endpoint = new AwsSmartHomeRequestEndpoint();
    protected AwsSmartHomeRequestPayload payload = new AwsSmartHomeRequestPayload();
    protected AwsSmartHomeRequestProperty property = new AwsSmartHomeRequestProperty();

    public abstract void process(String name, int zwavedeviceid, int channelid, JSONObject json, PhoneUser phoneuser);

    public void createOperationResponse(JSONObject json, AwsSmartHomeRequestEvent directive) {
        setEvent(json, directive);
        setContext(directive);
    }

    protected void setPayload() {

    }

    protected void setEvent(JSONObject json, AwsSmartHomeRequestEvent directive) {
        setEndPoint(json);
        setHeader(directive);
        setPayload();

        event.setEndpoint(endpoint);
        event.setHeader(header);
        event.setPayload(payload);
    }

    protected void setHeader(AwsSmartHomeRequestEvent directive) {
        header.setMessageId(directive.getHeader().getMessageId());
        header.setNamespace("Alexa");
        header.setName("Response");
        header.setCorrelationToken(directive.getHeader().getCorrelationToken());
    }

    protected void setContext(AwsSmartHomeRequestEvent directive) {
        setProperty(directive);
        context.getProperties().add(property);
    }

    protected void setProperty(AwsSmartHomeRequestEvent directive) {
        property.setName(directive.getHeader().getName());
        property.setNamespace(directive.getHeader().getNamespace());
        property.setTimeOfSample();
        property.setUncertaintyInMilliseconds(0);
    }

    protected void setEndPoint(JSONObject json) {
        String sid = JSONUtil.getString(json, ENDPOINTID_PATH);
        endpoint.setEndpointId(sid);
        endpoint.setScope(new AwsSmartHomeRequestScope(JSONUtil.getString(json, SCOPE_TYPE_PATH), JSONUtil.getString(json, SCOPE_TOKEN_PATH)));
    }

    public AwsSmartHomeRequestEvent getEvent() {
        return event;
    }

    public AwsSmartHomeRequestContext getContext() {
        return context;
    }

    public Integer getResultCode() {
        return resultCode;
    }

}
