package com.iremote.infraredtrans.serianet.dsc;

import com.alibaba.fastjson.JSONObject;
import com.iremote.action.helper.PushEventToThirdpartHelper;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.jms.JMSUtil;
import com.iremote.domain.EventtoThirdpart;
import com.iremote.domain.Notification;
import com.iremote.domain.Partition;
import com.iremote.domain.ZWaveDevice;
import com.iremote.service.ZWaveDeviceService;
import com.iremote.thirdpart.common.ThirdPartHelper;
import com.iremote.thirdpart.wcj.service.EventtoThirdpartService;

import java.util.Date;
import java.util.List;

public class DSCUserClosingProcessor extends DSCReportBaseProcessor {
    ZWaveDevice zd;
    Partition partition;
    int dscpartitionid;
    int usercode;
    String partitionname;

    @Override
    protected void updateDeviceStatus() {
    }

    @Override
    protected void pushMessage() {
        if (zd == null) {
            return;
        }

        PushEventToThirdpartHelper.pushEventToThirdpart(seriaNetReportBean.getDeviceid(),
                zwavedeviceid, partition.getPartitionid(), partition.getDscpartitionid(), partition.getArmstatus(), usercode, IRemoteConstantDefine.MESSAGE_PARTITION_ARM_USER_CODE);
    }

    @Override
    protected void writeLog() {
        if (zd == null) {
            return;
        }

        JSONObject json = new JSONObject();
        json.put("partitionname", partitionname);

        Notification notification = new Notification();
        notification.setDeviceid(seriaNetReportBean.getDeviceid());
        notification.setReporttime(new Date());
        notification.setZwavedeviceid(zd.getZwavedeviceid());
        notification.setNuid(zd.getNuid());
        notification.setName(zd.getName());
        notification.setMajortype(zd.getMajortype());
//        notification.setApplianceid(zd.getApplianceid());
        notification.setMessage(IRemoteConstantDefine.MESSAGE_PARTITION_ARM_USER_CODE);
        notification.setDevicetype(zd.getDevicetype());
        notification.setPhoneuserid(getPhoneUser().getPhoneuserid());
        notification.setAppendmessage(String.valueOf(usercode));
        notification.setAppendjson(json);
        JMSUtil.sendmessage(IRemoteConstantDefine.NOTIFICATION_REPORT_SHARE, notification);
    }

    @Override
    protected void init() {
        dscpartitionid = getValue(seriaNetReportBean.getCmd(), 3, 4);
        usercode = getValue(seriaNetReportBean.getCmd(), 4, 8);
        ZWaveDeviceService zds = new ZWaveDeviceService();
        List<ZWaveDevice> zdList = zds.querybydeviceidtype(seriaNetReportBean.getDeviceid(), IRemoteConstantDefine.DEVICE_TYPE_DSC);
        if (zdList == null || zdList.size() == 0) {
            return;
        }
        zd = zdList.get(0);

        for (int i = 0;zd.getPartitions()!= null && i < zd.getPartitions().size(); i++) {
            if (zd.getPartitions().get(i).getDscpartitionid()== dscpartitionid){
                partition = zd.getPartitions().get(i);
                partitionname = zd.getPartitions().get(i).getName();
                break;
            }
        }
    }
}
