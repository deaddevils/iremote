package com.iremote.action.room;

import java.util.Date;

import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.Utils;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilege;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeType;
import com.iremote.dataprivilege.interceptorchecker.DataPrivileges;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.Room;
import com.iremote.service.RoomService;
import com.opensymphony.xwork2.Action;

@DataPrivileges( dataprivilege = {
	@DataPrivilege(dataprivilgetype = DataPrivilegeType.MODIFY, domain = "infrareddevices", parameter = "infrareddeviceids"),
	@DataPrivilege(dataprivilgetype = DataPrivilegeType.MODIFY, domain = "zwavedevices", parameter = "zwavedeviceids"),
	@DataPrivilege(dataprivilgetype = DataPrivilegeType.MODIFY, domain = "cameras", parameter = "cameraids")
})
public class AddRoomAction
{
	private int resultCode = ErrorCodeDefine.SUCCESS;
	private String name;
	private String zwavedeviceids;
	private String infrareddeviceids;
	private String cameraids;
	private String subdeviceids;
	private Room room ;
	private PhoneUser phoneuser ;
	
	public String execute()
	{
		createRoom();
		RoomActionHelper.createRoomZwaveAppliance(room , Utils.jsontoIntList(zwavedeviceids));
		RoomActionHelper.createRoomInfraredAppliance(room , Utils.jsontoIntList(infrareddeviceids));
		RoomActionHelper.createRoomCameraAppliance(room, Utils.jsontoIntList(cameraids));
		RoomActionHelper.createRoomSubDeviceAppliance(room, Utils.jsontoIntList(subdeviceids));

		RoomService rs = new RoomService();
		rs.saveOrUpdate(room);
		
		phoneuser.setLastupdatetime(new Date());
		PhoneUserHelper.sendInfoChangeMessage(phoneuser , "addroom");

		return Action.SUCCESS;
	}

	private void createRoom()
	{
		room = new Room();
		room.setName(name);
		room.setRoomid(Utils.createtoken());
		room.setPhonenumber(phoneuser.getPhonenumber());
		room.setPhoneuserid(phoneuser.getPhoneuserid());
	}

	public int getResultCode()
	{
		return resultCode;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setZwavedeviceids(String zwavedeviceids)
	{
		this.zwavedeviceids = zwavedeviceids;
	}

	public void setInfrareddeviceids(String infrareddeviceids)
	{
		this.infrareddeviceids = infrareddeviceids;
	}

	public Room getRoom()
	{
		return room;
	}

	public void setPhoneuser(PhoneUser phoneuser)
	{
		this.phoneuser = phoneuser;
	}

	public void setCameraids(String cameraids)
	{
		this.cameraids = cameraids;
	}

	public void setSubdeviceids(String subdeviceids) {
		this.subdeviceids = subdeviceids;
	}
}
