package com.iremote.task.devicecommand;

import java.util.*;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iremote.action.device.SetArmStatusAction;
import com.iremote.action.partition.SetPartitionArmStatus;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.Hibernate.HibernateUtil;
import com.iremote.common.encrypt.AES;
import com.iremote.domain.Partition;
import com.iremote.domain.PhoneUser;
import com.iremote.service.PartitionService;
import com.iremote.service.PhoneUserService;
import com.iremote.service.RemoteService;
import com.iremote.task.timertask.processor.PushHelper;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.TagDefine;
import com.iremote.common.Utils;
import com.iremote.device.operate.IOperationTranslator;
import com.iremote.device.operate.OperationTranslatorStore;
import com.iremote.domain.Command;
import com.iremote.domain.ZWaveDevice;
import com.iremote.infraredtrans.ConnectionManager;
import com.iremote.infraredtrans.SynchronizeRequestHelper;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvByteUnit;
import com.iremote.infraredtrans.tlv.TlvIntUnit;
import com.iremote.infraredtrans.tlv.TlvWrap;
import com.iremote.service.ZWaveDeviceService;
import org.hibernate.Hibernate;

public class ExecuteDeviceCommands implements Runnable{
	
	private static Log log = LogFactory.getLog(ExecuteDeviceCommands.class);
	
	private Collection<Command> commands = new ArrayList<Command>();
	private Collection<CommandTlv> commandtlvs = new ArrayList<CommandTlv>();
	private int operatetype;
	private String operator;
	private String deviceid ;
	private String phoneUserName;
	
	public ExecuteDeviceCommands(String deviceid,Collection<CommandTlv> commandtlvs)
	{
		super();
		this.commandtlvs = commandtlvs;
		this.deviceid = deviceid;
	}

	public ExecuteDeviceCommands(Collection<Command> commands, int operatetype , String operator)
	{
		super();
		if ( commands != null && commands.size() > 0 )
			this.commands.addAll(commands);
	}

	public ExecuteDeviceCommands(Collection<Command> commands, int operatetype , String operator, String phoneUserName)
	{
		super();
		this.phoneUserName = phoneUserName;
		if ( commands != null && commands.size() > 0 )
			this.commands.addAll(commands);
	}

	@Override
	public void run() {
		
//		for ( Command c : commands )
//		{
//			ExecuteDeviceCommand edc = new ExecuteDeviceCommand(c , 0 , operatetype , operator);
//			edc.run();
//		}
		
		Map<String ,ArrayList<CommandTlv>> map = createCommandTlv();
		
		for ( String key : map.keySet())
		{
			if ( !ConnectionManager.contants(key))
			{
				log.info(key);
				log.info("gateway offline");
				continue;
			}
			List<CommandTlv> lst = CommandHelper.mergeCommand(key , map.get(key) , false);
			if ( lst == null )
				continue; 
			for ( CommandTlv ct : lst )
			{
				SynchronizeRequestHelper.asynchronizeRequest(key, ct, IRemoteConstantDefine.DEFAULT_TIME_OUT_SECOND);
			}
		}
		
		for ( CommandTlv ct : commandtlvs )
		{
			SynchronizeRequestHelper.asynchronizeRequest(deviceid, ct, IRemoteConstantDefine.DEFAULT_TIME_OUT_SECOND);
		}
	}

