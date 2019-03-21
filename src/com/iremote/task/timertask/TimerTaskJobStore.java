package com.iremote.task.timertask;

import com.iremote.task.timertask.processor.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class TimerTaskJobStore {
    private static Map<Integer, Class<? extends Runnable>> clsmap = new HashMap<>();
    private static TimerTaskJobStore instance = new TimerTaskJobStore();

    public static TimerTaskJobStore getInstance() {
        return instance;
    }

    protected TimerTaskJobStore() {
        registProcessor(1, ZwavePartitionDelayArmingProcessor.class);
        registProcessor(2, PhoneuserDelayArmingProceesor.class);
        registProcessor(3, SensorDelayAlarmingProcessor.class);
        registProcessor(4, ChannelDelayAlarmingProcessor.class);
        registProcessor(5, CameraDelayAlarmingProcessor.class);
        registProcessor(6, DSCKeyDelayUnAlarmProcessor.class);
        registProcessor(7, DSCKeyDelayUnAlarmProcessor.class);
        registProcessor(8, DSCKeyDelayUnAlarmProcessor.class);
    }

    public void registProcessor(Integer type, Class<? extends Runnable> cls) {
        if (type == null) {
            return;
        }
        clsmap.put(type, cls);
    }

    public Runnable getProcessor(Integer type, int timerTaskid) {
        if (clsmap.containsKey(type)) {
            return createProcessor(clsmap.get(type), timerTaskid);
        }
        return null;
    }

    private Runnable createProcessor(Class<? extends Runnable> aClass, Integer timerTaskid) {

        if (aClass == null) {
            return null;
        }
        try {
            Constructor<? extends Runnable> constructor = aClass.getConstructor(Integer.class);
            Runnable runnable = constructor.newInstance(timerTaskid);
            return runnable;
        } catch (NoSuchMethodException e) {
            try {
                return aClass.newInstance();
            } catch (InstantiationException e1) {
                e1.printStackTrace();
            } catch (IllegalAccessException e1) {
                e1.printStackTrace();
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
