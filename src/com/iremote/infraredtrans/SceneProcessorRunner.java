package com.iremote.infraredtrans;

import com.iremote.action.phoneuser.QuerySunriseOrSunsetTimeHelper;
import com.iremote.action.scene.SceneHelper;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.TimerUtils;
import com.iremote.common.schedule.ScheduleManager;
import com.iremote.domain.Address;
import com.iremote.domain.Timer;
import com.iremote.scene.TimerExecutor;
import com.iremote.service.AddressService;
import com.iremote.service.RemoteService;
import com.iremote.service.TimerService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Calendar;
import java.util.List;

public class SceneProcessorRunner implements Runnable {
    private static Log log = LogFactory.getLog(SceneProcessorRunner.class);

    private long taskIndentify;
    private Timer timer;
    private boolean isAddScene;
    private boolean managing = false;
    private static int[] WEEKDAY_LIST = new int[]{0, 64, 32, 16, 8, 4, 2, 1};


    public SceneProcessorRunner(long taskIndentify, Timer timer, boolean isAddScene) {
        this.taskIndentify = taskIndentify;
        this.timer = timer;
        this.isAddScene = isAddScene;
    }

    public SceneProcessorRunner(long taskIndentify, boolean managing) {
        this.taskIndentify = taskIndentify;
        this.managing = managing;
    }

    @Override
    public void run() {
        if (!managing) {
            if (isAddScene) {
                addSunScene();
            } else {
                cancelSunScene();
            }
        } else {
            manageSunScene();
        }
    }

    private void manageSunScene() {
        TimerService timerService = new TimerService();
        List<Timer> timers = timerService.querySunScene();
        for (Timer timer1 : timers) {
            timer = timer1;
            addSunScene();
        }
    }

    private void addSunScene() {
        if (!checkWeekday()){
            log.info("by the scene weekday setting, skip today. timerId: "+timer.getTimerid());
            return;
        }

        long endTimeOfToday = TimerUtils.getEndTimeOfToday();
        Long suntime = getSunTime();
        if (suntime == null) {
            return;
        }

        long timeOfADayMillis = 1 * 24 * 60 * 60 * 1000L;
        if (timer.getTimetype() == IRemoteConstantDefine.SCENE_TIME_TYPE_SUNRISE && suntime <= System.currentTimeMillis()) {
            suntime += timeOfADayMillis;
        } else if (timer.getTimetype() == IRemoteConstantDefine.SCENE_TIME_TYPE_SUNSET && suntime >= endTimeOfToday) {
            suntime -= timeOfADayMillis;
        }

        if (suntime >= System.currentTimeMillis() && suntime <= endTimeOfToday) {
            int second = (int) ((suntime - System.currentTimeMillis()) / 1000);
            ScheduleManager.excutein(second, new TimerExecutor(timer.getTimerid()), timer.getTimerid(), IRemoteConstantDefine.QUARTZ_GROUP_DEVICE_TIMER);
        }
    }

    private boolean checkWeekday() {
        int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        int weekday = timer.getWeekday();

        return (weekday & WEEKDAY_LIST[day]) != 0;
    }

    private Long getSunTime() {
        if (timer.getScene() == null) {
            return null;
        }

        Integer phoneuserid = SceneHelper.getSceneOwnerByTimer(timer);

        if (phoneuserid == null) {
            return null;
        }

        Address address = new AddressService().queryByPhoneuserid(phoneuserid);
        if (address == null || address.getCityid() == null) {
            return null;
        }

        long time = QuerySunriseOrSunsetTimeHelper.querySunriseOrSunsetTime(address.getCityid(), timer.getTimetype());

        return time == 0 ? null : time;
    }

    private void cancelSunScene() {
        ScheduleManager.cancelJob(timer.getTimerid(), IRemoteConstantDefine.QUARTZ_GROUP_DEVICE_TIMER);
    }

    public long getTaskIndentify() {
        return taskIndentify;
    }
}
