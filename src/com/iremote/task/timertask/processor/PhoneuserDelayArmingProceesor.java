package com.iremote.task.timertask.processor;

import com.alibaba.fastjson.JSONObject;
import com.iremote.action.phoneuser.SetPhoneUserArmStatus;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.TimerTask;
import com.iremote.service.PhoneUserService;
import com.iremote.service.TimerTaskService;

public class PhoneuserDelayArmingProceesor implements Runnable {
    private int phoneuserid;
    private TimerTask timerTask;
    private String msg;
    private Integer armstatus;
    private String password = "";
    private Integer timerTaskId;

    public PhoneuserDelayArmingProceesor(Integer timerTaskId) {
        this.timerTaskId = timerTaskId;
    }

    public PhoneuserDelayArmingProceesor(TimerTask timerTask) {
        this.timerTask = timerTask;
        initTest();
    }

    protected void initTest(){
        TimerTaskService tts = new TimerTaskService();
        timerTask.setDeviceid("fdsafas");
    }

    private boolean init() {
        TimerTaskService tts = new TimerTaskService();
        this.timerTask = tts.query(timerTaskId);

        if (timerTask != null) {
            phoneuserid = timerTask.getObjid();
            tts.delete(timerTask);
            if (timerTask.getJsonpara() != null) {
                JSONObject json = JSONObject.parseObject(timerTask.getJsonpara());
                msg = json.getString("msg");
                armstatus = json.getInteger("todevicestatus");
                if (json.containsKey("password")) {
                    password = json.getString("password");
                }
            }
            return false;
        } else {
            return true;
        }

    }

    @Override
    public void run() {
        if (init()) {
            return;
        }
        //TODO 

        if (armstatus == null) {
            return;
        }
        PhoneUserService phoneUserService = new PhoneUserService();
        PhoneUser phoneuser = phoneUserService.query(phoneuserid);
        SetPhoneUserArmStatus spuas = new SetPhoneUserArmStatus();
        spuas.setArmstatus(armstatus);
        spuas.setPassword(password);
        spuas.setPhoneuser(phoneuser);
        spuas.setSavenotification(true);
        String resultCode = spuas.execute();
    }
}
