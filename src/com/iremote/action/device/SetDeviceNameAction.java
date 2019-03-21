package com.iremote.action.device;

import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.jms.JMSUtil;
import com.iremote.common.jms.vo.InfoChange;
import com.iremote.domain.*;
import com.iremote.service.RoomApplianceService;
import com.opensymphony.xwork2.Action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SetDeviceNameAction extends SetDeiviceNamePageAction{
    private int resultCode = ErrorCodeDefine.SUCCESS ;
    private int channelid;
    private String color;
    private String name;
    private List<String> lst;
    private Map<Integer,Integer> color_capabilitycode = new HashMap<>();
    private ZWaveSubDevice zWaveSubDevice;
    public String execute()
    {
        if(zwavedeviceid == null && cameraid == null && infrareddeviceid == null){
            resultCode = ErrorCodeDefine.PARMETER_ERROR;
            return Action.SUCCESS;
        }
        lst = PhoneUserHelper.queryDeviceidbySharetoPhoneuserid(phoneuser.getPhoneuserid());

        if ( zwavedeviceid != null && zwavedeviceid != 0 )
            processZwaveDevice();
        else if ( cameraid != null && cameraid != 0 )
            processCamera();
        else
            processInfraredDevice();

        editName();
        if(resultCode != ErrorCodeDefine.SUCCESS){
            return Action.SUCCESS;
        }
        JMSUtil.sendmessage(IRemoteConstantDefine.NOTIFICATION_INFO_CHANGED, new InfoChange(deviceid , new Date() , 0) );
        return Action.SUCCESS;
    }


    {
        color_capabilitycode.put(0,IRemoteConstantDefine.CAPABILITY_DEVICE_COLOR);
        color_capabilitycode.put(1,IRemoteConstantDefine.CAPABILITY_CHANNEL_COLOR_1);
        color_capabilitycode.put(2,IRemoteConstantDefine.CAPABILITY_CHANNEL_COLOR_2);
        color_capabilitycode.put(3,IRemoteConstantDefine.CAPABILITY_CHANNEL_COLOR_3);
        color_capabilitycode.put(4,IRemoteConstantDefine.CAPABILITY_CHANNEL_COLOR_4);
        color_capabilitycode.put(5,IRemoteConstantDefine.CAPABILITY_CHANNEL_COLOR_5);
        color_capabilitycode.put(6,IRemoteConstantDefine.CAPABILITY_CHANNEL_COLOR_6);
    }

    @Override
    protected String processZwaveDevice() {
        super.processZwaveDevice();
        if ( this.zwaveDevice == null )
        {
        	this.resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT;
        	return null;
        }
        
        List<ZWaveDevice> dlst = zds.querybydeviceidandName(lst , name);
        if ( dlst != null && dlst.size() > 0 ){
            for(ZWaveDevice z : dlst){
                if(zwavedeviceid.equals(z.getZwavedeviceid())){
                    continue;
                }else{
                    if(name.equals(z.getName())){
                        resultCode = ErrorCodeDefine.NAME_IS_EXIST;
                        break;
                    }
                }
            }
            return null;
        }
        if(channelid > 0){
            List<ZWaveSubDevice> zsds = zwaveDevice.getzWaveSubDevices();
            if ( zsds != null && zsds.size() > 0  )
            {
	            for(ZWaveSubDevice zsd : zsds)
	            {
	                if(zsd.getChannelid() == channelid)
	                    continue;

                    if(zsd.getName().equals(name))
                    {
                        resultCode = ErrorCodeDefine.NAME_IS_EXIST;
                        return null;
                    }
	            }
            }
        }
        deviceid = zwaveDevice.getDeviceid();
        return null;
    }

    @Override
    protected String processCamera() {
        super.processCamera();
        List<Camera> dlst = cs.queryByDeviceidAndName(lst , name);
        if ( dlst != null && dlst.size() > 0 ){
            for(Camera c : dlst){
                if(cameraid.equals(c.getCameraid())){
                    continue;
                }else{
                    if(name.equals(c.getName())){
                        resultCode = ErrorCodeDefine.NAME_IS_EXIST;
                        break;
                    }
                }
            }
            return null;
        }
        deviceid = camera.getDeviceid();
        return null;
    }

    @Override
    protected String processInfraredDevice() {
        super.processInfraredDevice();
        List<InfraredDevice> dlst = ids.querybydeviceidandName(lst , name);
        if ( dlst != null && dlst.size() > 0 ){
            resultCode = ErrorCodeDefine.NAME_IS_EXIST;
            return null;
        }
        if ( dlst != null && dlst.size() > 0 ){
            for(InfraredDevice i : dlst){
                if(infrareddeviceid.equals(i.getInfrareddeviceid())){
                    continue;
                }else{
                    if(name.equals(i.getName())){
                        resultCode = ErrorCodeDefine.NAME_IS_EXIST;
                        break;
                    }
                }
            }
            return null;
        }
        deviceid = infrareddevice.getDeviceid();
        return null;
    }

    private void editName(){
    	RoomApplianceService ras = new RoomApplianceService();
        List<DeviceCapability> capabilitys = null;
        if(infrareddevice != null){
            infrareddevice.setName(name);
             capabilitys = infrareddevice.getCapability();
             RoomAppliance querybyInfraredid = ras.querybyInfraredid(infrareddevice.getInfrareddeviceid());
             if(querybyInfraredid!=null){
            	 querybyInfraredid.setName(name);
             }
        }else if(camera != null){
            camera.setName(name);
            RoomAppliance querybyCameraid = ras.querybyCameraid(camera.getCameraid());
            if(querybyCameraid!=null){
            	querybyCameraid.setName(name);
            }
        }else if(zwaveDevice != null){
            if(channelid > 0){
                for(ZWaveSubDevice zd : zwaveDevice.getzWaveSubDevices()){
                    if(zd.getChannelid() == channelid){
                        zWaveSubDevice = zd;
                        break;
                    }
                }
                if(zWaveSubDevice != null){
                    zWaveSubDevice.setName(name);
                    RoomAppliance querybyZwavedeviceidAndChannelid = ras.querybyZwavedeviceidAndChannelid(zwavedeviceid, channelid);
                    if(querybyZwavedeviceidAndChannelid!=null){
                    	querybyZwavedeviceidAndChannelid.setName(name);
                    }
                }else{
                    zWaveSubDevice = new ZWaveSubDevice();
                    zWaveSubDevice.setZwavedevice(zwaveDevice);
                    zWaveSubDevice.setChannelid(channelid);
                    zWaveSubDevice.setZwavesubdeviceid(zwavedeviceid);
                    zWaveSubDevice.setName(name);
                    zwaveDevice.getzWaveSubDevices().add(zWaveSubDevice);
                }
            }else{
                zwaveDevice.setName(name);
                List<RoomAppliance> query = ras.query(zwaveDevice.getDeviceid(), zwaveDevice.getApplianceid());
                if(query != null && query.size() > 0){
        			RoomAppliance roomAppliance = query.get(0);
        			roomAppliance.setName(name);
        			ras.saveOrUpdate(roomAppliance);
        		}
            }
            capabilitys = zwaveDevice.getCapability();
        }else{
            resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT;
            return;
        }

        for(DeviceCapability c : capabilitys){
            if( c.getCapabilitycode() == color_capabilitycode.get(channelid)){
                c.setCapabilityvalue(color);
                return;
            }
        }
        DeviceCapability dc = new DeviceCapability();
        dc.setCapabilitycode(color_capabilitycode.get(channelid));
        dc.setCapabilityvalue(color);
        dc.setZwavedevice(zwaveDevice);
        dc.setInfrareddevice(infrareddevice);
        dc.setCamera(camera);
        capabilitys.add(dc);
    }


    public void setChannelid(int channelid) {
        this.channelid = channelid;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getResultCode() {
        return resultCode;
    }
}
