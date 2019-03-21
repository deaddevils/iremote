package com.iremote.task.devicecommand;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.TagDefine;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvByteUnit;
import com.iremote.infraredtrans.tlv.TlvWrap;

public class CommandPackageTlv extends CommandTlv
{
	private static Log log = LogFactory.getLog(CommandPackageTlv.class);
	
	private List<CommandTlv> ctlst = new ArrayList<CommandTlv>();
	private List<byte[]> cmdlst = new ArrayList<byte[]>();
	private int length = 0 ;
	private int sequence = 1 ; 
	private boolean build = false ;
	
	public CommandPackageTlv()
	{
		super(TagDefine.COMMAND_CLASS_DEVICE, TagDefine.COMMAND_SUB_CLASS_DEVICE_BATCH_COMMAND);
	}

	public boolean initCommand(CommandTlv ct)
	{
		return addCommand(ct) ;
	}
	
	public boolean addCommand(CommandTlv ct)
	{
		if ( build == true )
			return false ;
		
		int l = ct.getPackageCommandLength() ;
		if ( l == 0 )
			return false ;

		if ( length + l >= 500 )
			return false ;

		byte[] cmd = ct.getBytesforPackageCommand();
		if ( cmd == null )
			return false ;

		cmd[0] =  (byte)sequence;
		sequence ++ ;
		cmdlst.add(cmd);
		ctlst.add(ct);
		return true;
	}

	@Override
	public int getAckcount()
	{
		return ctlst.size() + 1 ;
	}
		
	@Override
	public void onAckArrive(int ack , byte[] content)
	{
		if ( ack != ErrorCodeDefine.SUCCESS)
			return ;

		Integer rst = TlvWrap.readInteter(content, TagDefine.TAG_PACKAGE_COMMAND_SEQUENCE, 4);
		if ( rst != null && rst != 0 && rst <= ctlst.size())
		{
			CommandTlv ct = ctlst.get(rst-1);
			ct.onAckArrive(ack, content);
		}
		else if ( rst != null && rst == 0 )
			ctlst.clear();
	}

	private void build()
	{
		if ( build == true )
			return ;
		build = true ;
		
		int l = 0 ;
		for ( byte[] cmd : cmdlst)
			l += cmd.length;
		byte[] rc = new byte[l];
		
		int index = 0 ;
		for ( byte[] cmd : cmdlst)
		{
			System.arraycopy(cmd, 0, rc, index, cmd.length);
			index += cmd.length;
		}
		
		this.addUnit(new TlvByteUnit(TagDefine.TAG_PACKAGE_COMMAND , rc));
	}
	
	@Override
	public int getTotalLength()
	{
		build();
		return super.getTotalLength();
	}

	@Override
	public byte[] getByte()
	{
		build();
		return super.getByte();
	}
	
	
}
