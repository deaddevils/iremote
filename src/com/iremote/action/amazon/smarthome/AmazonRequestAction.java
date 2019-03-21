package com.iremote.action.amazon.smarthome;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.iremote.action.amazon.smarthome.handler.ControllerStore;
import com.iremote.action.amazon.smarthome.handler.OperationHandler;
import com.iremote.action.amazon.smarthome.handler.ThermostatControllerHandler;
import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.action.helper.RequestHelper;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.JSONUtil;
import com.iremote.domain.*;
import com.iremote.service.*;
import com.opensymphony.xwork2.Action;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

public class AmazonRequestAction {
    private static Log log = LogFactory.getLog(AmazonRequestAction.class);
    private static final String NAMESPACE_PATH = "directive.header.namespace";
    private static final String NAME_PATH = "directive.header.name";
    private static final String TOKEN_PATH_1 = "directive.payload.scope.token";
    private static final String TOKEN_PATH_2 = "directive.endpoint.scope.token";
    private static final String ENDPOINTID_PATH = "directive.endpoint.endpointId";

    private static final String DISCOVERY = "Alexa.Discovery";
    private static final Map<String, String[]> DEVICE_ENDPOINTMAP = new HashMap<String, String[]>();

    static {
        DEVICE_ENDPOINTMAP.put("5", new String[]{"SMARTLOCK", "Alexa.LockController", "lockState"});
        DEVICE_ENDPOINTMAP.put("7", new String[]{"LIGHT", "Alexa.PowerController", "powerState"});
        DEVICE_ENDPOINTMAP.put("8", new String[]{"LIGHT", "Alexa.PowerController", "powerState"});
        DEVICE_ENDPOINTMAP.put("9", new String[]{"LIGHT", "Alexa.PowerController", "powerState"});
        DEVICE_ENDPOINTMAP.put("11", new String[]{"SMARTPLUG", "Alexa.PowerController", "powerState"});
        DEVICE_ENDPOINTMAP.put("12", new String[]{"SWITCH", "Alexa.PowerController", "powerState"});
        DEVICE_ENDPOINTMAP.put("13", new String[]{"SWITCH", "Alexa.PercentageController", "percentage"});
        DEVICE_ENDPOINTMAP.put("14", new String[]{"THERMOSTAT", "Alexa.PowerController", "powerState"});
        DEVICE_ENDPOINTMAP.put("20", new String[]{"LIGHT", "Alexa.PowerController", "powerState"});
        DEVICE_ENDPOINTMAP.put("46", new String[]{"LIGHT", "Alexa.ColorController", "color"});
        DEVICE_ENDPOINTMAP.put("999", new String[]{"SCENE_TRIGGER", "Alexa.SceneController", "powerState"});
    }

    private int resultCode;
    private PhoneUser phoneuser;
    private String namespace;
    private String usertoken;
    private AwsSmartHomeRequestEvent event = null;
    private AwsSmartHomeRequestContext context = null;
    private AwsSmartHomeRequestEvent directive;
    private AwsSmartHomeResponse response = new AwsSmartHomeResponse();

    public String execute() {
        String str = RequestHelper.readParameter();
        log.info(str);
        return execute(str);
    }

    public String execute(String str) {
        JSONObject json = JSONObject.parseObject(str);
        directive = JSON.parseObject(json.getString("directive"), AwsSmartHomeRequestEvent.class);
        initPhoneuser(json);

        if (phoneuser == null)
            return Action.SUCCESS;

        namespace = JSONUtil.getString(json, NAMESPACE_PATH);

        if (DISCOVERY.equals(namespace)) {
            processDiscover(json);
        } else {
            processOperation(json);
        }

        response.setEvent(event);
        response.setContext(context);
        log.info("response: " + JSON.toJSONString(response));
        return Action.SUCCESS;
    }

    private void processOperation(JSONObject json) {
        String sid = JSONUtil.getString(json, ENDPOINTID_PATH);
        if (StringUtils.isBlank(sid))
            return;

        String[] said = sid.split("_");
        if (said == null || said.length != 3)
            return;

        int zwavedeviceid = Integer.valueOf(said[1]);
        int channelid = Integer.valueOf(said[2]);
        String name = JSONUtil.getString(json, NAME_PATH);
        OperationHandler handler = ControllerStore.getHandlerInstance(namespace, name);
        if (handler != null) {
            handler.process(name, zwavedeviceid, channelid, json, phoneuser);
            handler.createOperationResponse(json, directive);
            event = handler.getEvent();
            context = handler.getContext();
        }
        resultCode = handler.getResultCode();
    }

