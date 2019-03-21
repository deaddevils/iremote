package com.iremote.infraredtrans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.asycresponse.IAsyncResponse;
import com.iremote.common.constant.GatewayConnectionStatus;

public class Remoter {

	private static Log log = LogFactory.getLog(Remoter.class);
	
	private GatewayConnectionStatus status = GatewayConnectionStatus.connected;
	private String uuid;
	private String token = "";
	private String token2 = "";
	private int requestCount = 0 ;
	private AtomicInteger sequence = new AtomicInteger(1);
	private int softversion = 1 ;
	private int reportsequence = 0 ;
	private int time1;
	private int time2;
	private byte[] key1 ;
	private byte[] key2 ;
	private byte[] key3 ;
	private int heartBeatPushTagCount = IRemoteConstantDefine.HEART_BEAT_PUSH_TAG_MAX_COUNT;
	private long nextLongMsgHeartBeatTime = System.currentTimeMillis() - 100;
	private int network;
	private int networkIntensity;

	private Map<Integer , AsyncResponseWrap> synchronizeObject = new ConcurrentHashMap<Integer , AsyncResponseWrap>();

	public int getSequence() 
	{
		int s = sequence.getAndIncrement();
		if ( s >=250 )
			sequence.set(1);
		return s ;
	}

	public int getRequestCount() {
		return requestCount;
	}
	public void setRequestCount(int requestCount) {
		this.requestCount = requestCount;
	}
	public boolean isHaslogin() 
	{
		return this.status == GatewayConnectionStatus.hasLogin;
	}
	public void setHaslogin(boolean haslogin) 
	{
		if ( haslogin )
			this.status = GatewayConnectionStatus.hasLogin;
		else 
			this.status = GatewayConnectionStatus.connected;
	}
	public String getUuid() 
	{
		if ( StringUtils.isNotBlank(uuid))
			return uuid ;
		return getToken();
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	public void addSynchronizeObject(int sequence,IAsyncResponse svo)
	{
		synchronizeObject.put(sequence, new AsyncResponseWrap(svo));
	}
	
	public void clearSynchronizeObject()
	{
		long et = System.currentTimeMillis();
		
		List<Integer> rl = new ArrayList<Integer>();
		for ( Integer key : synchronizeObject.keySet() )
		{
			AsyncResponseWrap arw = synchronizeObject.get(key);
			if ( arw.getExpiretime() < et )
				rl.add(key);
		}
		
		for ( Integer key : rl )
		{	
			synchronizeObject.remove(key);
			log.trace(key);
		}
	}
	
	public IAsyncResponse getSynchronizeObject(int sequence)
	{
		AsyncResponseWrap arw = synchronizeObject.get(sequence);
		if ( arw == null )
			return null ;
		return arw.getAr();
	}
	
	public int getSoftversion() {
		return softversion;
	}
	public void setSoftversion(int softversion) {
		this.softversion = softversion;
	}
	public int getReportsequence() {
		return reportsequence;
	}
	public void setReportsequence(int reportsequence) {
		this.reportsequence = reportsequence;
	}
	public byte[] getKey1() {
		return key1;
	}

	public void setKey1(byte[] key1) {
		this.key1 = key1;
	}

	public byte[] getKey2() {
		return key2;
	}

	public void setKey2(byte[] key2) {
		this.key2 = key2;
	}

	public byte[] getKey3() {
		return key3;
	}

	public void setKey3(byte[] key3) {
		this.key3 = key3;
	}

	public int getTime1() {
		return time1;
	}

	public void setTime1(int time1) {
		this.time1 = time1;
	}

	public int getTime2() {
		return time2;
	}

	public void setTime2(int time2) {
		this.time2 = time2;
	}

	public String getToken2() {
		return token2;
	}

	public void setToken2(String token2) {
		this.token2 = token2;
	}
	
	private static class AsyncResponseWrap
	{
		private IAsyncResponse ar ;
		private long expiretime ;
		
		public AsyncResponseWrap(IAsyncResponse ar)
		{
			super();
			this.ar = ar;
			expiretime = System.currentTimeMillis() + IRemoteConstantDefine.DEFAULT_TIME_OUT_SECOND * 5 * 1000;
		}
		public IAsyncResponse getAr()
		{
			return ar;
		}
		public long getExpiretime()
		{
			return expiretime;
		}
		
		
	}

	public int getHeartBeatPushTagCount() {
		return heartBeatPushTagCount;
	}

	public void setHeartBeatPushTagCount(int heartBeatPushTagCount) {
		this.heartBeatPushTagCount = heartBeatPushTagCount;
	}

	public long getNextLongMsgHeartBeatTime() {
		return nextLongMsgHeartBeatTime;
	}

	public void setNextLongMsgHeartBeatTime(long nextLongMsgHeartBeatTime) {
		this.nextLongMsgHeartBeatTime = nextLongMsgHeartBeatTime;
	}

	public GatewayConnectionStatus getStatus() {
		return status;
	}

	public void setStatus(GatewayConnectionStatus status) {
		this.status = status;
	}

	public int getNetwork() {
		return network;
	}

	public void setNetwork(int network) {
		this.network = network;
	}

	public int getNetworkIntensity() {
		return networkIntensity;
	}

	public void setNetworkIntensity(int networkIntensity) {
		this.networkIntensity = networkIntensity;
	}
}