	private Map<String ,ArrayList<CommandTlv>> createCommandTlv()
	{
		Map<String ,ArrayList<CommandTlv>> map = new  HashMap<String ,ArrayList<CommandTlv>>() ;
		HashSet<String> dscDeviceIdSet = new HashSet<>();

		for ( Command command : commands )
		{
			List<CommandTlv> lst = getCommandList(map , command.getDeviceid());
			if ( command.getInfraredcode() != null && command.getInfraredcode().length > 0 )
			{
				byte b[] = command.getInfraredcode();
				CommandTlv ct = new CommandTlv(4 , 1);
				
				if ( Utils.isByteMatch(new Byte[]{4 , 1 }, b))
					ct.addUnit(new TlvByteUnit(40 , TlvWrap.getValue(b, TagDefine.TAG_HEAD_LENGTH)));
				else if ( Utils.isByteMatch(new Byte[]{0,40}, b))
					ct.addUnit(new TlvByteUnit(40 , TlvWrap.getValue(b, 0)));
				else 
					ct.addUnit(new TlvByteUnit(40 , b));
	
				lst.add(ct) ;
			}
			else
			{
				JSONArray jsonArray = command.getCommandjson();

				for (int i = 0; i < jsonArray.size(); i++) {
					JSONObject json = jsonArray.getJSONObject(i);
					String operate = json.getString(IRemoteConstantDefine.OPERATION);
					Integer armstatus = null;
					if (IRemoteConstantDefine.WARNING_TYPE_USER_INHOME_ARM.equals(operate))
						armstatus = IRemoteConstantDefine.PARTITION_STATUS_IN_HOME;
					if (IRemoteConstantDefine.WARNING_TYPE_USER_ARM.equals(operate))
						armstatus = IRemoteConstantDefine.PARTITION_STATUS_OUT_HOME;
					if (IRemoteConstantDefine.WARNING_TYPE_USER_DISARM.equals(operate))
						armstatus = IRemoteConstantDefine.PARTITION_STATUS_DIS_ARM;
					if (armstatus != null)
					{
						Integer resultCode = null;
						if (json.containsKey("partitionid")) {
							int partitionid = json.getIntValue("partitionid");
							PartitionService ps = new PartitionService();
							Partition partition = ps.query(partitionid);
							if (partition == null) {
								continue;
							}

							if (partition.getZwavedevice() != null && dscDeviceIdSet.contains(partition.getZwavedevice().getDeviceid())) {
								try {
									HibernateUtil.commit();
									Thread.sleep(5000);
									HibernateUtil.getSession().clear();
									HibernateUtil.beginTransaction();
								} catch (InterruptedException e) {
									log.info(e);
								}
							}

							if (partition.getZwavedevice() != null) {
								dscDeviceIdSet.add(partition.getZwavedevice().getDeviceid());
							}

							Integer phoneuserid = getPhoneuserFromCommand(partition);
							if (phoneuserid == null){
								continue;
							}
							PhoneUser phoneuser = new PhoneUserService().query(phoneuserid);

							SetPartitionArmStatus spas = new SetPartitionArmStatus();
							spas.setPhoneuser(phoneuser);
							spas.setArmstatus(armstatus);
							spas.setPartitionid(partitionid);
							spas.setSetDelay(true);
							if (phoneUserName == null) {
								spas.setOperator("");
							} else {
								spas.setOperator(phoneUserName);
							}
							if (armstatus == IRemoteConstantDefine.PARTITION_STATUS_DIS_ARM) {
								String encryptpassword = json.getString("encryptpassword");
								if (!StringUtils.isBlank(encryptpassword)) {
									spas.setPassword(AES.decrypt2Str(encryptpassword));
								}
							}

							spas.execute();
							resultCode =  spas.getResultCode();

							if (resultCode != ErrorCodeDefine.SUCCESS){
								PushHelper.pushNotificationbyType( phoneuser, partition.getName(), phoneuser.getUsername(), IRemoteConstantDefine.NOTIFICATION_TYPE_SCENE_SET_PARTITION_ARMSTATUS_FAILED);
							}
						} else if (json.containsKey("zwavedeviceid") || json.containsKey("cameraid")) {
							SetArmStatusAction sasa = new SetArmStatusAction();
							sasa.setArmstatus(armstatus);
							sasa.setCameraid(json.getIntValue("cameraid"));
							sasa.setZwavedeviceid(json.getIntValue("zwavedeviceid"));
							sasa.execute();
							resultCode = sasa.getResultCode();
						}

						log.info("the result of set partition arm status: "+resultCode);
					} else {
						ZWaveDeviceService zds = new ZWaveDeviceService();
						ZWaveDevice zd = zds.query(command.getZwavedeviceid());
						IOperationTranslator ot = OperationTranslatorStore.getInstance().getOperationTranslator(zd.getMajortype(), zd.getDevicetype());
						ot.setZWavedevice(zd);
						ot.setCommandjson(command.getCommandjsonstr());
						List<CommandTlv> l = ot.getCommandTlv();
						if ( l != null )
							lst.addAll(l);
						break;
					}
				}
			}
//			else if ( command.getZwavecommand() != null && command.getZwavecommand().length > 0 )
//				lst.addAll(CommandHelper.splitCommand(command.getZwavecommand()));
//			else if ( command.getZwavecommands() != null && command.getZwavecommands().length > 0 )
//				lst.addAll(CommandHelper.splitCommand(command.getZwavecommands()));
			
			if ( lst != null )
			{
				for ( CommandTlv ct : lst)
				{
					if ( operator != null && operator.length() > 0 )
					{
						ct.addOrReplaceUnit(new TlvByteUnit(12 , operator.getBytes()));
						ct.addOrReplaceUnit(new TlvIntUnit(TagDefine.TAG_OPERATION_TYPE , operatetype,TagDefine.TAG_LENGTH_1 ));
					}
					else 
					{
						ct.addOrReplaceUnit(new TlvByteUnit(12 , IRemoteConstantDefine.ASSOCIATION_SCENE_TASK_OPERATOR.getBytes()));
						ct.addOrReplaceUnit(new TlvIntUnit(TagDefine.TAG_OPERATION_TYPE ,IRemoteConstantDefine.OPERATOR_TYPE_ASSCIATION , TagDefine.TAG_LENGTH_1));
					}
				}
			}
		}
		return map ;
	}

	private Integer getPhoneuserFromCommand(Partition partition) {
		Integer phoneuserid = null;
		if (partition.getPhoneuser() == null) {
			RemoteService rs = new RemoteService();
			deviceid = partition.getZwavedevice().getDeviceid();
			phoneuserid = rs.queryOwnerId(deviceid);
		} else {
			phoneuserid = partition.getPhoneuser().getPhoneuserid();
		}

		if (phoneuserid == null) {
			return null;
		}

		return phoneuserid;
	}

	private List<CommandTlv> getCommandList(Map<String ,ArrayList<CommandTlv>> map , String deviceid)
	{
		if ( !map.containsKey(deviceid))
			map.put(deviceid, new ArrayList<CommandTlv>());
		
		return map.get(deviceid);
	}
}
