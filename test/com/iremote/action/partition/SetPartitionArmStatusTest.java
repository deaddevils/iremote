package com.iremote.action.partition;

import com.iremote.domain.PhoneUser;
import com.iremote.domain.ZWaveDevice;
import com.iremote.service.PhoneUserService;
import com.iremote.service.ZWaveDeviceService;
import com.iremote.test.db.Db;

import java.util.List;

public class SetPartitionArmStatusTest {
    public static void main(String[] args) {
        Db.init();

        SetPartitionArmStatus spas = new SetPartitionArmStatus();
        PhoneUserService pus = new PhoneUserService();
        PhoneUser query = pus.query(6);
        spas.setPhoneuser(query);
        spas.setArmstatus(1);
        spas.setPartitionid(342);
        spas.setPassword("1234");
        spas.execute();

        Db.commit();
    }
}
