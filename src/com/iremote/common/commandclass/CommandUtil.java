package com.iremote.common.commandclass;

import com.alibaba.fastjson.JSONArray;
import com.iremote.common.RGBHSV;
import com.iremote.common.TagDefine;
import com.iremote.common.Utils;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvByteUnit;
import com.iremote.infraredtrans.tlv.TlvIntUnit;
import org.apache.commons.lang3.StringUtils;

public class CommandUtil {
	
	public static CommandTlv createCommandTlv(byte[] command , int nuid)
	{
		CommandTlv ct = new CommandTlv(30 , 7);
		ct.addUnit(new TlvByteUnit(70 , command));
		ct.addUnit(new TlvIntUnit(71 , nuid , getnuIdLenght(nuid)));
		ct.addUnit(new TlvIntUnit(72 , 0 , 1));
		return ct ;  
	}
	
	public static CommandTlv createAlarmCommand(int nuid)
	{
		return createAlarmCommand(nuid , (byte)0xff); 
	}
	
	public static CommandTlv createAlarmCommand(int nuid , byte status)
	{
		CommandTlv ct = new CommandTlv(30 , 7);
		ct.addUnit(new TlvByteUnit(70 , new byte[]{0x20,0x01,status}));
		ct.addUnit(new TlvIntUnit(72 , 0 , 1));
		ct.addUnit(new TlvByteUnit(74 , new byte[]{0x20,0x03,status}));
		ct.addUnit(new TlvIntUnit(71 , nuid , getnuIdLenght(nuid)));
		return ct ;
	}

	public static CommandTlv createGarageDoorCommand(int nuid, byte status)
	{
		CommandTlv ct = new CommandTlv(30 , 7);
		ct.addUnit(new TlvByteUnit(70 , new byte[]{0x66, 0x01,status}));
		ct.addUnit(new TlvIntUnit(72 , 0 , 1));
		if ((status & 0xff) == 255) {
			ct.addUnit(new TlvByteUnit(74, new byte[]{0x66, 0x03, (byte) 0xfe}));
		}
		ct.addUnit(new TlvIntUnit(71 , nuid , getnuIdLenght(nuid)));
		return ct ;
	}

	public static CommandTlv createPlasmaDryerPowerCommand(int nuid, byte status)
	{
		CommandTlv ct = new CommandTlv(30 , 7);
		ct.addUnit(new TlvByteUnit(70 , new byte[]{0x25, 0x01, status}));
		ct.addUnit(new TlvIntUnit(72 , 0 , 1));
		ct.addUnit(new TlvByteUnit(74, new byte[]{0x25, 0x03, status}));
		ct.addUnit(new TlvIntUnit(71 , nuid , getnuIdLenght(nuid)));
		return ct ;
	}

	public static CommandTlv createPlasmaDryerModeCommand(int nuid, byte mode, byte duration)
	{
		CommandTlv ct = new CommandTlv(30 , 7);
		ct.addUnit(new TlvByteUnit(70 , new byte[]{0x70, 0x04, 0x02, 0x02, mode, duration}));
		ct.addUnit(new TlvIntUnit(72 , 0 , 1));
		ct.addUnit(new TlvByteUnit(74, new byte[]{0x70, 0x06, 0x02, 0x02, mode, duration}));
		ct.addUnit(new TlvIntUnit(71 , nuid , getnuIdLenght(nuid)));
		return ct ;
	}

	public static CommandTlv createPlasmaDryerModeCommand(int nuid, Integer mode, Integer duration, String statuses) {
		if (mode == null) {
			mode = getStatuses(statuses, 0);
		}
		if (duration == null) {
			duration = getStatuses(statuses, 1);
		}

		return createPlasmaDryerModeCommand(nuid, mode.byteValue(), duration.byteValue());
	}

	private static int getStatuses(String statuses, int index) {
		JSONArray jsonArray = JSONArray.parseArray(statuses);
		int status = jsonArray.getIntValue(index);
		return status != -1 ? status : 1;
	}


