package com.iremote.infraredtrans.tlv;

import java.util.ArrayList;
import java.util.List;

public class TlvArrayUnit implements ITLVUnit {

	protected int tag;
	protected List<ITLVUnit> lst = new ArrayList<ITLVUnit>();
	
	public TlvArrayUnit(int tag) {
		super();
		this.tag = tag;
	}

	@Override
	public byte[] getByte() 
	{
		int l = getTotalLength();
		byte[] b = new byte[l];
		int index = TlvWrap.TAGLENGTH_LENGTH;
		for ( ITLVUnit t : lst )
		{
			byte[] ub = t.getByte();
			int ul = t.getTotalLength();
			System.arraycopy(ub, 0, b, index, ul);
			index += ul ;
		}
		TlvWrap.writeTag(b, tag);
		TlvWrap.writeLength(b, l - TlvWrap.TAGLENGTH_LENGTH);
		return b;
	}

	@Override
	public int getTotalLength() {
		int l = 0 ;
		
		for ( ITLVUnit t : lst )
			l += t.getTotalLength();
		return l + TlvWrap.TAGLENGTH_LENGTH;
	}
	
	public void addUnit(ITLVUnit tlv)
	{
		lst.add(tlv);
	}
	
	public void addOrReplaceUnit(ITLVUnit tlv)
	{
		removeUnitByTag(tlv.getTag());
		this.addUnit(tlv);
	}
	
	public void removeUnitByTag(int tag)
	{
		ITLVUnit t = null ;
		for ( ITLVUnit tt : lst)
		{
			if ( tt.getTag() != 0 && tt.getTag() == tag)
			{
				t = tt ;
				break;
			}
		}
		if ( t != null )
			lst.remove(t);
	}
	
	public void removeAllUnitByTag(int tag)
	{
		List<ITLVUnit> rl = new ArrayList<ITLVUnit>() ;
		for ( ITLVUnit tt : lst)
		{
			if ( tt.getTag() != 0 && tt.getTag() == tag)
			{
				rl.add(tt);
				break;
			}
		}
		if ( rl.size() > 0 )
			lst.removeAll(rl);
	}
	
	public ITLVUnit getUnitbyTag(int tag)
	{
		for ( ITLVUnit tt : lst)
		{
			if ( tt.getTag() == tag )
				return tt ;
		}
		return null ;

	}
	
	public byte[] getByteAryValueByTag(int tag)
	{
		ITLVUnit tlv = getUnitbyTag(tag);
		if ( tlv == null )
			return null ;
		
		byte[] cmd = tlv.getByte();
		return TlvWrap.getValue(cmd, 0);
	}
	
	public Integer getIntegerByTag(int tag)
	{
		ITLVUnit tlv = getUnitbyTag(tag);
		if ( tlv == null )
			return null ;
		
		byte[] cmd = tlv.getByte();
		return TlvWrap.readInteter(cmd, tag, 0);
	}

	@Override
	public int getTag()
	{
		return tag;
	}

}
