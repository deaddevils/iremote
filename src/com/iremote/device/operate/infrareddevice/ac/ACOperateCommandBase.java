package com.iremote.device.operate.infrareddevice.ac;

import cn.jpush.api.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.iremote.common.Utils;
import com.iremote.device.operate.IDeviceOperateCommand;
import com.iremote.domain.InfraredDevice;
import com.iremote.infraredcode.CodeLiberayHelper;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvByteUnit;
import com.iremote.service.InfraredDeviceService2;
import com.iremote.service.InfreredCodeLiberayService;

public abstract class ACOperateCommandBase implements IDeviceOperateCommand {
    protected InfraredDevice infrareddevice;
    protected byte[] command;
    protected byte value;

    @Override
    public CommandTlv createCommand() {
        CommandTlv ct = new CommandTlv(4, 1);

        createAcCommand();

        if (command == null)
            return null;

        adjustCommand();

        updateDeviceStatus();

		calculatecheckcode();

		ct.addUnit(new TlvByteUnit(40, command));
        return ct;
    }

    protected abstract void adjustCommand();

    private void updateDeviceStatus() {
        int[] ns = new int[7];
        for (int i = 0; i < ns.length; i++)
            ns[i] = command[4 + i];
        infrareddevice.setStatuses(JSON.toJSONString(ns));
    }

	private void calculatecheckcode()
	{
		for ( int i = 0 ; i < command.length - 1  ; i ++ )
			command[command.length -1] += command[i] ;
	}
	
    protected void createAcCommand() {
        int[] cl = infrareddevice.getCodelibery();
        if (cl == null) {
            String s = new InfreredCodeLiberayService().queryByCodeid(Integer.valueOf(infrareddevice.getCodeid()), infrareddevice.getDevicetype());
            if (StringUtils.isNotEmpty(s)) {
                cl = Utils.jsontoIntArray(s);
            }
        }

        command = CodeLiberayHelper.createAcCommandBase(cl);

        String s = infrareddevice.getStatuses();
        if (s == null)
            s = Utils.AC_DEFAULT_STATUSES;

        command[4] = Utils.getJsonArrayValue(s, 0, 25).byteValue();
        command[5] = Utils.getJsonArrayValue(s, 1, 1).byteValue();
        command[6] = Utils.getJsonArrayValue(s, 2, 2).byteValue();
        command[7] = Utils.getJsonArrayValue(s, 3, 1).byteValue();
        command[8] = Utils.getJsonArrayValue(s, 4, 1).byteValue();
        command[9] = Utils.getJsonArrayValue(s, 5, 2).byteValue();
        command[10] = Utils.getJsonArrayValue(s, 6, 1).byteValue();

    }

    public void setValue(byte value) {
        this.value = value;
    }

    public void setInfrareddevice(InfraredDevice infrareddevice) {
        this.infrareddevice = infrareddevice;
    }


}