	public static CommandTlv createCalibrateCommand(int nuid){
		CommandTlv ct = new CommandTlv(30 , 7);
		ct.addUnit(new TlvByteUnit(70 , new byte[]{0x70,0x04,0x09,0x02,0x00,0x01}));
		ct.addUnit(new TlvIntUnit(72 , 0 , 1));
		ct.addUnit(new TlvIntUnit(71 , nuid , getnuIdLenght(nuid)));
		return ct;
	}
	public static CommandTlv createCalibrateCommand(int nuid,byte channel){
		CommandTlv ct = new CommandTlv(30 , 7);
		ct.addUnit(new TlvByteUnit(70 , new byte[]{0x60,0x0d,0x00,channel,0x70,0x04,0x09,0x02,0x00,0x01}));
		ct.addUnit(new TlvIntUnit(72 , 0 , 1));
		ct.addUnit(new TlvIntUnit(71 , nuid , getnuIdLenght(nuid)));
		return ct;
	}
	public static CommandTlv createLowVoltageCommand(int nuid, byte b, byte c){
		CommandTlv ct = new CommandTlv(30 , 7);
		ct.addUnit(new TlvByteUnit(70 , new byte[]{0x70,0x04,0x08,0x02,b,c}));
		ct.addUnit(new TlvIntUnit(72 , 0 , 1));
		ct.addUnit(new TlvByteUnit(74 , new byte[]{0x70,0x06,0x08,0x02,b,c}));
		ct.addUnit(new TlvIntUnit(71 , nuid , getnuIdLenght(nuid)));
		return ct;
	}
	public static CommandTlv createLowVoltageCommand(int nuid,byte channel, byte b, byte c){
		CommandTlv ct = new CommandTlv(30 , 7);
		ct.addUnit(new TlvByteUnit(70 , new byte[]{0x60,0x0d,0x00,channel,0x70,0x04,0x08,0x02,b,c}));
		ct.addUnit(new TlvIntUnit(72 , 0 , 1));
		ct.addUnit(new TlvByteUnit(74 , new byte[]{0x60,0x0d,channel,0x00,0x70,0x06,0x08,0x02,b,c}));
		ct.addUnit(new TlvIntUnit(71 , nuid , getnuIdLenght(nuid)));
		return ct;
	}
	public static CommandTlv createBatchConfigCommand(int nuid,int voltagechoose,int frequency,
			int sensitivity,int baojingmenxian,int delaytimes,int alarmtimes,int highvoltage){
		CommandTlv ct = new CommandTlv(30 , 7);
		ct.addUnit(new TlvByteUnit(70 , new byte[]{0x70,0x07,0x00,0x01,0x07,0x02,
		0x00,(byte)voltagechoose,0x00,(byte)frequency,0x00,(byte)sensitivity,0x00,(byte)baojingmenxian,
		0x00,(byte)delaytimes,(byte)(alarmtimes/256),(byte)(alarmtimes%256),(byte)(highvoltage/256),(byte)(highvoltage%256)}));
		ct.addUnit(new TlvIntUnit(72 , 0 , 1));
		ct.addUnit(new TlvByteUnit(74 , new byte[]{0x70,0x09,0x00,0x01,0x07,0x00,0x02,
				0x00,(byte)voltagechoose,0x00,(byte)frequency,0x00,(byte)sensitivity,0x00,(byte)baojingmenxian,
				0x00,(byte)delaytimes,(byte)(alarmtimes/256),(byte)(alarmtimes%256),(byte)(highvoltage/256),(byte)(highvoltage%256)}));
		ct.addUnit(new TlvIntUnit(71 , nuid , getnuIdLenght(nuid)));
		return ct;
	}
	public static CommandTlv createBatchConfigCommand(int nuid,byte channel,int voltagechoose,int frequency,
			int sensitivity,int baojingmenxian,int delaytimes,int alarmtimes,int highvoltage){
		CommandTlv ct = new CommandTlv(30 , 7);
		ct.addUnit(new TlvByteUnit(70 , new byte[]{0x60,0x0d,0x00,channel,0x70,0x07,0x00,0x01,0x07,0x02,
		0x00,(byte)voltagechoose,0x00,(byte)frequency,0x00,(byte)sensitivity,0x00,(byte)baojingmenxian,
		0x00,(byte)delaytimes,(byte)(alarmtimes/256),(byte)(alarmtimes%256),(byte)(highvoltage/256),(byte)(highvoltage%256)}));
		ct.addUnit(new TlvIntUnit(72 , 0 , 1));
		ct.addUnit(new TlvByteUnit(74 , new byte[]{0x60,0x0d,channel,0x00,0x70,0x09,0x00,0x01,0x07,0x00,0x02,
				0x00,(byte)voltagechoose,0x00,(byte)frequency,0x00,(byte)sensitivity,0x00,(byte)baojingmenxian,
				0x00,(byte)delaytimes,(byte)(alarmtimes/256),(byte)(alarmtimes%256),(byte)(highvoltage/256),(byte)(highvoltage%256)}));
		ct.addUnit(new TlvIntUnit(71 , nuid , getnuIdLenght(nuid)));
		return ct;
	}
	//createReadEfanceConfigCommand
	public static CommandTlv createBatchReadEfanceConfigCommand(int nuid){
		CommandTlv ct = new CommandTlv(30 , 7);
		ct.addUnit(new TlvByteUnit(70 , new byte[]{0x70,0x08,0x00,0x01,0x07}));
		ct.addUnit(new TlvIntUnit(72 , 0 , 1));
		ct.addUnit(new TlvIntUnit(71 , nuid , getnuIdLenght(nuid)));
		return ct;
	}
	public static CommandTlv createBatchReadEfanceConfigCommand(int nuid,int channel){
		CommandTlv ct = new CommandTlv(30 , 7);
		ct.addUnit(new TlvByteUnit(70 , new byte[]{0x60,0x0d,0x00,(byte)channel,0x70,0x08,0x00,0x01,0x07}));
		ct.addUnit(new TlvIntUnit(72 , 0 , 1));
		ct.addUnit(new TlvIntUnit(71 , nuid , getnuIdLenght(nuid)));
		return ct;
	}
	public static CommandTlv createReadEfanceConfigCommand(int nuid){
		CommandTlv ct = new CommandTlv(30 , 7);
		ct.addUnit(new TlvByteUnit(70 , new byte[]{0x70,0x05,0x08}));
		ct.addUnit(new TlvIntUnit(72 , 0 , 1));
		ct.addUnit(new TlvIntUnit(71 , nuid , getnuIdLenght(nuid)));
		return ct;
	}
	public static CommandTlv createReadEfanceConfigCommand(int nuid,int channel){
		CommandTlv ct = new CommandTlv(30 , 7);
		ct.addUnit(new TlvByteUnit(70 , new byte[]{0x60,0x0d,0x00,(byte)channel,0x70,0x05,0x08}));
		ct.addUnit(new TlvIntUnit(72 , 0 , 1));
		ct.addUnit(new TlvIntUnit(71 , nuid , getnuIdLenght(nuid)));
		return ct;
	}
	public static CommandTlv createReadEfanceVolageCommand(int nuid){
		CommandTlv ct = new CommandTlv(30 , 7);
		ct.addUnit(new TlvByteUnit(70 , new byte[]{0x31,0x04,0x0f,0x00}));
		ct.addUnit(new TlvIntUnit(72 , 0 , 1));
		ct.addUnit(new TlvIntUnit(71 , nuid , getnuIdLenght(nuid)));
		return ct;
	}
	public static CommandTlv createReadEfanceVolageCommand(int nuid,int channel){
		CommandTlv ct = new CommandTlv(30 , 7);
		ct.addUnit(new TlvByteUnit(70 , new byte[]{0x60,0x0d,0x00,(byte)channel,0x31,0x04,0x0f,0x00}));
		ct.addUnit(new TlvIntUnit(72 , 0 , 1));
		ct.addUnit(new TlvIntUnit(71 , nuid , getnuIdLenght(nuid)));
		return ct;
	}
	public static CommandTlv createSetNEOSensitivityCommand(int nuid, byte sensitivity){
		CommandTlv ct = new CommandTlv(30 , 7);
		ct.addUnit(new TlvByteUnit(70 , new byte[]{0x70,0x04,1,1,sensitivity}));
		ct.addUnit(new TlvIntUnit(72 , 1 , 1));
		ct.addUnit(new TlvIntUnit(71 , nuid , getnuIdLenght(nuid)));
		return ct;
	}
	
