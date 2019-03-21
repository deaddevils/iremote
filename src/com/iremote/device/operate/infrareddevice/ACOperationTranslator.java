package com.iremote.device.operate.infrareddevice;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iremote.common.Utils;
import com.iremote.device.operate.OperationTranslatorBase;
import com.iremote.infraredcode.CodeLiberayHelper;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvByteUnit;
import com.iremote.service.InfraredDeviceService2;
import com.iremote.service.InfreredCodeLiberayService;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;

public class ACOperationTranslator extends OperationTranslatorBase {
    private static Log log = LogFactory.getLog(ACOperationTranslator.class);

    private static final int[][] POWER_TRANSLATE = new int[][]{{0, 5}, {0, 0, 0, 0, 0, 1}};
    private static final int[][] MODE_TRANSLATE = new int[][]{{0, 3, 2, 8, 6, 1}, {0, 5, 2, 1, 0, 0, 4, 0, 3}};
    private static final int[][] FAN_TRANSLATE = new int[][]{{0, 0, 1, 5, 3}, {1, 2, 0, 4, 0, 3}};
    private Integer power;
    private Integer temperature;
    private Integer mode;
    private Integer winddirection;
    private Integer fan;
    private Integer autodirection;

    @Override
    public String getDeviceStatus() {
        return null;
    }

    @Override
    public String getCommandjson() {
        try {
            if (StringUtils.isBlank(this.commandjson) && this.command != null && this.command.length > 5) {
                Utils.print("", this.command);
                int baseindex = 0;
                if (this.command[0] == 4 && this.command[1] == 1)
                    baseindex = 4;
                if (this.command[4] == 0 && this.command[5] == 40)
                    baseindex = 8;
                if (this.command.length <= baseindex + 10)
                    return null;
                this.temperature = command[baseindex + 4] & 0xff;
                this.fan = FAN_TRANSLATE[0][command[baseindex + 5] & 0xff];
                this.winddirection = command[baseindex + 6] & 0xff;
                this.autodirection = command[baseindex + 7] & 0xff;
                this.power = POWER_TRANSLATE[0][command[baseindex + 8] & 0xff];
                this.mode = MODE_TRANSLATE[0][command[baseindex + 10] & 0xff];

                JSONObject json = new JSONObject();
                json.put("temperature", this.temperature);
                json.put("fan", this.fan);
                json.put("winddirection", this.winddirection);
                json.put("autodirection", this.autodirection);
                json.put("power", this.power);
                json.put("mode", this.mode);

                if (json != null) {
                    JSONArray jsonarray = new JSONArray();
                    jsonarray.add(json);
                    this.commandjson = jsonarray.toJSONString();
                }
            }
        } catch (Throwable t) {
            log.error(t.getMessage(), t);
        }
        return this.commandjson;
    }

    private CommandTlv createAcCommand(int[] liberay)
    {
    	byte[] command = CodeLiberayHelper.createAcCommandBase(liberay);

        command[4] = this.temperature != null ? this.temperature.byteValue() : 25;
        command[5] = this.fan != null ? (byte) FAN_TRANSLATE[1][this.fan] : (byte) FAN_TRANSLATE[1][1];
        command[6] = this.winddirection != null ? this.winddirection.byteValue() : 2;
        if ( command[6] == 0 )
        	command[6] = 2 ;
        command[7] = this.autodirection != null ? this.autodirection.byteValue() : 1;
        command[8] = (byte) POWER_TRANSLATE[1][this.power];
        command[9] = 1;
        command[10] = this.mode != null ? (byte) MODE_TRANSLATE[1][this.mode] : (byte) MODE_TRANSLATE[1][2];

        for (int i = 0; i < command.length - 1; i++)
            command[command.length - 1] += command[i];

        CommandTlv ct = new CommandTlv(4, 1);
        ct.addUnit(new TlvByteUnit(40, command));

        return ct;
    }

    @Override
    public Integer getValue() {
        return null;
    }

    @Override
    public String getStatus() {
        return null;
    }

    @Override
    public List<CommandTlv> getCommandTlv() {
        if (this.commandtlvlst != null)
            return commandtlvlst;

        if (this.command == null && infrareddevice != null) {
            if (power == null && StringUtils.isNotBlank(this.commandjson)) {
                JSONArray jsonarray = parseJSONArray(this.commandjson);
                for (int i = 0; i < jsonarray.size(); i++) {
                    JSONObject json = jsonarray.getJSONObject(i);
                    this.power = json.getInteger("power");
                    this.temperature = json.getInteger("temperature");
                    this.fan = json.getInteger("fan");
                    this.winddirection = json.getInteger("winddirection");
                    this.autodirection = json.getInteger("autodirection");
                    this.mode = json.getInteger("mode");
                }
            }
            if (power != null) {
                int[] codelibery = infrareddevice.getCodelibery();
                if (codelibery == null) {
                    String s = new InfreredCodeLiberayService().queryByCodeid(Integer.valueOf(infrareddevice.getCodeid()), infrareddevice.getDevicetype());
                    if (StringUtils.isNotBlank(s)) {
                        codelibery = Utils.jsontoIntArray(s);
                    }
                }
                CommandTlv ct = this.createAcCommand(codelibery);
                if (ct != null) {
                    commandtlvlst = new ArrayList<CommandTlv>();
                    commandtlvlst.add(ct);
                }
            }
        }
        return commandtlvlst;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public void setWinddirection(int winddirection) {
        this.winddirection = winddirection;
    }

    public void setFan(int fan) {
        this.fan = fan;
    }

    public void setAutodirection(int autodirection) {
        this.autodirection = autodirection;
    }


}
