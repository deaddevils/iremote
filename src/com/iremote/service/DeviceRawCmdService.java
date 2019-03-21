package com.iremote.service;

import com.iremote.common.Hibernate.CriteriaWrap;
import com.iremote.common.Hibernate.ExpWrap;
import com.iremote.common.Hibernate.HibernateUtil;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.domain.DeviceRawCmd;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;

import java.util.List;

public class DeviceRawCmdService extends BaseService<DeviceRawCmd> {

    public DeviceRawCmd query(int deviceRawCmdId) {
        CriteriaWrap cw = new CriteriaWrap(DeviceRawCmd.class.getName());
        cw.add(ExpWrap.eq("devicerawcmdid", deviceRawCmdId));
        return cw.uniqueResult();
    }

    /**
     * If cmdindex != 0 && cmdindex != 1 && cmdindex != 2, result may not be unique
     */
    public DeviceRawCmd queryUniqueByCmdindex(int cmdindex, int zwavedeviceid) {
        CriteriaWrap cw = new CriteriaWrap(DeviceRawCmd.class.getName());
        cw.add(ExpWrap.eq("cmdindex", cmdindex));
        cw.add(ExpWrap.eq("zwavedeviceid", zwavedeviceid));
        return cw.uniqueResult();
    }

    public List<DeviceRawCmd> queryByZwaveDeviceId(int zwavedeviceid) {
        CriteriaWrap cw = new CriteriaWrap(DeviceRawCmd.class.getName());
        cw.add(ExpWrap.eq("zwavedeviceid", zwavedeviceid));
        return cw.list();
    }

    public void deleteByZwaveDeviceId(int zwavedeviceid) {
        String sql = "delete from devicerawcmd where zwavedeviceid = :zwavedeviceid";
        SQLQuery query = HibernateUtil.getSession().createSQLQuery(sql);
        query.setInteger("zwavedeviceid", zwavedeviceid);

        query.executeUpdate();
    }

    public DeviceRawCmd queryUniquePowerCmdByCmdIndex(int cmdindex, int zwavedeviceid) {
        DeviceRawCmd cmd = queryUniqueByCmdindex(cmdindex, zwavedeviceid);
        if (cmd != null && StringUtils.isNotBlank(cmd.getRawcmd())){
            return cmd;
        }
        cmd = queryUniqueByCmdindex(IRemoteConstantDefine.PASS_THROUGH_DEVICE_CMD_INDEX_POWER_ON_AND_OFF, zwavedeviceid);
        return cmd;
    }

    public void deleteByCmdIndex(int cmdindex, int zwavedeviceid) {
        String sql = "delete from devicerawcmd where zwavedeviceid = :zwavedeviceid and cmdindex = :cmdindex";
        SQLQuery query = HibernateUtil.getSession().createSQLQuery(sql);
        query.setInteger("zwavedeviceid", zwavedeviceid);
        query.setInteger("cmdindex", cmdindex);

        query.executeUpdate();
    }
}