	public static CommandTlv createSetNEOSilentsecondsCommand(int nuid, int silentseconds){
		CommandTlv ct = new CommandTlv(30 , 7);
		ct.addUnit(new TlvByteUnit(70 , new byte[]{0x70,0x04,2,2,(byte)(silentseconds/256),(byte)(silentseconds%256)}));
		ct.addUnit(new TlvIntUnit(72 , 1 , 1));
		ct.addUnit(new TlvIntUnit(71 , nuid , getnuIdLenght(nuid)));
		return ct;
	}
	public static CommandTlv createGetNEOSensitivityCommand(int nuid){
		CommandTlv ct = new CommandTlv(30 , 7);
		ct.addUnit(new TlvByteUnit(70 , new byte[]{0x70,0x05,1}));
		ct.addUnit(new TlvIntUnit(72 , 1 , 1));
		ct.addUnit(new TlvIntUnit(71 , nuid , getnuIdLenght(nuid)));
		return ct;
	}
	public static CommandTlv createGetNEOSilentsecondsCommand(int nuid){
		CommandTlv ct = new CommandTlv(30 , 7);
		ct.addUnit(new TlvByteUnit(70 , new byte[]{0x70,0x05,2}));
		ct.addUnit(new TlvIntUnit(72 , 1 , 1));
		ct.addUnit(new TlvIntUnit(71 , nuid , getnuIdLenght(nuid)));
		return ct;
	}
	
	public static CommandTlv createSetLeedarsonSensitivityCommand(int nuid, byte sensitivity){
		CommandTlv ct = new CommandTlv(30 , 7);
		ct.addUnit(new TlvByteUnit(70 , new byte[]{0x70,0x04,0x0c,1,sensitivity}));
		ct.addUnit(new TlvIntUnit(72 , 1 , 1));
		ct.addUnit(new TlvIntUnit(71 , nuid , getnuIdLenght(nuid)));
		return ct;
	}
	
	public static CommandTlv createSetLeedarsonSilentsecondsCommand(int nuid, int silentseconds){
		CommandTlv ct = new CommandTlv(30 , 7);
		ct.addUnit(new TlvByteUnit(70 , new byte[]{0x70,0x04,0x0d,2,(byte)(silentseconds/256),(byte)(silentseconds%256)}));
		ct.addUnit(new TlvIntUnit(72 , 1 , 1));
		ct.addUnit(new TlvIntUnit(71 , nuid , getnuIdLenght(nuid)));
		return ct;
	}
	public static CommandTlv createGetLeedarsonSensitivityCommand(int nuid){
		CommandTlv ct = new CommandTlv(30 , 7);
		ct.addUnit(new TlvByteUnit(70 , new byte[]{0x70,0x05,0x0c}));
		ct.addUnit(new TlvIntUnit(72 , 1 , 1));
		ct.addUnit(new TlvIntUnit(71 , nuid , getnuIdLenght(nuid)));
		return ct;
	}
	public static CommandTlv createGetLeedarsonSilentsecondsCommand(int nuid){
		CommandTlv ct = new CommandTlv(30 , 7);
		ct.addUnit(new TlvByteUnit(70 , new byte[]{0x70,0x05,0x0d}));
		ct.addUnit(new TlvIntUnit(72 , 1 , 1));
		ct.addUnit(new TlvIntUnit(71 , nuid , getnuIdLenght(nuid)));
		return ct;
	}
	
