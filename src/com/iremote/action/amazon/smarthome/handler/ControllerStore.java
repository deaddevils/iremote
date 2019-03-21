package com.iremote.action.amazon.smarthome.handler;

import java.util.HashMap;
import java.util.Map;

public class ControllerStore {
    private static final String ALEXA_PERCENTAGECONTROLLER = "Alexa.PercentageController";
    private static final String ALEXA_POWERCONTROLLER = "Alexa.PowerController";
    private static final String ALEXA_LOCKCONTROLLER = "Alexa.LockController";
    private static final String ALEXA_SCENECONTROLLER = "Alexa.SceneController";
    private static final String ALEXA_COLORCONTROLLER = "Alexa.ColorController";
    private static final String ALEXA_COLORTEMPERATURECONTROLLER = "Alexa.ColorTemperatureController";
    private static final String ALEXA_BRIGHTNESSCONTROLLERHANDLER = "Alexa.BrightnessController";
    private static final String ALEXA_THERMOSTATCONTROLLER = "Alexa.ThermostatController";
    private static final String ALEXA = "Alexa";
    private static final String NAME_PEPORTSTATE = "ReportState";


    private static Map<String, Class<? extends OperationHandler>> map = new HashMap<>();

    static {
        map.put(ALEXA_PERCENTAGECONTROLLER, PercentageControllerHandler.class);
        map.put(ALEXA_POWERCONTROLLER, PowerControllerHandler.class);
        map.put(ALEXA_LOCKCONTROLLER, LockControllerHandler.class);
        map.put(ALEXA_SCENECONTROLLER, SceneControllerHandler.class);
        map.put(NAME_PEPORTSTATE, ReportStateHandler.class);
        map.put(ALEXA_COLORCONTROLLER, ColorControllerHandler.class);
        map.put(ALEXA_COLORTEMPERATURECONTROLLER, ColorTemperatureControllerHandler.class);
        map.put(ALEXA_BRIGHTNESSCONTROLLERHANDLER, BrightnessControllerHandler.class);
        map.put(ALEXA_THERMOSTATCONTROLLER, ThermostatControllerHandler.class);
    }

    public static OperationHandler getHandlerInstance(String namespace, String name) {
        Class<? extends OperationHandler> aClass = null;
        if (ALEXA.equals(namespace)) {
            aClass = map.get(name);
        } else {
            aClass = map.get(namespace);
        }
        if (aClass == null) {
            return null;
        }
        try {
            OperationHandler operationHandler = aClass.newInstance();
            return operationHandler;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

}
