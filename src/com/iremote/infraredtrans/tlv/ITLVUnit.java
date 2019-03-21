package com.iremote.infraredtrans.tlv;

public interface ITLVUnit {
	
	int getTag();
	int getTotalLength();
	byte[] getByte();
}