	public static CommandTlv createSetSirenSoundCommand(int nuid, byte sound){
		CommandTlv ct = new CommandTlv(30 , 7);
		ct.addUnit(new TlvByteUnit(70 , new byte[]{0x70,0x04,0x01,1,sound}));
		ct.addUnit(new TlvIntUnit(72 , 0 , 1));
		ct.addUnit(new TlvIntUnit(71 , nuid , getnuIdLenght(nuid)));
		return ct;
	}
	public static CommandTlv createSetSirenTimeCommand(int nuid, byte time){
		CommandTlv ct = new CommandTlv(30 , 7);
		ct.addUnit(new TlvByteUnit(70 , new byte[]{0x70,0x04,0x02,1,time}));
		ct.addUnit(new TlvIntUnit(72 , 0 , 1));
		ct.addUnit(new TlvIntUnit(71 , nuid , getnuIdLenght(nuid)));
		return ct;
	}
	public static CommandTlv createSetSirenMusicCommand(int nuid, byte music){
		CommandTlv ct = new CommandTlv(30 , 7);
		ct.addUnit(new TlvByteUnit(70 , new byte[]{0x70,0x04,0x05,1,music}));
		ct.addUnit(new TlvIntUnit(72 , 0 , 1));
		ct.addUnit(new TlvIntUnit(71 , nuid , getnuIdLenght(nuid)));
		return ct;
	}
	
	public static  CommandTlv createUnalarmCommand(int nuid)
	{
		CommandTlv ct = new CommandTlv(30 , 7);
		ct.addUnit(new TlvByteUnit(70 , new byte[]{0x20,0x01,(byte)0x00}));
		ct.addUnit(new TlvIntUnit(72 , 0 , 1));
		ct.addUnit(new TlvByteUnit(74 , new byte[]{0x20,0x03,(byte)0x00}));
		ct.addUnit(new TlvIntUnit(71 , nuid , getnuIdLenght(nuid)));
		return ct ;
	}
	public static  CommandTlv createEfance1UnalarmCommand(int nuid)
	{
		CommandTlv ct = new CommandTlv(30 , 7);
		ct.addUnit(new TlvByteUnit(70 , new byte[]{0x20,0x01,(byte)0xfe}));
		ct.addUnit(new TlvIntUnit(72 , 0 , 1));
		//ct.addUnit(new TlvByteUnit(74 , new byte[]{0x20,0x03,(byte)0xfe}));
		ct.addUnit(new TlvIntUnit(71 , nuid , getnuIdLenght(nuid)));
		return ct ;
	}
	public static  CommandTlv createEfance2UnalarmCommand(int nuid,byte channel)
	{
		CommandTlv ct = new CommandTlv(30 , 7);
		ct.addUnit(new TlvByteUnit(70 , new byte[]{0x60,0x0d,00,channel,0x20,0x01,(byte)0xfe}));
		ct.addUnit(new TlvIntUnit(72 , 0 , 1));
		//ct.addUnit(new TlvByteUnit(74 , new byte[]{0x60,0x0d,00,channelindex,0x20,0x03,(byte)0xfe}));
		ct.addUnit(new TlvIntUnit(71 , nuid , getnuIdLenght(nuid)));
		return ct ;
	}
	
	public static CommandTlv createBasicSetCommand(int nuid , byte status)
	{
		CommandTlv ct = new CommandTlv(30 , 7);
		ct.addUnit(new TlvByteUnit(70 , new byte[]{0x20,0x01,status}));
		ct.addUnit(new TlvIntUnit(72 , 0 , 1));
		ct.addUnit(new TlvIntUnit(71 , nuid , getnuIdLenght(nuid)));
		return ct ;
	}
	
	public static CommandTlv createSwitchCommand(int nuid , byte status)
	{
		CommandTlv ct = new CommandTlv(30 , 7);
		
		ct.addUnit(new TlvByteUnit(70 , new byte[]{0x25,0x01,status}));
		ct.addUnit(new TlvIntUnit(72 , 0 , 1));
		ct.addUnit(new TlvByteUnit(74 , new byte[]{0x25,0x03,status}));
		ct.addUnit(new TlvIntUnit(71 , nuid , getnuIdLenght(nuid)));
		return ct ;
	}
	
	public static CommandTlv createSwitchCommand(int nuid , byte channel, byte status )
	{
		if ( channel == 0 )
			return createSwitchCommand(nuid , status);
		
		CommandTlv ct = new CommandTlv(30 , 7);
		
		ct.addUnit(new TlvByteUnit(70 , new byte[]{0x60 , 0x0d , 0 , channel , 0x25,0x01,status}));
		ct.addUnit(new TlvIntUnit(72 , 0 , 1));
		ct.addUnit(new TlvByteUnit(74 , new byte[]{0x60 , 0x0d , channel , 0 , 0x25,0x03,status}));
		ct.addUnit(new TlvIntUnit(71 , nuid , getnuIdLenght(nuid)));
		return ct ;
	}
	
