package com.iremote.action.infraredcode2;

import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.domain.InfraredDevice;
import com.iremote.infraredcode.CodeLiberayHelper;
import com.iremote.infraredtrans.SynchronizeRequestHelper;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvByteUnit;
import com.iremote.infraredtrans.tlv.TlvWrap;
import com.iremote.service.InfraredDeviceService;
import com.iremote.service.InfreredCodeLiberayService;
import com.opensymphony.xwork2.Action;
import org.apache.commons.lang3.StringUtils;

public class ACOperateAction {

    protected int resultCode = ErrorCodeDefine.SUCCESS;
    private Integer infrareddeviceid;
    private String deviceid ;
    private Integer codeid;
	private int power;
    private int temperature;
    private int mode;
    private int winddirection = 2 ;
    private int fan = 1;
    private int autodirection = 1;
    private int curoperation = 1;

    private int[] liberay ;

    public String execute() 
    {
    	if ( init() == false )
    		return Action.SUCCESS;

        byte[] command = createAcCommand(liberay);

        CommandTlv ct = new CommandTlv(4, 1);
        ct.addUnit(new TlvByteUnit(40, command));

        byte[] rst = SynchronizeRequestHelper.synchronizeRequest(deviceid, ct, IRemoteConstantDefine.DEFAULT_TIME_OUT_SECOND);

        if (rst == null) {
            resultCode = ErrorCodeDefine.TIME_OUT;
        } else {
            resultCode = TlvWrap.readInt(rst, 1, TlvWrap.TAGLENGTH_LENGTH);
            if (resultCode == Integer.MIN_VALUE)
                resultCode = ErrorCodeDefine.TIME_OUT;
        }
        return Action.SUCCESS;
    }
    
    private boolean init()
    {
	    if ( this.codeid != null )  // codeid is valid , query id from db .
		{
	    	InfreredCodeLiberayService cls = new InfreredCodeLiberayService();
	        String str = cls.queryByCodeid(codeid, IRemoteConstantDefine.DEVICE_TYPE_AC);

	        if (StringUtils.isNotBlank(str))
	            liberay = Utils.jsontoIntArray(str);
	        else
	        {
	        	this.resultCode = ErrorCodeDefine.INFRARED_CODE_LIBRARY_NOT_EXISTS;
	        	return false ;
	        }
		}
    	
    	if ( StringUtils.isNotBlank(deviceid) && this.liberay != null )  // deviceid and codeid is valid , ignore infrareddeviceid .
    	{
              return true;
    	}
    	else if ( this.infrareddeviceid != null )   //deviceid or codeid or both are null , infrareddeviceid is valid , ignore deviceid
    	{
    		InfraredDeviceService svr = new InfraredDeviceService();
	        InfraredDevice id = svr.query(infrareddeviceid);
	        if (id == null) 
	        {
	            this.resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT;
	            return false;
	        }
	        this.deviceid = id.getDeviceid();  // ignore deviceid from APP.
	        
	        if ( this.liberay != null )  // codeid is valid , ignore code attributes of the infrared device.
	        	return true;
	        
	        if ( id.getCodelibery() != null ) // use code library setting of the device
	        {
	        	this.liberay = id.getCodelibery();
	        	return true;
	        }
	        else  // use codeid setting of the device
	        {
	        	InfreredCodeLiberayService cls = new InfreredCodeLiberayService();
	        	String s = cls.queryByCodeid(Integer.valueOf(id.getCodeid()),id.getDevicetype());
                if (StringUtils.isNotBlank(s)) 
                {
                    liberay = Utils.jsontoIntArray(s);
                    return true;
                }
                else
                {
                	this.resultCode = ErrorCodeDefine.INFRARED_CODE_LIBRARY_NOT_EXISTS;
                	return false ;
                }
	        }
    	}
    	
    	this.resultCode = ErrorCodeDefine.PARMETER_ERROR;
    	return false ;
    }

    protected byte[] createAcCommand(int[] liberay)
    {
        byte[] command = CodeLiberayHelper.createAcCommandBase(liberay);

        initValue();

        command[4] = (byte) this.temperature;
        command[5] = (byte) this.fan;
        command[6] = (byte) this.winddirection;
        command[7] = (byte) this.autodirection;
        command[8] = (byte) this.power;
        command[9] = (byte) this.curoperation;
        command[10] = (byte) this.mode;

        for (int i = 0; i < command.length - 1; i++)
            command[command.length - 1] += command[i];

        return command;
    }

    private void initValue() {
        if (this.power == 5) {
            this.power = 1;
        }

        switch (this.mode) {
            case 1:
                this.mode = 5;
                break;
            case 3:
                this.mode = 1;
                break;
            case 6:
                this.mode = 4;
                break;
            case 8:
                this.mode = 3;
                break;
            default:
                break;
        }
        switch (this.fan) {
            case 0:
                this.fan = 1;
                break;
            case 1:
                this.fan = 2;
                break;
            case 5:
                this.fan = 3;
                break;
            case 3:
                this.fan = 4;
                break;
            default:
                break;
        }
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setInfrareddeviceid(Integer infrareddeviceid) {
        this.infrareddeviceid = infrareddeviceid;
    }

    public void setCodeid(int codeid) {
        this.codeid = codeid;
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
    
    public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}

    public void setCuroperation(int curoperation) {
        this.curoperation = curoperation;
    }
}