    protected void initPhoneuser(JSONObject json) {
        usertoken = JSONUtil.getString(json, TOKEN_PATH_1);
        if (StringUtils.isBlank(usertoken))
            usertoken = JSONUtil.getString(json, TOKEN_PATH_2);
        if (StringUtils.isBlank(usertoken))
            return;
        UserTokenService uts = new UserTokenService();
        UserToken ut = uts.querybytoken(usertoken);

        if (ut == null)
            return;

        PhoneUserService pus = new PhoneUserService();
        phoneuser = pus.query(ut.getPhoneuserid());
    }

    private void processDiscover(JSONObject json) {
        event = new AwsSmartHomeRequestEvent();
        AwsSmartHomeRequestHeader header = new AwsSmartHomeRequestHeader();
        header.setMessageId(this.directive.getHeader().getMessageId());
        header.setNamespace("Alexa.Discovery");
        header.setName("Discover.Response");
        event.setHeader(header);

        List<ZWaveDevice> lst = queryZWaveDevice();
        for (ZWaveDevice zd : lst) {
            event.getPayload().getEndpoints().addAll(createEndpoint(zd));
        }
        List<Scene> scenes = queryScene();
        for (Scene s : scenes) {
            event.getPayload().getEndpoints().addAll(createEndpoint(s));
        }
    }

    private List<AwsSmartHomeRequestEndpoint> createEndpoint(Scene s) {
        List<AwsSmartHomeRequestEndpoint> lst = new ArrayList<>();
        String[] strings = DEVICE_ENDPOINTMAP.get("999");
        AwsSmartHomeRequestEndpoint ep = new AwsSmartHomeRequestEndpoint();
        ep.setFriendlyName(s.getName());
        ep.setEndpointId(String.format("scene_%d_%d", s.getScenedbid(), s.getScenetype()));
        ep.setDisplayCategories(new String[]{strings[0]});
        ep.setManufacturerName(getPlatformName());

        AwsSmartHomeRequestCapability c = new AwsSmartHomeRequestCapability();
        c.setType("AlexaInterface");
        c.setInterface(strings[1]);
        c.setSupportsDeactivation(false);
//        c.setProactivelyReported(true);
//        c.setRetrievable(true);
        c.setProperties(new AwsSmartHomeRequestProperty(Arrays.asList(new AwsSmartHomeRequestSupport(strings[2])), true));
        ep.getCapabilities().add(c);

        lst.add(ep);
        return lst;
    }

    private List<AwsSmartHomeRequestEndpoint> createEndpoint(ZWaveDevice zd) {
        List<AwsSmartHomeRequestEndpoint> lst = new ArrayList<AwsSmartHomeRequestEndpoint>();

        if (zd.getzWaveSubDevices() == null || zd.getzWaveSubDevices().size() == 0) {
            AwsSmartHomeRequestEndpoint e = createEndpoint(zd, zd.getName(), 0);
            if (e != null)
                lst.add(e);
        } else {
            for (ZWaveSubDevice zs : zd.getzWaveSubDevices()) {
                AwsSmartHomeRequestEndpoint e = createEndpoint(zd, zs.getName(), zs.getChannelid());
                if (e != null)
                    lst.add(e);
            }
        }
        return lst;
    }

    private AwsSmartHomeRequestEndpoint createEndpoint(ZWaveDevice zd, String name, int channel) {
        if (!DEVICE_ENDPOINTMAP.containsKey(zd.getDevicetype()))
            return null;

        String[] st = DEVICE_ENDPOINTMAP.get(zd.getDevicetype());

        AwsSmartHomeRequestEndpoint ep = new AwsSmartHomeRequestEndpoint();

        ep.setFriendlyName(name);
        ep.setEndpointId(String.format("zwave_%d_%d", zd.getZwavedeviceid(), channel));
        ep.setDisplayCategories(new String[]{st[0]});
        ep.setManufacturerName(getPlatformName());

        AwsSmartHomeRequestCapability c = new AwsSmartHomeRequestCapability();
        c.setType("AlexaInterface");
        c.setInterface(st[1]);

        c.setProperties(new AwsSmartHomeRequestProperty(Arrays.asList(new AwsSmartHomeRequestSupport(st[2])), true, true));
        ep.getCapabilities().add(c);

        appendCapability(zd, ep);
        return ep;
    }