	public static CommandTlv createMultilevelSwitchCommand(int nuid , byte status)
	{
		CommandTlv ct = new CommandTlv(30 , 7);
		
		ct.addUnit(new TlvByteUnit(70 , new byte[]{0x26,0x01,status}));
		ct.addUnit(new TlvIntUnit(72 , 0 , 1));
		ct.addUnit(new TlvByteUnit(74 , new byte[]{0x26,0x03,status}));
		ct.addUnit(new TlvIntUnit(71 , nuid , getnuIdLenght(nuid)));
		return ct ;
	}
	
	public static CommandTlv createMotorStopCommand(int nuid)
	{
		CommandTlv ct = new CommandTlv(30 , 7);
		
		ct.addUnit(new TlvByteUnit(70 , new byte[]{0x26,0x01,(byte)0xff}));
		ct.addUnit(new TlvIntUnit(72 , 0 , 1));
		ct.addUnit(new TlvByteUnit(74 , new byte[]{0x26,0x03,(byte)0xff}));
		ct.addUnit(new TlvIntUnit(71 , nuid , getnuIdLenght(nuid)));
		return ct ;
	}

	
	public static CommandTlv createLockCommand(int nuid , byte status)
	{
		CommandTlv ct = new CommandTlv(30 , 7);
		
		ct.addUnit(new TlvByteUnit(70 , new byte[]{0x62,0x01,status}));
		ct.addUnit(new TlvIntUnit(72 , 0 , 1));
		ct.addUnit(new TlvByteUnit(74 , new byte[]{0x62,0x03,status,0x00, 0x02,(byte)0xfe, (byte)0xfe}));
		ct.addUnit(new TlvIntUnit(71 , nuid , getnuIdLenght(nuid)));
		return ct ;
	}
	
	public static CommandTlv createAirconditionCommand(int nuid , byte status)
	{
		CommandTlv ct = new CommandTlv(30 , 7);
		
		ct.addUnit(new TlvByteUnit(70 , new byte[]{0x40 ,0x01,status}));
		ct.addUnit(new TlvIntUnit(72 , 0 , 1));
		ct.addUnit(new TlvByteUnit(74 , new byte[]{0x40 ,0x03,status}));
		ct.addUnit(new TlvIntUnit(71 , nuid , getnuIdLenght(nuid)));
		return ct ;
	}
	
	public static CommandTlv createAirconditionFanCommand(int nuid , byte status)
	{
		CommandTlv ct = new CommandTlv(30 , 7);
		
		ct.addUnit(new TlvByteUnit(70 , new byte[]{0x44 ,0x01,status}));
		ct.addUnit(new TlvIntUnit(72 , 0 , 1));
		ct.addUnit(new TlvByteUnit(74 , new byte[]{0x44 ,0x03,status}));
		ct.addUnit(new TlvIntUnit(71 , nuid , getnuIdLenght(nuid)));
		return ct ;
	}
	
	public static CommandTlv createAirconditionTemperatureCommand(int nuid , byte mode , byte unit, byte status)
	{
		CommandTlv ct = new CommandTlv(30 , 7);
		
		ct.addUnit(new TlvByteUnit(70 , new byte[]{0x43 ,0x01,mode , unit , status}));
		ct.addUnit(new TlvIntUnit(72 , 0 , 1));
		ct.addUnit(new TlvByteUnit(74 , new byte[]{0x43 ,0x03,mode , unit , status}));
		ct.addUnit(new TlvIntUnit(71 , nuid , getnuIdLenght(nuid)));
		return ct ;  
	}

	public static CommandTlv createHeatingControllerCommand(int nuid , int temperature)
	{
		CommandTlv ct = new CommandTlv(30 , 7);

		ct.addUnit(new TlvByteUnit(70 , new byte[]{0x43 ,0x01,1 , 1 , (byte)temperature}));
		ct.addUnit(new TlvIntUnit(72 , 1 , 1));
		ct.addUnit(new TlvByteUnit(74 , new byte[]{0x43 ,0x03,1 , 1 , (byte)temperature}));
		ct.addUnit(new TlvIntUnit(71 , nuid , getnuIdLenght(nuid)));
		return ct ;
	}

	public static CommandTlv createNewWindPowerCommand(int nuid,int power)
	{
		CommandTlv ct = new CommandTlv(30 , 7);
		ct.addUnit(new TlvByteUnit(70 , new byte[]{(byte)0x60,(byte)0x0D,0x00,0x02,0x25,0x01,(byte)power}));
		ct.addUnit(new TlvIntUnit(72 , 0 , 1));
		ct.addUnit(new TlvByteUnit(74 , new byte[]{(byte)0x60,(byte)0x0D,0x02,0x02,0x25,0x03,(byte)power}));
		ct.addUnit(new TlvIntUnit(71 , nuid , getnuIdLenght(nuid)));
		return ct ;
	}
	
	public static CommandTlv createNewWindFanCommand(int nuid,int fan)
	{
		CommandTlv ct = new CommandTlv(30 , 7);
		ct.addUnit(new TlvByteUnit(70 , new byte[]{(byte)0x60,(byte)0x0D,0x00,0x01,0x25,0x01,(byte)fan}));
		ct.addUnit(new TlvIntUnit(72 , 0 , 1));
		ct.addUnit(new TlvByteUnit(74 , new byte[]{(byte)0x60,(byte)0x0D,0x01,0x01,0x25,0x03,(byte)fan}));
		ct.addUnit(new TlvIntUnit(71 , nuid , getnuIdLenght(nuid)));
		
		return ct ;
	}

