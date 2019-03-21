package com.iremote.infraredtrans.tlv;

public class TlvByteArrayUnit implements ITLVUnit {

	private byte[] content;
	
	public TlvByteArrayUnit(byte[] content) {
		super();
		this.content = content;
	}

	@Override
	public int getTotalLength() {
		return content.length;
	}

	@Override
	public byte[] getByte() {
		return content;
	}

	@Override
	public int getTag()
	{
		return 0;
	}
}
