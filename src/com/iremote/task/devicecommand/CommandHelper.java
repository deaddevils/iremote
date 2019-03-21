package com.iremote.task.devicecommand;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.iremote.common.TagDefine;
import com.iremote.common.constant.GatewayCapabilityType;
import com.iremote.domain.Remote;
import com.iremote.infraredtrans.GatewayReportHelper;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvByteArrayUnit;
import com.iremote.infraredtrans.tlv.TlvWrap;
import com.iremote.service.RemoteService;

public class CommandHelper {

	private static Log log = LogFactory.getLog(CommandHelper.class);
	
	public static List<CommandTlv> splitCommand(byte[] zwavecommand)
	{
		List<CommandTlv> lst = new ArrayList<CommandTlv>();
		
		byte[][] b = TlvWrap.splitTag(zwavecommand, 0);
		int tag = TlvWrap.getTag(b[0], 0);
		CommandTlv ct = null ;
		for ( int i = 0 ; i < b.length ; i ++ )	
		{
			// create CommandTlv object and add it to list at command start .
			if ( TlvWrap.getTag(b[i], 0) == tag )
			{
				 ct = new CommandTlv(30 , 7);
				 lst.add(ct);
			}
			
			if ( !isClockDoorReport(b[i]))
				ct.addUnit(new TlvByteArrayUnit(b[i]));
		}
		
		return lst;
	}
	
	public static List<CommandTlv> mergeCommand(Remote remote , List<CommandTlv> lst )
	{
		return mergeCommand(remote , lst , false);
	}
	
	public static List<CommandTlv> mergeCommand(String deviceid , List<CommandTlv> lst , boolean usegroup)
	{
		RemoteService rs = new RemoteService();
		Remote r = rs.getIremotepassword(deviceid);
		
		return mergeCommand(r , lst , usegroup);
	}
	
	public static List<CommandTlv> mergeCommand(Remote remote , List<CommandTlv> lst, boolean usegroup)
	{
		if ( remote == null )
			return null ;
		
//		if ( usegroup && GatewayReportHelper.hasGatewayCapability(remote, GatewayCapabilityType.devicegroupcommand.getCapabilitycode() ))
//			lst = mergetoDeviceGroupCommand(remote.getDeviceid() ,lst);

		if (GatewayReportHelper.hasGatewayCapability(remote, GatewayCapabilityType.batchzwavecommand.getCapabilitycode() ) )
			lst = mergetoCommandPackage(remote.getDeviceid() , lst);
		return lst ;
	}
	
	private static List<CommandTlv> mergetoDeviceGroupCommand(String deviceid , List<CommandTlv> lst)
	{
		if ( lst == null || lst.size() <= 1 )
			return lst ;
		List<CommandTlv> rstl = new ArrayList<CommandTlv>();
		
		for ( ; lst.size() > 0 ; )
		{
			CommandTlv ft = lst.remove(0);
			DeviceGroupCommandTlv dgc = new DeviceGroupCommandTlv(deviceid);
			if ( dgc.initCommand(ft) == false )
			{
				rstl.add(ft);
				continue;
			}
			
			List<CommandTlv> rl = new ArrayList<CommandTlv>();
			for ( CommandTlv ct : lst )
			{
				if ( dgc.merge(ct))
					rl.add(ct);
			}
			if ( rl.size() > 0 )
			{
				lst.removeAll(rl);
				rstl.add(dgc);
			}
			else 
				rstl.add(ft);
		}
		return rstl ;
	}
	
	private static List<CommandTlv> mergetoCommandPackage(String deviceid , List<CommandTlv> lst)
	{
		if ( lst == null || lst.size() <= 1 )
			return lst ;
		List<CommandTlv> rstl = new ArrayList<CommandTlv>();
		
		for ( ; lst.size() > 0 ; )
		{
			CommandTlv ft = lst.remove(0);
			CommandPackageTlv cpt = new CommandPackageTlv();
			if ( cpt.initCommand(ft) == false )
			{
				rstl.add(ft);
				continue;
			}
			
			List<CommandTlv> rl = new ArrayList<CommandTlv>();
			for ( CommandTlv ct : lst )
			{
				if ( cpt.addCommand(ct))
					rl.add(ct);
			}
			if ( rl.size() > 0 )
			{
				lst.removeAll(rl);
				rstl.add(cpt);
			}
			else 
				rstl.add(ft);
		}
		return rstl ;
	}
	
	
	private static boolean isClockDoorReport(byte[] b)
	{
		if ( TlvWrap.getTag(b, 0) != TagDefine.TAG_ZWAVE_COMMAND_REPORT )
			return false;
		
		byte[] v = TlvWrap.readTag(b, TagDefine.TAG_ZWAVE_COMMAND_REPORT, 0);
		if ( v == null || v.length < 3 )
			return false ;
		return ( v[0] == 98 && v[1] == 3 && v[2] == (byte)255 );
	}
	
	public static void main(String arg[])
	{
		System.out.print(isClockDoorReport(new byte[]{}));
	}
}
