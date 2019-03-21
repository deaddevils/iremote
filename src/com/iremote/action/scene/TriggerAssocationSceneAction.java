package com.iremote.action.scene;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.TagDefine;
import com.iremote.common.commandclass.CommandUtil;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilege;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeType;
import com.iremote.dataprivilege.interceptorchecker.DataPrivileges;
import com.iremote.domain.Associationscene;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.ThirdPart;
import com.iremote.domain.ZWaveDevice;
import com.iremote.infraredtrans.SynchronizeRequestHelper;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvByteUnit;
import com.iremote.infraredtrans.tlv.TlvIntUnit;
import com.iremote.scene.SceneExecutor;
import com.iremote.service.AssociationsceneService;
import com.iremote.service.ZWaveDeviceService;
import com.opensymphony.xwork2.Action;

@DataPrivileges( dataprivilege = {
	@DataPrivilege(dataprivilgetype = DataPrivilegeType.OPERATION, domain = "camera", parameter = "cameraid"),
	@DataPrivilege(dataprivilgetype = DataPrivilegeType.OPERATION, domain = "device", parameter = "zwavedeviceid"),
})
public class TriggerAssocationSceneAction 
{
	private int resultCode = ErrorCodeDefine.SUCCESS ;
	private Integer zwavedeviceid ;
	private Integer cameraid;
	private Integer channelid;
	private String devicestatus;
	private PhoneUser phoneuser ;
	private ThirdPart thirdpart ;
	
	public String execute()
	{
		if ( zwavedeviceid != null && zwavedeviceid == 0 )
			zwavedeviceid = null ;
		if ( cameraid != null && cameraid == 0 )
			zwavedeviceid = null ;
		if ( zwavedeviceid == null && cameraid == null )
		{
			this.resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT;
			return Action.SUCCESS;
		}
		
		if ( channelid == null )
			channelid = 0 ;

		if (associationDoorLockBellRing())
			return Action.SUCCESS;

		AssociationsceneService ass = new AssociationsceneService();
		List<Associationscene> lst = ass.queryAssociationscene(zwavedeviceid, cameraid, channelid,devicestatus);
		
		if ( lst == null || lst.isEmpty() )
		{
			this.resultCode = ErrorCodeDefine.ASSOCIATIONSCENE_NOT_EXIST ;
			return Action.SUCCESS;
		}
		
		boolean hasAssociationCommand = false ;
		for ( Associationscene as : lst )
		{
			if ( as.getCommandlist().size() > 0 )
			{
				hasAssociationCommand = true ;
				break ;
			}
		}
		
		if ( hasAssociationCommand == false )
		{
			this.resultCode = ErrorCodeDefine.ASSOCIATIONSCENE_NOT_EXIST ;
			return Action.SUCCESS;
		}
		
		Set<Integer> sids = new HashSet<Integer>();
		for ( Associationscene as : lst )
			sids.add(as.getScenedbid());
		
		String name = "";
		if ( phoneuser != null )
			name = phoneuser.getPhonenumber();
		else if ( thirdpart != null )
			name = thirdpart.getName();
		
		for ( Integer id : sids )
		{
			SceneExecutor se = new SceneExecutor(id , phoneuser , thirdpart , name, IRemoteConstantDefine.OPERATOR_TYPE_ASSCIATION, IRemoteConstantDefine.SCENE_EXECUTE_TYPE_USER_TRIGGER);
			se.run();
		}
		
		return Action.SUCCESS;
	}

	private boolean associationDoorLockBellRing() {
		if (zwavedeviceid != null) {
			ZWaveDevice zWaveDevice = new ZWaveDeviceService().query(zwavedeviceid);
			if (zWaveDevice != null
					&& IRemoteConstantDefine.DEVICE_TYPE_LIST_LIKE_DOOR_LOCK.contains(zWaveDevice.getDevicetype())
					&& IRemoteConstantDefine.WARNING_TYPE_DOOR_BELL_RING.equals(devicestatus)) {
				CommandTlv ct = CommandUtil.createLockCommand(zWaveDevice.getNuid(), (byte)IRemoteConstantDefine.DEVICE_STATUS_DOOR_LOCK_OPEN);

				ct.addOrReplaceUnit(new TlvByteUnit(12 , IRemoteConstantDefine.ASSOCIATION_SCENE_TASK_OPERATOR.getBytes()));
				ct.addOrReplaceUnit(new TlvIntUnit(TagDefine.TAG_OPERATION_TYPE ,IRemoteConstantDefine.OPERATOR_TYPE_ASSCIATION , TagDefine.TAG_LENGTH_1));

				SynchronizeRequestHelper.asynchronizeRequest(zWaveDevice.getDeviceid(), ct, IRemoteConstantDefine.DEFAULT_TIME_OUT_SECOND);

				return true;
			}
		}
		return false;
	}

	public int getResultCode() {
		return resultCode;
	}
	public void setZwavedeviceid(Integer zwavedeviceid) {
		this.zwavedeviceid = zwavedeviceid;
	}
	public void setCameraid(Integer cameraid) {
		this.cameraid = cameraid;
	}
	public void setChannelid(Integer channelid) {
		this.channelid = channelid;
	}
	public void setDevicestatus(String devicestatus) {
		this.devicestatus = devicestatus;
	}

	public void setPhoneuser(PhoneUser phoneuser) {
		this.phoneuser = phoneuser;
	}

	public void setThirdpart(ThirdPart thirdpart) {
		this.thirdpart = thirdpart;
	}
	
	

}
