package com.iremote.infraredtrans.tlv;

public class TlvIntArray implements ITLVUnit
{
	private int tag;
	private int length ;
	private int[] value;
	
	public TlvIntArray(int tag, int[] value, int length)
	{
		super();
		this.tag = tag;
		this.length = length;
		this.value = value;
		if ( this.value == null )
			this.value = new int[0];
	}

	@Override
	public int getTag()
	{
		return tag;
	}

	@Override
	public int getTotalLength()
	{
		return length * value.length + TlvWrap.TAGLENGTH_LENGTH;
	}

	@Override
	public byte[] getByte()
	{
		byte[] content = new byte[getTotalLength()];
		TlvWrap.writeTag(content, tag);
		TlvWrap.writeLength(content, length * value.length);
		for ( int i = 0 ; i < value.length ; i ++ )
			TlvWrap.writeInt(content, length * i + TlvWrap.TAGLENGTH_LENGTH, value[i], length);
		return content;
	}

}
