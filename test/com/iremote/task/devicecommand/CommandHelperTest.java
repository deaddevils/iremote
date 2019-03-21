package com.iremote.task.devicecommand;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.iremote.common.Utils;
import com.iremote.common.commandclass.CommandUtil;
import com.iremote.domain.Remote;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.service.RemoteService;
import com.iremote.test.db.Db;


public class CommandHelperTest
{
	
	public static void main2(String arg[])
	{
		JSONObject json = new JSONObject();
		json.put("key", "v1");
		System.out.println(json.toJSONString());
		
		json.put("key", "v2");
		System.out.println(json.toJSONString());
	}
	
	public static void main1(String arg[])
	{
		Db.init();
		RemoteService rs = new RemoteService();
		
		Remote r = rs.getIremotepassword("iRemote4005000000255");
		List<CommandTlv> lst = new ArrayList<CommandTlv>();
		
		lst.add(CommandUtil.createSwitchCommand(11,(byte)1,(byte)0));
		lst.add(CommandUtil.createSwitchCommand(11,(byte)2,(byte)0));
		lst.add(CommandUtil.createSwitchCommand(12,(byte)0));
		lst.add(CommandUtil.createSwitchCommand(13,(byte)1,(byte)0));
		lst.add(CommandUtil.createSwitchCommand(13,(byte)2,(byte)0));
		lst.add(CommandUtil.createSwitchCommand(13,(byte)3,(byte)255));
		lst.add(CommandUtil.createSwitchCommand(14,(byte)1,(byte)0));
		lst.add(CommandUtil.createSwitchCommand(14,(byte)2,(byte)0));
		lst.add(CommandUtil.createSwitchCommand(14,(byte)3,(byte)255));

		lst.add(CommandUtil.createSwitchCommand(15,(byte)1,(byte)0));
		lst.add(CommandUtil.createSwitchCommand(15,(byte)2,(byte)0));
		lst.add(CommandUtil.createSwitchCommand(16,(byte)1,(byte)0));
		lst.add(CommandUtil.createSwitchCommand(16,(byte)2,(byte)0));
		lst.add(CommandUtil.createSwitchCommand(17,(byte)0));
		lst.add(CommandUtil.createSwitchCommand(18,(byte)0));
		lst.add(CommandUtil.createSwitchCommand(20,(byte)255));

		
		for ( CommandTlv ct : lst )
			ct.setDeviceid("iRemote4005000000255");
		
		lst = CommandHelper.mergeCommand(r, lst);
		
		for ( CommandTlv ct : lst )
			Utils.print("", ct.getByte());
		
		Db.commit();
	}
	
	public static void main(String arg[])
	{
		Db.init();
		RemoteService rs = new RemoteService();
		
		Remote r = rs.getIremotepassword("iRemote2005000000680");
		List<CommandTlv> lst = new ArrayList<CommandTlv>();
		lst.add(CommandUtil.createSwitchCommand(5,(byte)0));
		lst.add(CommandUtil.createSwitchCommand(7,(byte)0));
		lst.add(CommandUtil.createSwitchCommand(8,(byte)0));
		lst.add(CommandUtil.createSwitchCommand(9,(byte)255));
		lst.add(CommandUtil.createSwitchCommand(10,(byte)255));
		
		for ( CommandTlv ct : lst )
			ct.setDeviceid("iRemote2005000000680");
		
		lst = CommandHelper.mergeCommand(r, lst);
		
		for ( CommandTlv ct : lst )
			Utils.print("", ct.getByte());
		
		Db.commit();
	}
}
