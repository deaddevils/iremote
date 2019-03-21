package com.iremote.action.infraredcode2;

import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.device.operate.infrareddevice.TvStbOperateCommandBase;
import com.iremote.domain.InfraredDevice;
import com.iremote.infraredtrans.ConnectionManager;
import com.iremote.infraredtrans.SynchronizeRequestHelper;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvWrap;
import com.iremote.service.InfraredDeviceService;
import com.iremote.service.InfreredCodeLiberayService;
import com.opensymphony.xwork2.Action;
import org.apache.commons.lang3.StringUtils;

public class TvStbOperationAction {
    protected int resultCode = ErrorCodeDefine.SUCCESS;
    private Integer infrareddeviceid;
    private String deviceid ;
    private Integer codeid;
    private String operation;
    private Integer value;
    protected String devicetype ;

    //private int[] liberay ;

    public String execute()
    {
    	if ( init() == false )
    		return Action.SUCCESS;

        TvStbOperateCommandBase toc = new TvStbOperateCommandBase();

        if (value != null)
            operation = String.format("%s_%d", operation, value);

        if (IRemoteConstantDefine.DEVICE_TYPE_STB.equals(devicetype))
            toc.setStbOperation(operation);
        if (IRemoteConstantDefine.DEVICE_TYPE_TV.equals(devicetype))
            toc.setTvOperation(operation);
        
        toc.setDevicetype(devicetype);
        toc.setCodeid(String.valueOf(codeid));
        
        //toc.setCodeliberay(this.liberay);

        CommandTlv ct = toc.createCommand();

        if (ct == null) {
            this.resultCode = ErrorCodeDefine.NOT_SUPPORT;
            return Action.SUCCESS;
        }

        if (ConnectionManager.isOnline(deviceid) == false) {
            this.resultCode = ErrorCodeDefine.DEVICE_OFFLINE;
            return Action.SUCCESS;
        }

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
	    	String str = cls.queryByCodeid(this.codeid, devicetype);
	    	
	        if (StringUtils.isBlank(str))
	        {
	        	this.resultCode = ErrorCodeDefine.INFRARED_CODE_LIBRARY_NOT_EXISTS;
	        	return false ;
	        }
		}

    	if ( StringUtils.isNotBlank(deviceid) && this.codeid != null )  // deviceid and codeid is valid , ignore infrareddeviceid .
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

	        if ( this.codeid != null )  // codeid is valid , ignore code attributes of the infrared device.
	        	return true;

	        /* ignore device code library column,use codeid column instead 
	        if ( id.getCodelibery() != null  ) // use code library setting of the device
	        {
	        	this.liberay = id.getCodelibery();
	        	return true;
	        }
	        else  // use codeid setting of the device
	        */
	        {
	        	InfreredCodeLiberayService cls = new InfreredCodeLiberayService();
	        	String s = cls.queryByCodeid(Integer.valueOf(id.getCodeid()), id.getDevicetype());
                if (StringUtils.isNotBlank(s))
                {
                    //liberay = Utils.jsontoIntArray(s);
                	this.codeid = Integer.valueOf(id.getCodeid());
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

    public int getResultCode() {
        return resultCode;
    }

    public void setInfrareddeviceid(Integer infrareddeviceid) {
        this.infrareddeviceid = infrareddeviceid;
    }

    public void setCodeid(int codeid) {
        this.codeid = codeid;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public void setValue(int value) {
        this.value = value;
    }

	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}

	public void setDevicetype(String devicetype) {
		this.devicetype = devicetype;
	}

} 
