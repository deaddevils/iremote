package com.iremote.action.room;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilege;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeType;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.Room;
import com.iremote.domain.RoomAppliance;
import com.iremote.domain.RoomDevice;
import com.iremote.service.RoomDeviceService;
import com.iremote.service.RoomService;
import com.opensymphony.xwork2.Action;

@DataPrivilege(dataprivilgetype = DataPrivilegeType.MODIFY, domain = "room", parameter = "roomdbid")
public class DeleteRoomApplianceAction
{
	private int resultCode = ErrorCodeDefine.SUCCESS;
	private int roomdbid ;
	private String zwavedeviceids;
	private String infrareddeviceids;
	private String cameraids;
	private String subdeviceids;

	private Integer zwavedeviceid;
	private Integer infrareddeviceid;
	private Integer cameraid;
	private Integer subdeviceid;

	private PhoneUser phoneuser ;

	public String execute()
	{
		RoomService rs = new RoomService();
		Room r = rs.query(roomdbid);
		
		if ( r == null )
		{
			this.resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT;
			return Action.SUCCESS;
		}
		
		if ( r.getAppliancelist() == null )
			r.setAppliancelist(new ArrayList<RoomAppliance>());

		List<Integer> zidl = Utils.jsontoIntList(zwavedeviceids);
		List<Integer> ridl = Utils.jsontoIntList(infrareddeviceids);
		List<Integer> cidl = Utils.jsontoIntList(cameraids);
		List<Integer> sid1 = Utils.jsontoIntList(subdeviceids);

		if ( this.zwavedeviceid != null )
			zidl.add(zwavedeviceid);
		if ( this.infrareddeviceid != null )
			ridl.add(infrareddeviceid);
		if ( this.cameraid != null )
			cidl.add(cameraid);
		if ( this.subdeviceid != null )
			sid1.add(subdeviceid);

		remove(r , IRemoteConstantDefine.DEVICE_MAJORTYPE_ZWAVE , zidl );
		remove(r , IRemoteConstantDefine.DEVICE_MAJORTYPE_INFRARED , ridl );
		remove(r , IRemoteConstantDefine.DEVICE_MAJORTYPE_CAMERA , cidl );
		remove(r , IRemoteConstantDefine.DEVICE_MAJORTYPE_ZWAVE , sid1 );

		if ( r.getAppliancelist().size() == 0 )
			rs.delete(r);
		
		phoneuser.setLastupdatetime(new Date());
		PhoneUserHelper.sendInfoChangeMessage(phoneuser , "deleteroomappliance");
		
		return Action.SUCCESS;
	}
	
	private void remove(Room r , String majortype , List<Integer> lst)
	{
		for ( ListIterator<RoomAppliance> it = r.getAppliancelist().listIterator() ; it.hasNext();)
		{
			RoomAppliance ra = it.next();
			
			if ( !ra.getMajortype().equals(majortype))
				continue ;
			
			int id = 0 ;
			if ( IRemoteConstantDefine.DEVICE_MAJORTYPE_ZWAVE.equals(majortype))
				if(ra.getSubdeviceid()!= null && ra.getSubdeviceid() > 0) {
					id = ra.getSubdeviceid();
				}else {
					id = ra.getZwavedeviceid();
				}
			else if ( IRemoteConstantDefine.DEVICE_MAJORTYPE_INFRARED.equals(majortype))
				id = ra.getInfrareddeviceid();
			else if ( IRemoteConstantDefine.DEVICE_MAJORTYPE_CAMERA.equals(majortype))
				id = ra.getCameraid();
			if ( lst.contains(id)){
				it.remove();
				deleteFromRoomDevice(id);
			}
		}
	}

	private void deleteFromRoomDevice(int id) {
		RoomDeviceService rds = new RoomDeviceService();
		List<RoomDevice> roomDevices = rds.querybydeviceid(id);
		rds.deleteAll(roomDevices);
	}

	public int getResultCode()
	{
		return resultCode;
	}
	public void setRoomdbid(int roomdbid)
	{
		this.roomdbid = roomdbid;
	}
	public void setZwavedeviceids(String zwavedeviceids)
	{
		this.zwavedeviceids = zwavedeviceids;
	}
	public void setInfrareddeviceids(String infrareddeviceids)
	{
		this.infrareddeviceids = infrareddeviceids;
	}

	public void setPhoneuser(PhoneUser phoneuser)
	{
		this.phoneuser = phoneuser;
	}

	public void setZwavedeviceid(Integer zwavedeviceid)
	{
		this.zwavedeviceid = zwavedeviceid;
	}

	public void setInfrareddeviceid(Integer infrareddeviceid)
	{
		this.infrareddeviceid = infrareddeviceid;
	}

	public void setCameraids(String cameraids)
	{
		this.cameraids = cameraids;
	}

	public void setCameraid(Integer cameraid)
	{
		this.cameraid = cameraid;
	}

	public void setSubdeviceids(String subdeviceids) {
		this.subdeviceids = subdeviceids;
	}

	public void setSubdeviceid(Integer subdeviceid) {
		this.subdeviceid = subdeviceid;
	}
}