	public static CommandTlv createFreshAirFanCommand(int nuid , int channelid , int fan)
	{
		CommandTlv ct = new CommandTlv(30 , 7);
		ct.addUnit(new TlvByteUnit(70 , new byte[]{(byte)0x60,(byte)0x0D,0x00,(byte)channelid,0x44,0x01,(byte)fan}));
		ct.addUnit(new TlvIntUnit(72 , 0 , 1));
		ct.addUnit(new TlvByteUnit(74 , new byte[]{(byte)0x60,(byte)0x0D,(byte)channelid,0x00,0x44,0x03,(byte)fan}));
		ct.addUnit(new TlvIntUnit(71 , nuid , getnuIdLenght(nuid)));
		
		return ct ;
	}
	

	public static CommandTlv createKaraoVolumeCommand(int nuid,int volumeType,int volume)
	{
		CommandTlv ct = new CommandTlv(30 , 7);
		ct.addUnit(new TlvByteUnit(70 , new byte[]{0x60,0x0d,0x00,(byte)volumeType,0x26,0x01,(byte)volume}));
		ct.addUnit(new TlvIntUnit(72 , 0 , 1));
		ct.addUnit(new TlvByteUnit(74 , new byte[]{0x60,0x0d,(byte)volumeType,0x00,0x26,0x03,(byte)volume}));
		ct.addUnit(new TlvIntUnit(71 , nuid , getnuIdLenght(nuid)));
		return ct ;
	}
	public static CommandTlv createKaraoModeCommand(int nuid,int mode)
	{
		CommandTlv ct = new CommandTlv(30 , 7);
		ct.addUnit(new TlvByteUnit(70 , new byte[]{0x2b,0x01,(byte)mode,0x00}));
		ct.addUnit(new TlvIntUnit(72 , 0 , 1));
		ct.addUnit(new TlvByteUnit(74 , new byte[]{0x2b,(byte)0xcb,(byte)mode,0x00}));
		ct.addUnit(new TlvIntUnit(71 , nuid , getnuIdLenght(nuid)));
		
		return ct ;
	}
	public static CommandTlv createKaraoPowereCommand(int nuid,int power)
	{
		CommandTlv ct = new CommandTlv(30 , 7);
		ct.addUnit(new TlvByteUnit(70 , new byte[]{0x25,0x01,(byte)power}));
		ct.addUnit(new TlvIntUnit(72 , 0 , 1));
		ct.addUnit(new TlvByteUnit(74 , new byte[]{0x25,0x03,(byte)power}));
		ct.addUnit(new TlvIntUnit(71 , nuid , getnuIdLenght(nuid)));
		
		return ct ;
	}
	
	public static CommandTlv createDoorlockGetVersionComand(int nuid)
	{
		CommandTlv ct = new CommandTlv(30 , 7);
		ct.addUnit(new TlvIntUnit(71 , nuid , getnuIdLenght(nuid)));
		ct.addUnit(new TlvIntUnit(72 , 0 , 1));
		ct.addUnit(new TlvByteUnit(70 , new byte[]{(byte)0x80,0x07,0x00,(byte)0x60,0x10,0x01,0x03,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00}));
		return ct ;
	}
	
	public static CommandTlv createZWaveManufactureCommand(int nuid)
	{
		CommandTlv ct = new CommandTlv(30 , 7);
		ct.addUnit(new TlvIntUnit(71 , nuid , getnuIdLenght(nuid)));
		ct.addUnit(new TlvIntUnit(72 , 0 , 1));
		ct.addUnit(new TlvByteUnit(70 , new byte[]{(byte)0x72,0x04}));
		return ct ;
	}

	public static CommandTlv CreateColorSwitchCommand(int nuid , int light )
	{
		int orilight = light ;
		light = ((light * 100 ) /255) ;
		if (light == 0 ) light = 1 ;
		if ( light == 100 ) light = 99 ;
	
		CommandTlv ct = new CommandTlv(30 , 7);
		ct.addUnit(new TlvByteUnit(70 , new byte[]{0x26, 0x01 ,(byte)light }));
		ct.addUnit(new TlvIntUnit(71 , nuid , getnuIdLenght(nuid)));
		ct.addUnit(new TlvIntUnit(72 , 0 , 1));
		ct.addUnit(new TlvByteUnit(74 ,createColorSwitchCommand((byte)0x33 , (byte)0xA4 , new Byte[]{null , (byte) orilight} )));
		return ct ;
	}
	
	public static CommandTlv CreateColorSwitchOpenCommand(int nuid , int light )
	{
		CommandTlv ct = new CommandTlv(30 , 7);
		ct.addUnit(new TlvByteUnit(70 , new byte[]{0x26, 0x01 ,(byte)light}));
		ct.addUnit(new TlvIntUnit(71 , nuid , getnuIdLenght(nuid)));
		ct.addUnit(new TlvIntUnit(72 , 0 , 1));
		ct.addUnit(new TlvByteUnit(74 ,new byte[]{0x26, 0x03 ,(byte)light}));
		return ct ;
	}
	
// from mmx	
//	if(temperature<128)
//	{
//	  cold=(255-temperature)*(light/255);
//	  warn=light-cold;
//	}
//	else
//	{
//	  warn=temperature*(light/255);
//	  cold=light-warn;
//	 }
//
//	0x33 05 02 00 warn 01 cold 02
//	0x26 01 light 02
	
//	temperature:warm
//	light:bright
	
