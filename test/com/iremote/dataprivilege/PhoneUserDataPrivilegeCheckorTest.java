package com.iremote.dataprivilege;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.domain.PhoneUser;
import com.iremote.service.PhoneUserService;
import com.iremote.test.db.Db;

public class PhoneUserDataPrivilegeCheckorTest {
    public static void main(String[] args) {
    /*    Db.init();

        PhoneUserService pus = new PhoneUserService();
        PhoneUser pu = pus.query(436);
        PhoneUserDataPrivilegeCheckor pudpc = new PhoneUserDataPrivilegeCheckor(pu);

        Db.commit();
*/
        System.out.println(checkPartitionStatus(1,4));
    }

    private static boolean checkPartitionStatus(Integer armstatus, Integer armstatus2) {
        return armstatus != IRemoteConstantDefine.PARTITION_STATUS_DIS_ARM
                && (armstatus2 == IRemoteConstantDefine.PARTITION_ARM_STATUS_EXIT_DELAY_IN_PROGRESS
                || armstatus2 == IRemoteConstantDefine.PARTITION_ARM_STATUS_ENTRY_DELAY_IN_PROGRESS);
    }
}