    private void appendCapability(ZWaveDevice zd, AwsSmartHomeRequestEndpoint ep) {
        if (IRemoteConstantDefine.DEVICE_TYPE_CURTAIN.equals(zd.getDevicetype())
                || IRemoteConstantDefine.DEVICE_TYPE_RGB_COLOR_SWITCH.equals(zd.getDevicetype())) {
            AwsSmartHomeRequestCapability capability = new AwsSmartHomeRequestCapability();
            capability.setType("AlexaInterface");
            capability.setInterface("Alexa.PowerController");
            capability.setProperties(new AwsSmartHomeRequestProperty(Arrays.asList(new AwsSmartHomeRequestSupport("powerState")), true, true));
            ep.getCapabilities().add(capability);
        }

        if (IRemoteConstantDefine.DEVICE_TYPE_DOOR_LOCK.equals(zd.getDevicetype())) {
            AwsSmartHomeRequestCapability capability = new AwsSmartHomeRequestCapability();
            capability.setType("AlexaInterface");
            capability.setInterface("Alexa.EndpointHealth");
            capability.setProperties(new AwsSmartHomeRequestProperty(Arrays.asList(new AwsSmartHomeRequestSupport("connectivity")), true, true));
            ep.getCapabilities().add(capability);
        }

        if (IRemoteConstantDefine.DEVICE_TYPE_RGB_COLOR_SWITCH.equals(zd.getDevicetype())
                || IRemoteConstantDefine.DEVICE_TYPE_DIMMER.equals(zd.getDevicetype())) {
            AwsSmartHomeRequestCapability capability = new AwsSmartHomeRequestCapability();
            capability.setType("AlexaInterface");
            capability.setInterface("Alexa.BrightnessController");
            capability.setProperties(new AwsSmartHomeRequestProperty(Arrays.asList(new AwsSmartHomeRequestSupport("brightness")), true, true));
            ep.getCapabilities().add(capability);
        }

        if (IRemoteConstantDefine.DEVICE_TYPE_AIR_CONDITIONER.equals(zd.getDevicetype())) {
            AwsSmartHomeRequestCapability capability = new AwsSmartHomeRequestCapability();
            capability.setType("AlexaInterface");
            capability.setInterface("Alexa.ThermostatController");

            AwsSmartHomeRequestProperty property = new AwsSmartHomeRequestProperty();
            property.getSupported().add(new AwsSmartHomeRequestSupport("targetSetpoint"));
            property.getSupported().add(new AwsSmartHomeRequestSupport("thermostatMode"));
            property.setProactivelyReported(true);
            property.setRetrievable(true);
            capability.setProperties(property);

            AwsSmartHomeRequestConfiguration configuration = new AwsSmartHomeRequestConfiguration();
            configuration.setSupportsScheduling(false);
            configuration.getSupportedModes().add(ThermostatControllerHandler.MODE[6]);
            configuration.getSupportedModes().add(ThermostatControllerHandler.MODE[1]);
            configuration.getSupportedModes().add(ThermostatControllerHandler.MODE[2]);
            configuration.getSupportedModes().add(ThermostatControllerHandler.MODE[3]);
            configuration.getSupportedModes().add(ThermostatControllerHandler.MODE[4]);
            capability.setConfiguration(configuration);

            ep.getCapabilities().add(capability);
        }
    }

    private List<Scene> queryScene() {
        List<Integer> pidl = getPhoneuseridList();
        SceneService ss = new SceneService();
        List<Scene> scenes = ss.queryScenebyPhoneuserid(pidl);
        return scenes;
    }

    private List<ZWaveDevice> queryZWaveDevice() {
        List<Integer> pidl = getPhoneuseridList();

        RemoteService rs = new RemoteService();
        List<String> didl = rs.queryDeviceidbyPhoneUserid(pidl);

        ZWaveDeviceService zds = new ZWaveDeviceService();
        return zds.querybydeviceid(didl);
    }

    private List<Integer> getPhoneuseridList() {
        List<Integer> pidl;
        if (phoneuser.getFamilyid() != null && phoneuser.getFamilyid() != 0)
            pidl = PhoneUserHelper.queryPhoneuseridbyfamilyid(phoneuser.getFamilyid());
        else {
            pidl = new ArrayList<Integer>(1);
            pidl.add(phoneuser.getPhoneuserid());
        }
        return pidl;
    }

    private String getPlatformName() {
        String name = "";
        if (phoneuser == null) {
            return name;
        }
        OemProductorService service = new OemProductorService();
        OemProductor productor = service.querybyplatform(phoneuser.getPlatform());
        if (productor == null) {
            return name;
        }
        return productor.getName();
    }

  /*  public AwsSmartHomeResponse getResponse() {
        return response;
    }*/

    public AwsSmartHomeRequestEvent getEvent() {
        return event;
    }

    public AwsSmartHomeRequestContext getContext() {
        return context;
    }

    public void setEvent(AwsSmartHomeRequestEvent event) {
        this.event = event;
    }

    public int getResultCode() {
        return resultCode;
    }
}