	public static CommandTlv createColorSwitchCommand(int nuid , Byte[] params , String devicestatuses)
	{
		int c = 0 ;
		for ( ; c < params.length -1 ; c ++ )
			if ( c != 1 && params[c] != null )
				break ;
		if ( c == params.length -1 )
			return null ;
		Byte temperature = params[0];
		Byte light = params[1];
		
		if ( temperature != null && light == null )
		{
			Integer l = Utils.getJsonArrayValue(devicestatuses , 1);
			if ( l != null )
				light = l.byteValue();
			else 
				light = 0 ;
			params[1] = light;
		}
		
		if ( light != null && temperature == null )
		{
			Integer t = Utils.getJsonArrayValue(devicestatuses , 0);
			if ( t != null )
				temperature = t.byteValue();
			else 
				temperature = 0 ;
			params[0] = temperature;
		}
		
		Integer cold = 0 , warn = 0 ;
		
		if ( temperature != null && light != null  )
		{
			if((temperature & 0xff)<128)
			{
				warn=((255-(temperature & 0xff))*(light & 0xff))/255;
				cold=(light & 0xff)-warn;
			}
			else
			{
				cold=(temperature&0xff)*(light & 0xff)/255;
				warn=(light & 0xff)-cold;
			 }
		}
		
		RGBHSV rh = RGBHSV.rgbtrans(params[2], params[3], params[4], params[10]);
		byte[] bp = null ;
		byte[] bq = null ;
		if ( rh == null )
		{
			if ( params[0] == null )
				params[0] = 0 ;
			if ( params[1] == null )
				params[1] = 0 ;
			
			bq = createColorSwitchCommand((byte)0x33 , (byte)0x05 , new Byte[]{warn.byteValue() , cold.byteValue()} );
			bp = createColorSwitchCommand((byte)0x33 , (byte)0xA4 , new Byte[]{params[0] , params[1]} );
		}
		else 
		{
			bq = createColorSwitchCommand((byte)0x33 , (byte)0x05 , new Byte[]{warn.byteValue() , cold.byteValue() , rh.getR().byteValue() , rh.getG().byteValue() ,rh.getB().byteValue()} );
			bp = createColorSwitchCommand((byte)0x33 , (byte)0xA4 , params );
		}
		
		if ( bp == null || bq == null )
			return null ;
		
		CommandTlv ct = new CommandTlv(30 , 7);
		ct.addUnit(new TlvByteUnit(70 , bq));
		ct.addUnit(new TlvIntUnit(71 , nuid , getnuIdLenght(nuid)));
		ct.addUnit(new TlvIntUnit(72 , 0 , 1));
		ct.addUnit(new TlvByteUnit(74 , bp));
		return ct ;
	}
	
	public static CommandTlv createDscArmCommand(int nuid , byte status , String password)
	{
		CommandTlv ct = new CommandTlv(30 , 7);
		ct.addUnit(new TlvIntUnit(71 , nuid , getnuIdLenght(nuid)));
		ct.addUnit(new TlvByteUnit(70 , new byte[]{(byte)0x25,0x01,status}));
		ct.addUnit(new TlvByteUnit(TagDefine.TAG_PASSWORD , password.getBytes()));
		return ct ;
	}
	
	public static CommandTlv createDscQueryArmStatusCommand(int nuid , String password)
	{
		CommandTlv ct = new CommandTlv(30 , 7);
		ct.addUnit(new TlvIntUnit(71 , nuid , getnuIdLenght(nuid)));
		ct.addUnit(new TlvByteUnit(70 , new byte[]{(byte)0x25,0x02}));
		ct.addUnit(new TlvByteUnit(TagDefine.TAG_PASSWORD , password.getBytes()));
		return ct ;
	}
	
	public static CommandTlv createDscCommand(String command)
	{
        StringBuilder sb = new StringBuilder();
        sb.append(command);

        char[] chars = sb.toString().toCharArray();
        int total = 0;
        for (int i = 0; i < chars.length; i++) 
            total += (int) chars[i];
        
        String string = Integer.toHexString(total);
        if (string.length() > 2) 
            string = string.substring(string.length() - 2);
        
        sb.append(string.toUpperCase());
        
        byte[] bytes = sb.toString().getBytes();
        byte[] endString = new byte[]{0x0D, 0x0A};
        byte[] cmd = new byte[bytes.length + endString.length];
        System.arraycopy(bytes, 0, cmd, 0, bytes.length);
        System.arraycopy(endString, 0, cmd, bytes.length, endString.length);
        
		CommandTlv commandTlv = new CommandTlv(107, 1);
		commandTlv.addUnit(new TlvByteUnit(93, cmd));
		
		return commandTlv;
	}
	

	public static CommandTlv createPauseCommand(int nuid , byte status)
	{
		CommandTlv ct = new CommandTlv(30 , 7);
		//0x91 02 1c 04 01 01/02
		ct.addUnit(new TlvByteUnit(70 , new byte[]{(byte)0x91 ,0x02 ,0x1c ,0x04,0x01,status}));
		ct.addUnit(new TlvIntUnit(72 , 0 , 1));
		ct.addUnit(new TlvByteUnit(74 , new byte[]{(byte)0x91 ,0x02 ,0x1c ,0x04,0x03,status}));
		ct.addUnit(new TlvIntUnit(71 , nuid , getnuIdLenght(nuid)));
		return ct ;
	}
	
