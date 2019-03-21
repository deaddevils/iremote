package com.iremote.task.timertask;

import com.iremote.action.partition.SetPartitionArmStatus;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.ZWaveDevice;
import com.iremote.infraredtrans.zwavecommand.DoorSensorReportProcessor;
import com.iremote.infraredtrans.zwavecommand.ZWaveReportBaseProcessor;
import com.iremote.infraredtrans.zwavecommand.ZwaveReportBean;
import com.iremote.service.PhoneUserService;
import com.iremote.service.ZWaveDeviceService;
import com.iremote.test.db.Db;
import org.quartz.SchedulerException;

public class TimerTaskTest {
    public static void main(String[] args) throws SchedulerException {
        Db.init();
 /*      TimerTaskService tts = new TimerTaskService();
//        List<TimerTask> timerTasks = tts.queryAll();
        TimerTask query = tts.query(1);
        tts.delete(query);
        System.out.println(query.getType());*/
//        tts.delete(timerTasks.get(0));
//        System.out.println(timerTasks);
      PhoneUserService pus = new PhoneUserService();
        PhoneUser phoneuser = pus.query(6);
        ZWaveDeviceService zds = new ZWaveDeviceService();
        ZWaveDevice zd = zds.query(5);
        ZwaveReportBean zrb = new ZwaveReportBean();
//        zrb.set
        ZWaveReportBaseProcessor pro = new DoorSensorReportProcessor();
        pro.setNoSessionZwaveDevice(zd);
//        pro.setReport();
        partitionArmStatus(phoneuser);
//        start();
//        query();

        Db.commit();

    }

    private static void partitionArmStatus(PhoneUser phoneuser) {
        SetPartitionArmStatus spas = new SetPartitionArmStatus();
        spas.setPartitionid(342);
        spas.setPassword("");
        spas.setArmstatus(1);
        spas.setPhoneuser(phoneuser);
        spas.execute();
    }

    private static void query() {

//        TimerTaskService tts = new TimerTaskService();
//        List<timertask> query = tts.queryAll();
//        System.out.println(query);
        ZWaveDeviceService zds = new ZWaveDeviceService();
        ZWaveDevice query = zds.query(390);
//        Partition partition = new Partition();
//        partition.setName("Par 1");
//        query.setPartition(partition);
//        query.setPartition(null);
        zds.update(query);
       /* PartitionService ps = new PartitionService();
        Partition p = ps.query(49);
        System.out.println(p.getZwavedevice().getDevicetype());
        System.out.println(p.getPhoneuser().getName());*/
    }

    private static void start() throws SchedulerException {
       /* Trigger trigger = newTrigger().withIdentity("trigger1", "group1") //定义name/group
//                .startNow()//一旦加入scheduler，立即生效
                .startAt(new Date(System.currentTimeMillis() + 5000))
                .withSchedule(simpleSchedule()) //使用SimpleTrigger
//                        .withIntervalInSeconds(1)
//                .withRepeatCount(0)) //每隔一秒执行一次

//                        .repeatForever()) //一直执行，奔腾到老不停歇
                .build();
        JobDetail job = newJob(ZwavePatitionDelayArmingProcessor.class) //定义Job类为HelloQuartz类，这是真正的执行逻辑所在
                .withIdentity("job1", "group1") //定义name/group
                .usingJobData("name", 1) //定义属性
                .build();

        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.scheduleJob(job, trigger);
        scheduler.start();*/
    }

}
