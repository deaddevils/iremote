package com.iremote.action.room;

import java.util.Date;

import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilege;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeType;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.Room;
import com.iremote.service.RoomService;
import com.opensymphony.xwork2.Action;

@DataPrivilege(dataprivilgetype = DataPrivilegeType.MODIFY, domain = "room", parameter = "roomdbid")
public class ModifyRoomAction
{
	private int resultCode = ErrorCodeDefine.SUCCESS;
	private int roomdbid ;
	private String name ;
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
		
		r.setName(name);
		
		phoneuser.setLastupdatetime(new Date());
		PhoneUserHelper.sendInfoChangeMessage(phoneuser , "editroom");

		return Action.SUCCESS;
	}

	public int getResultCode()
	{
		return resultCode;
	}

	public void setRoomdbid(int roomdbid)
	{
		this.roomdbid = roomdbid;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setPhoneuser(PhoneUser phoneuser)
	{
		this.phoneuser = phoneuser;
	}
	
	
}