	public static CommandTlv createMuteCommand(int nuid , byte status)
	{
		CommandTlv ct = new CommandTlv(30 , 7);
		//0x91 02 1c 05 01 01/02
		ct.addUnit(new TlvByteUnit(70 , new byte[]{(byte)0x91 ,0x02 ,0x1c ,0x05,0x01,status}));
		ct.addUnit(new TlvIntUnit(72 , 0 , 1));
		ct.addUnit(new TlvByteUnit(74 , new byte[]{(byte)0x91 ,0x02 ,0x1c ,0x05,0x03,status}));
		ct.addUnit(new TlvIntUnit(71 , nuid , getnuIdLenght(nuid)));
		return ct ;
	}
	
	public static CommandTlv createVolumnCommand(int nuid , byte status)
	{
		CommandTlv ct = new CommandTlv(30 , 7);
		
		ct.addUnit(new TlvByteUnit(70 , new byte[]{0x26,0x01,status}));
		ct.addUnit(new TlvIntUnit(72 , 0 , 1));
		ct.addUnit(new TlvByteUnit(74 , new byte[]{0x26,0x03,status}));
		ct.addUnit(new TlvIntUnit(71 , nuid , getnuIdLenght(nuid)));
		return ct ;
	}
	
	public static CommandTlv createLooptypeCommand(int nuid , byte status)
	{
		CommandTlv ct = new CommandTlv(30 , 7);
		
		ct.addUnit(new TlvByteUnit(70 , new byte[]{(byte)0x91 ,0x02 ,0x1c ,0x01,0x01,status}));
		ct.addUnit(new TlvIntUnit(72 , 0 , 1));
		ct.addUnit(new TlvByteUnit(74 , new byte[]{(byte)0x91 ,0x02 ,0x1c ,0x01,0x03,status}));
		ct.addUnit(new TlvIntUnit(71 , nuid , getnuIdLenght(nuid)));
		return ct ;
	}

	public static CommandTlv createSourceCommand(int nuid , byte status)
	{
		CommandTlv ct = new CommandTlv(30 , 7);
		
		ct.addUnit(new TlvByteUnit(70 , new byte[]{(byte)0x91 ,0x02 ,0x1c ,0x02,0x01,status}));
		ct.addUnit(new TlvIntUnit(72 , 0 , 1));
		ct.addUnit(new TlvByteUnit(74 , new byte[]{(byte)0x91 ,0x02 ,0x1c ,0x02,0x03,status}));
		ct.addUnit(new TlvIntUnit(71 , nuid , getnuIdLenght(nuid)));
		return ct ;
	}

	public static CommandTlv createSongCommand(int nuid , byte status)
	{
		CommandTlv ct = new CommandTlv(30 , 7);
		
		ct.addUnit(new TlvByteUnit(70 , new byte[]{(byte)0x91 ,0x02 ,0x1c ,0x06,0x01,status}));
		ct.addUnit(new TlvIntUnit(72 , 0 , 1));
		ct.addUnit(new TlvIntUnit(71 , nuid , getnuIdLenght(nuid))); 
		return ct ;
	}

	private static byte[] createColorSwitchCommand(byte commandclass , byte subcommandclass ,Byte[] params)
	{
		byte[] cb = new byte[16];
		cb[0] = commandclass ;
		cb[1] = subcommandclass;
		byte i = 0;
		byte j = 0 ;
		for ( ; i < params.length ; i ++ )
			if ( params[i] != null )
			{
				cb[3+j * 2 ] = i ;
				cb[3+j * 2 +1] = params[i];
				j ++ ;
			}
		if ( j == 0 )
			return null ;
		
		cb[2] = j ;
		byte[] cmd = new byte[3 + j * 2 + 1];
		System.arraycopy(cb, 0, cmd, 0, cmd.length);
		cmd[cmd.length -1] = 0x01 ;
		return cmd ;
	}

	public static CommandTlv createElectricFenceCommand(int nuid, byte status, int channel)
	{
		byte[] command = {0x20, 0x01, status};
		byte[] response = {0x20, 0x03, status};
		if (channel != 0) {
			byte[] commandHead = {0x60, 0x0d, 0x00, (byte)channel, 0, 0, 0};
			byte[] responseHead = {0x60, 0x0d, (byte)channel, 0x00, 0, 0, 0};
			System.arraycopy(command, 0, commandHead, 4, 3);
			System.arraycopy(response, 0, responseHead, 4, 3);
			command = commandHead;
			response = responseHead;
		}
		CommandTlv ct = new CommandTlv(30 , 7);
		ct.addUnit(new TlvByteUnit(70 , command));
		ct.addUnit(new TlvIntUnit(72 , 0 , 1));
		ct.addUnit(new TlvByteUnit(74 , response));
		ct.addUnit(new TlvIntUnit(71 , nuid , getnuIdLenght(nuid)));
		return ct ;
	}

	public static int getnuIdLenght(int nuid)
	{
		if ( nuid >= 10000 )
			return 4 ;
		return 1 ;
	}
}
