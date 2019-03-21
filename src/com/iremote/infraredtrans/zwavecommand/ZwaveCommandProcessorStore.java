package com.iremote.infraredtrans.zwavecommand;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.device.operate.zwavedevice.GarageDoorStatusChangeProcessor;
import com.iremote.infraredtrans.zwavecommand.bgm.BGMReportProcessor;
import com.iremote.infraredtrans.zwavecommand.bgm.BGMVolumnReportProcessor;
import com.iremote.infraredtrans.zwavecommand.doorlock.*;
import com.iremote.infraredtrans.zwavecommand.electricfence.*;
import com.iremote.infraredtrans.zwavecommand.freshair.FreshAirFanModeReportProcessor;
import com.iremote.infraredtrans.zwavecommand.freshair.FreshAirModeReportProcessor;
import com.iremote.infraredtrans.zwavecommand.passthrough.PassThroughDeviceInfraredStudyProcessor;
import com.iremote.infraredtrans.zwavecommand.passthrough.PassThroughDeviceStatusProcessor;
import com.iremote.infraredtrans.zwavecommand.plasmadryer.PlasmaDryerStatusReportProcessor;
import com.iremote.infraredtrans.zwavecommand.plasmadryer.PlasmaDryerTemperatureReportProcessor;
import com.iremote.infraredtrans.zwavecommand.plasmadryer.PlasmaDryerWorkModeReportProcessor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ZwaveCommandProcessorStore 
{
	private static Log log = LogFactory.getLog(ZwaveCommandProcessorStore.class);
	
	private static Map<Byte[] , Class<? extends IZwaveReportProcessor>> clsmap
	 	= new HashMap<Byte[] , Class<? extends IZwaveReportProcessor>>();
	private static Map<String , List<Byte[]>> bytemap = new HashMap<String , List<Byte[]> >();
	private final String DEFAULT_TYPE = "defaulttype";
	
	private static ZwaveCommandProcessorStore instance = new ZwaveCommandProcessorStore();

	public static ZwaveCommandProcessorStore getInstance()
	{
		return instance ; 
	}
	
	protected ZwaveCommandProcessorStore()
	{
		registProcessor(new Byte[]{0x26 , 0x03}, MultiLevelSwitchReportProcessor.class);

		registProcessor(new Byte[]{0x5b , 0x03}, SOSAlarmCentralSceneReportProcessor.class);
		
		registProcessor(new Byte[]{0x71 , 0x05}, TamperAlarmReportProcessor.class);
		registProcessor(new Byte[]{0x72 , 0x05}, ZWaveManufactureReportProcessor.class);
		registProcessor(new Byte[]{(byte)0x80 , 0x03}, BatteryReportProcessor.class);
		registProcessor(new Byte[]{(byte)0x9c , 0x02}, AlarmReportProcessor.class);

		registProcessor(new Byte[]{0x25 , 0x03},BinarySwitchReportProcessor.class);

		//smoke
		registProcessor(new Byte[]{0x71, 0x05, 0x00, 0x00, 0x00, (byte) 0xff, 0x01, 0x02}, IRemoteConstantDefine.DEVICE_TYPE_SMOKE, NotificationReport2Processor.class);
		registProcessor(new Byte[]{0x71, 0x05, 0x00, 0x00, 0x00, (byte) 0xff, null, 0x00}, IRemoteConstantDefine.DEVICE_TYPE_SMOKE, NotificationRestoreReportProcessor.class);

		//gas
		registProcessor(new Byte[]{0x71, 0x05, 0x00, 0x00, 0x00, (byte) 0xff, 0x02, 0x02}, IRemoteConstantDefine.DEVICE_TYPE_GAS, NotificationReport2Processor.class);
		registProcessor(new Byte[]{0x71, 0x05, 0x00, 0x00, 0x00, (byte) 0xff, null, 0x00}, IRemoteConstantDefine.DEVICE_TYPE_GAS, NotificationRestoreReportProcessor.class);

		registProcessor(new Byte[]{0x20 , 0x03} ,IRemoteConstantDefine.DEVICE_TYPE_ALARM , AlarmDeviceAlarmReportProcessor.class);
		registProcessor(new Byte[]{0x25 , 0x03} ,IRemoteConstantDefine.DEVICE_TYPE_ALARM , AlarmDeviceAlarmReportProcessor.class);
  
		registProcessor(new Byte[] {(byte)0x80, 0x07, 0x00, (byte)0xA0, 0x10, 0x01, 0x11},IRemoteConstantDefine.DEVICE_TYPE_FINGERPRING, FingerpringReprotProcessor.class);
		//door sensor
		registProcessor(new Byte[]{0x20 , 0x01} ,IRemoteConstantDefine.DEVICE_TYPE_DOOR_SENSOR , DoorSensorReportProcessor.class);
		registProcessor(new Byte[]{0x30 , 0x03} ,IRemoteConstantDefine.DEVICE_TYPE_DOOR_SENSOR , DoorSensorReportProcessor.class);
		registProcessor(new Byte[]{0x71 , 0x05,null,null,null,(byte)0xff ,0x06} ,IRemoteConstantDefine.DEVICE_TYPE_DOOR_SENSOR , DoorSensorNotificationReportProcessor.class);
		
		//switch
		registProcessor(new Byte[]{0x20 , 0x03}, IRemoteConstantDefine.DEVICE_TYPE_BINARY_SWITCH, BinarySwitchReportProcessor.class);
		registProcessor(new Byte[]{0x25 , 0x03}, IRemoteConstantDefine.DEVICE_TYPE_BINARY_SWITCH,BinarySwitchReportProcessor.class);
		registProcessor(new Byte[]{0x20 , 0x03}, IRemoteConstantDefine.DEVICE_TYPE_2_CHANNEL_BINARY_SWITCH , MultiChannelDeviceBinarySwitchReportProcessor.class);
		registProcessor(new Byte[]{0x25 , 0x03}, IRemoteConstantDefine.DEVICE_TYPE_2_CHANNEL_BINARY_SWITCH , MultiChannelDeviceBinarySwitchReportProcessor.class);
		registProcessor(new Byte[]{0x20 , 0x03}, IRemoteConstantDefine.DEVICE_TYPE_3_CHANNEL_BINARY_SWITCH , MultiChannelDeviceBinarySwitchReportProcessor.class);
		registProcessor(new Byte[]{0x25 , 0x03}, IRemoteConstantDefine.DEVICE_TYPE_3_CHANNEL_BINARY_SWITCH , MultiChannelDeviceBinarySwitchReportProcessor.class);

		registProcessor(new Byte[]{0x20 , 0x03}, IRemoteConstantDefine.DEVICE_TYPE_4_CHANNEL_BINARY_SWITCH , MultiChannelSubDeviceBinarySwitchReportProcessor.class);
		registProcessor(new Byte[]{0x25 , 0x03}, IRemoteConstantDefine.DEVICE_TYPE_POWER_FREE_2_CHANNEL_SWITCH , MultiChannelDeviceBinarySwitchReportProcessor.class);
		registProcessor(new Byte[]{0x25 , 0x03}, IRemoteConstantDefine.DEVICE_TYPE_POWER_FREE_3_CHANNEL_SWITCH , MultiChannelDeviceBinarySwitchReportProcessor.class);
		registProcessor(new Byte[]{0x25 , 0x03}, IRemoteConstantDefine.DEVICE_TYPE_POWER_FREE_4_CHANNEL_SWITCH , MultiChannelDeviceBinarySwitchReportProcessor.class);

		registProcessor(new Byte[]{0x2B, 0x01}, IRemoteConstantDefine.DEVICE_TYPE_SCENE_PANNEL , ScenePannelReportProcessor.class);
		registProcessor(new Byte[]{0x2B, 0x01}, IRemoteConstantDefine.DEVICE_TYPE_2_CHANNEL_SCENE_PANNEL , ScenePannelReportProcessor.class);
		registProcessor(new Byte[]{0x2B, 0x01}, IRemoteConstantDefine.DEVICE_TYPE_3_CHANNEL_SCENE_PANNEL , ScenePannelReportProcessor.class);
		registProcessor(new Byte[]{0x2B, 0x01}, IRemoteConstantDefine.DEVICE_TYPE_4_CHANNEL_SCENE_PANNEL , ScenePannelReportProcessor.class);
		
		registProcessor(new Byte[]{0x2B, 0x01}, IRemoteConstantDefine.DEVICE_TYPE_POWER_FREE_6_CHANNEL_SCENE_PANNEL , ScenePannelReportProcessor.class);

		registProcessor(new Byte[]{0x25, 0x03}, IRemoteConstantDefine.DEVICE_TYPE_2_CHANNEL_SWITCH_SCENE_PANNEL , TwoChannelSwitchScenePanelBinarySwitchReportProcessor.class);
		registProcessor(new Byte[]{0x2B, 0x01}, IRemoteConstantDefine.DEVICE_TYPE_2_CHANNEL_SWITCH_SCENE_PANNEL , ScenePannelReportProcessor.class);
		registProcessor(new Byte[]{0x25, 0x03}, IRemoteConstantDefine.DEVICE_TYPE_3_CHANNEL_SWITCH_SCENE_PANNEL , MultiChannelDeviceBinarySwitchReportProcessor.class);
		registProcessor(new Byte[]{0x2B, 0x01}, IRemoteConstantDefine.DEVICE_TYPE_3_CHANNEL_SWITCH_SCENE_PANNEL , ScenePannelReportProcessor.class);
		registProcessor(new Byte[]{0x25, 0x03}, IRemoteConstantDefine.DEVICE_TYPE_4_CHANNEL_SWITCH_SCENE_PANNEL , MultiChannelDeviceBinarySwitchReportProcessor.class);
		registProcessor(new Byte[]{0x2B, 0x01}, IRemoteConstantDefine.DEVICE_TYPE_4_CHANNEL_SWITCH_SCENE_PANNEL , ScenePannelReportProcessor.class);

		//move 0x70 0x06
		registProcessor(new Byte[]{0x30 , 0x03}, IRemoteConstantDefine.DEVICE_TYPE_MOVE, BinarySensorReportProcessor.class);
		registProcessor(new Byte[]{0x31 , 0x05}, IRemoteConstantDefine.DEVICE_TYPE_MOVE, MultLevelSensorReportProcessor.class);
		registProcessor(new Byte[]{0x71 , 0x05 , null , null, null, (byte)0xff, 0x07, 0x00}, IRemoteConstantDefine.DEVICE_TYPE_MOVE, MoveNotificationReportProcessor.class);
		registProcessor(new Byte[]{0x71 , 0x05 , null , null, null, (byte)0xff, 0x07, 0x08}, IRemoteConstantDefine.DEVICE_TYPE_MOVE, MoveNotificationReportProcessor.class);
		registProcessor(new Byte[]{0x70 , 0x06 , 1 }, IRemoteConstantDefine.DEVICE_TYPE_MOVE, NEOSensitivityReportProcessor.class);
		registProcessor(new Byte[]{0x70 , 0x06 , 2 }, IRemoteConstantDefine.DEVICE_TYPE_MOVE, NEOSilentsecondsReportProcessor.class);
		//Leedarson
		registProcessor(new Byte[]{0x70 , 0x06 , 0x0c }, IRemoteConstantDefine.DEVICE_TYPE_MOVE, LeedarsonSensitivityReportProcessor.class);
		registProcessor(new Byte[]{0x70 , 0x06 , 0x0d }, IRemoteConstantDefine.DEVICE_TYPE_MOVE, LeedarsonSilentsecondsReportProcessor.class);
		
		registProcessor(new Byte[]{0x20 , 0x03}, IRemoteConstantDefine.DEVICE_TYPE_OUTLET, BinarySwitchReportProcessor.class);
		registProcessor(new Byte[]{0x32 , 0x02}, IRemoteConstantDefine.DEVICE_TYPE_OUTLET, MeterReportProcessor.class);
		registProcessor(new Byte[]{0x71 , 0x05}, IRemoteConstantDefine.DEVICE_TYPE_OUTLET, NotificationReportProcessor.class);
		
		//meter
		registProcessor(new Byte[]{0x32 , 0x02, (byte)0x01,(byte)0x44}, IRemoteConstantDefine.DEVICE_TYPE_TUJIA_ELECTRIC_METER, ElectricEnergyReportProcessor.class);
		registProcessor(new Byte[]{0x32 , 0x02 ,(byte)0x81,(byte)0x4c}, IRemoteConstantDefine.DEVICE_TYPE_TUJIA_ELECTRIC_METER, ElectricCurrentReportProcessor.class);
		
		registProcessor(new Byte[]{0x32 , 0x02, (byte)0x03,(byte)0x44}, IRemoteConstantDefine.DEVICE_TYPE_WATER_METER, WaterReportProcessor.class);
		
		//air conditioner
		registProcessor(new Byte[]{0x31 , 0x05}, IRemoteConstantDefine.DEVICE_TYPE_AIR_CONDITIONER, AirCondtionerEnvTemperatureProcessor.class);
		registProcessor(new Byte[]{0x40 , 0x03}, IRemoteConstantDefine.DEVICE_TYPE_AIR_CONDITIONER, AirConditionerProcessor.class);
		registProcessor(new Byte[]{0x43 , 0x03}, IRemoteConstantDefine.DEVICE_TYPE_AIR_CONDITIONER, AirCondtionerTemperatureProcessor.class);
		registProcessor(new Byte[]{0x44 , 0x03}, IRemoteConstantDefine.DEVICE_TYPE_AIR_CONDITIONER, AirCondtionerfanProcessor.class);
		
		//karaok
		registProcessor(new Byte[]{0x71 , 0x05}, IRemoteConstantDefine.DEVICE_TYPE_KARAOK, null); // ignore this report ;
		registProcessor(new Byte[]{0x26, 0x03}, IRemoteConstantDefine.DEVICE_TYPE_KARAOK, KaraokVolumeReporteProcessor.class);
		registProcessor(new Byte[]{0x2b, (byte)0xcb}, IRemoteConstantDefine.DEVICE_TYPE_KARAOK, KaraokModeReporteProcessor.class);
		
		//new wind
		registProcessor(new Byte[]{0x25, 0x03}, IRemoteConstantDefine.DEVICE_TYPE_NEW_WIND, NewWindReporteProcessor.class);

		//door lock
		registProcessor(new Byte[]{0x71 , 0x05 , null , null , null , (byte)0xff , 0x08 , null}, StandardZwaveBatteryReportProcessor.class);

		registProcessor(new Byte[]{0x25 , 0x03}, IRemoteConstantDefine.DEVICE_TYPE_DOOR_LOCK , DoorbellRingReportProcessor.class);
		registProcessor(new Byte[]{0x62 , 0x03}, IRemoteConstantDefine.DEVICE_TYPE_DOOR_LOCK ,DoorlockReportProcessor.class);

		registProcessor(new Byte[]{0x63, 0x03, null, 0x01}, IRemoteConstantDefine.DEVICE_TYPE_DOOR_LOCK, DoorlockPasswordUserCheckProccess.class);
		registProcessor(new Byte[]{0x62 , 0x03}, IRemoteConstantDefine.DEVICE_TYPE_LOCK_CYLINDER ,DoorlockReportProcessor.class);
		
		registProcessor(new Byte[]{0x71 , 0x05 , 0x00 , 0x00 , 0x00 , (byte)0xff , 0x05 , 0x02} , IRemoteConstantDefine.DEVICE_TYPE_WATER, WaterLeakReportProcessor.class);
		registProcessor(new Byte[]{0x71 , 0x05 , 0x00 , 0x00 , 0x00 , (byte)0xff , 0x05 , 0x00} , IRemoteConstantDefine.DEVICE_TYPE_WATER, WaterLeakRestoreReportProcessor.class);
		
		registProcessor(new Byte[]{0x71 , 0x05 , null , null , null , (byte)0xff , 0x06 , 0x10} , IRemoteConstantDefine.DEVICE_TYPE_DOOR_LOCK, DoorlockPasswordErrorAlarmProcessor.class);
		registProcessor(new Byte[]{0x71 , 0x05 , null , null , null , (byte)0xff , 0x06 , 0x01} , IRemoteConstantDefine.DEVICE_TYPE_DOOR_LOCK, DoorlockCloseReportProcessor.class);
		registProcessor(new Byte[]{0x71 , 0x05 , null , null , null , (byte)0xff , 0x06 , 0x03} , IRemoteConstantDefine.DEVICE_TYPE_DOOR_LOCK, DoorlockCloseReportProcessor.class);
		registProcessor(new Byte[]{0x71 , 0x05 , null , null , null , (byte)0xff , 0x06 , 0x05} , IRemoteConstantDefine.DEVICE_TYPE_DOOR_LOCK, DoorlockCloseReportProcessor.class);
		registProcessor(new Byte[]{0x71 , 0x05 , null , null , null , (byte)0xff , 0x06 , 0x09} , IRemoteConstantDefine.DEVICE_TYPE_DOOR_LOCK, DoorlockCloseReportProcessor.class);
		registProcessor(new Byte[]{0x71 , 0x05 , null , null , null , (byte)0xff , 0x06 , 0x02} , IRemoteConstantDefine.DEVICE_TYPE_DOOR_LOCK, 	DoorlockOpenReportProcessor.class);
		registProcessor(new Byte[]{0x71 , 0x05 , null , null , null , (byte)0xff , 0x06 , 0x04} , IRemoteConstantDefine.DEVICE_TYPE_DOOR_LOCK, DoorlockOpenReportProcessor.class);
		registProcessor(new Byte[]{0x71 , 0x05 , null , null , null , (byte)0xff , 0x06 , 0x06} , IRemoteConstantDefine.DEVICE_TYPE_DOOR_LOCK, DoorlockOpenReportProcessor.class);
	
		registProcessor(new Byte[]{0x71 , 0x05 , null , null , null , (byte)0xff , 0x06 , 0x10} , IRemoteConstantDefine.DEVICE_TYPE_LOCK_CYLINDER, DoorlockPasswordErrorAlarmProcessor.class);
		registProcessor(new Byte[]{0x71 , 0x05 , null , null , null , (byte)0xff , 0x06 , 0x01} , IRemoteConstantDefine.DEVICE_TYPE_LOCK_CYLINDER, DoorlockCloseReportProcessor.class);
		registProcessor(new Byte[]{0x71 , 0x05 , null , null , null , (byte)0xff , 0x06 , 0x03} , IRemoteConstantDefine.DEVICE_TYPE_LOCK_CYLINDER, DoorlockCloseReportProcessor.class);
		registProcessor(new Byte[]{0x71 , 0x05 , null , null , null , (byte)0xff , 0x06 , 0x05} , IRemoteConstantDefine.DEVICE_TYPE_LOCK_CYLINDER, DoorlockCloseReportProcessor.class);
		registProcessor(new Byte[]{0x71 , 0x05 , null , null , null , (byte)0xff , 0x06 , 0x09} , IRemoteConstantDefine.DEVICE_TYPE_LOCK_CYLINDER, DoorlockCloseReportProcessor.class);
		registProcessor(new Byte[]{0x71 , 0x05 , null , null , null , (byte)0xff , 0x06 , 0x02} , IRemoteConstantDefine.DEVICE_TYPE_LOCK_CYLINDER, LockCylinderOpenReportProcessor.class);
		registProcessor(new Byte[]{0x71 , 0x05 , null , null , null , (byte)0xff , 0x06 , 0x04} , IRemoteConstantDefine.DEVICE_TYPE_LOCK_CYLINDER, LockCylinderOpenReportProcessor.class);
		registProcessor(new Byte[]{0x71 , 0x05 , null , null , null , (byte)0xff , 0x06 , 0x06} , IRemoteConstantDefine.DEVICE_TYPE_LOCK_CYLINDER, LockCylinderOpenReportProcessor.class);

		registProcessor(new Byte[]{(byte)0x80 , 0x07 , 0x00 , (byte)0x80, 0x10 , 0x01 , 0x0A}, IRemoteConstantDefine.DEVICE_TYPE_DOOR_LOCK, DeleteDoorlockPasswordUserProcessor.class);
		registProcessor(new Byte[]{(byte)0x80 , 0x07 , 0x00 , (byte)0xA0, 0x10 , 0x01 , 0x0A}, IRemoteConstantDefine.DEVICE_TYPE_DOOR_LOCK, DeleteDoorlockFingerprintUserProcessor.class);
		registProcessor(new Byte[]{(byte)0x80 , 0x07 , 0x00 , (byte)0x80, 0x10 , 0x01 , 0x03}, IRemoteConstantDefine.DEVICE_TYPE_DOOR_LOCK, AddDoorlockPasswordUserProcessor.class);
		registProcessor(new Byte[]{(byte)0x80 , 0x07 , 0x00 , (byte)0xA0, 0x10 , 0x01 , 0x0c}, IRemoteConstantDefine.DEVICE_TYPE_DOOR_LOCK, AddDoorlockFingerprintUserProcessor.class);
		registProcessor(new Byte[]{(byte)0x80 , 0x07 , 0x00 , (byte)0xA0, 0x10 , 0x01 , 0x08}, IRemoteConstantDefine.DEVICE_TYPE_DOOR_LOCK, AddDoorlockFingerprintUserProcessor.class);
		
		registProcessor(new Byte[]{(byte)0x80 , 0x07 , 0x00 , (byte)0xE0}, IRemoteConstantDefine.DEVICE_TYPE_DOOR_LOCK, DoorlockSelfDefineWaringReportProcessor.class);
		registProcessor(new Byte[]{(byte)0x80 , 0x07 , 0x00 , (byte)0xE0}, IRemoteConstantDefine.DEVICE_TYPE_LOCK_CYLINDER, DoorlockSelfDefineWaringReportProcessor.class);
		
		registProcessor(new Byte[]{(byte)0x80 , 0x07 , 0x00 , (byte)0xB0,null,null,null,null,0x15}, IRemoteConstantDefine.DEVICE_TYPE_DOOR_LOCK, DoorlockSelfDefineOpenwithPasswordReportProcessor.class);
		registProcessor(new Byte[]{(byte)0x80 , 0x07 , 0x00 , (byte)0xB0,null,null,null,null,0x20}, IRemoteConstantDefine.DEVICE_TYPE_DOOR_LOCK, DoorlockSelfDefineOpenwithCardReportProcessor.class);
		registProcessor(new Byte[]{(byte)0x80 , 0x07 , 0x00 , (byte)0xB1,null,null,null,null,0x00}, IRemoteConstantDefine.DEVICE_TYPE_DOOR_LOCK, DoorlockSelfDefineOpenwithPwandnoIdReportProcessor.class);
		registProcessor(new Byte[]{(byte)0x80 , 0x07 , 0x00 , (byte)0xB1,null,null,null,null,0x15}, IRemoteConstantDefine.DEVICE_TYPE_DOOR_LOCK, DoorlockSelfDefineOpenwithPwandnoIdReportProcessor.class);
		registProcessor(new Byte[]{(byte)0x80 , 0x07 , 0x00 , (byte)0xB1,null,null,null,null,0x16}, IRemoteConstantDefine.DEVICE_TYPE_DOOR_LOCK, DoorlockSelfDefineOpenwithPwandnoIdReportProcessor.class);
		registProcessor(new Byte[]{(byte)0x80 , 0x07 , 0x00 , (byte)0xB1,null,null,null,null,0x20}, IRemoteConstantDefine.DEVICE_TYPE_DOOR_LOCK, DoorlockSelfDefineOpenwithPwandnoIdReportProcessor.class);
		registProcessor(new Byte[]{(byte)0x80 , 0x07 , 0x00 , (byte)0xB1,null,null,null,null,0x27}, IRemoteConstantDefine.DEVICE_TYPE_DOOR_LOCK, DoorlockSelfDefineOpenwithPwandnoIdReportProcessor.class);
		registProcessor(new Byte[]{(byte)0x80 , 0x07 , 0x00 , (byte)0xB1,0x10,0x01,0x03,0x00,0x28}, IRemoteConstantDefine.DEVICE_TYPE_DOOR_LOCK, DoorlockOpenInsideReportProcessor.class);
		registProcessor(new Byte[]{(byte)0x80 , 0x07 , 0x00 , (byte)0xB1,null,null,null,null,0x29}, IRemoteConstantDefine.DEVICE_TYPE_DOOR_LOCK, DoorlockSelfDefineOpenwithPwandnoIdReportProcessor.class);

		registProcessor(new Byte[]{(byte)0x80,0x07,0x00,(byte)0xF0}, IRemoteConstantDefine.DEVICE_TYPE_DOOR_LOCK, DoorlockTimeRequestProcessor.class);
		
		registProcessor(new Byte[]{(byte)0x80,0x09,0x07}, IRemoteConstantDefine.DEVICE_TYPE_DOOR_LOCK, DoorlockOpenbyCardReportProcessor.class);
		registProcessor(new Byte[]{(byte)0x80,0x09,0x07}, IRemoteConstantDefine.DEVICE_TYPE_DOOR_CONTROL, DoorlockOpenbyCardReportProcessor.class);

		registProcessor(new Byte[]{(byte)0x80 , 0x07 , 0x00 , (byte)0xB1,0x10,0x01,0x03,0x00,0x21}, IRemoteConstantDefine.DEVICE_TYPE_DOOR_LOCK, DoorlockDoubleAuthReportProcessor.class);
		registProcessor(new Byte[]{(byte)0x80 , 0x07 , 0x00 , (byte)0xB1,0x10,0x01,0x03,0x00,0x22}, IRemoteConstantDefine.DEVICE_TYPE_DOOR_LOCK, DoorlockDoubleAuthReportProcessor.class);
		registProcessor(new Byte[]{(byte)0x80 , 0x07 , 0x00 , (byte)0xB1,0x10,0x01,0x03,0x00,0x23}, IRemoteConstantDefine.DEVICE_TYPE_DOOR_LOCK, DoorlockDoubleAuthReportProcessor.class);
		registProcessor(new Byte[]{(byte)0x80 , 0x07 , 0x00 , (byte)0xB1,0x10,0x01,0x03,0x00,0x24}, IRemoteConstantDefine.DEVICE_TYPE_DOOR_LOCK, DoorlockDoubleAuthReportProcessor.class);
		registProcessor(new Byte[]{(byte)0x80 , 0x07 , 0x00 , (byte)0xB1,0x10,0x01,0x03,0x00,0x25}, IRemoteConstantDefine.DEVICE_TYPE_DOOR_LOCK, DoorlockDoubleAuthReportProcessor.class);
		registProcessor(new Byte[]{(byte)0x80 , 0x07 , 0x00 , (byte)0xB1,0x10,0x01,0x03,0x00,0x26}, IRemoteConstantDefine.DEVICE_TYPE_DOOR_LOCK, DoorlockDoubleAuthReportProcessor.class);

		registProcessor(new Byte[]{(byte)0x80, 0x07, 0x00, 0x60, 0x10, 0x01, 0x02, 0x01}, IRemoteConstantDefine.DEVICE_TYPE_DOOR_LOCK, DoorlockResetProcessor.class);
		registProcessor(new Byte[]{(byte)0x80 , 0x07 , 0x00 , (byte)0x60,0x10,0x01,0x04}, IRemoteConstantDefine.DEVICE_TYPE_DOOR_LOCK, DoorlockVersionReportProcessor.class);

		registProcessor(new Byte[]{(byte)0x80, 0x07, 0x00, 0x60, 0x10, 0x01, 0x08, 0x00}, IRemoteConstantDefine.DEVICE_TYPE_DOOR_LOCK, DoorlockForbidRemoteOpen.class);
		registProcessor(new Byte[]{(byte)0x80, 0x07, 0x00, 0x60, 0x10, 0x01, 0x08, 0x01}, IRemoteConstantDefine.DEVICE_TYPE_DOOR_LOCK, DoorlockAllowRemoteOpen.class);

		//sos
		registProcessor(new Byte[]{0x71 , 0x05} , IRemoteConstantDefine.DEVICE_TYPE_SOS_ALARM, SOSAlarmReportProcessor.class);

		registProcessor(new Byte[]{0x20 , 0x01} ,IRemoteConstantDefine.DEVICE_TYPE_PEEPHOLE , PeepholeReportProcessor.class);
		
		registProcessor(new Byte[]{0x31 , (byte)0xF0} ,IRemoteConstantDefine.DEVICE_TYPE_AIR_QUALITY , AirQualityProcessor.class);
		
		registProcessor(new Byte[]{0x25 , 0x03}, IRemoteConstantDefine.DEVICE_TYPE_DEHUMIDIFY , DehumidifyReportProcessor.class);
		registProcessor(new Byte[]{0x26 , 0x03}, IRemoteConstantDefine.DEVICE_TYPE_DEHUMIDIFY , DehumidifyTemperatureReportProcessor.class);
		registProcessor(new Byte[]{0x31 , 0x05 , 0x01}, IRemoteConstantDefine.DEVICE_TYPE_DEHUMIDIFY, AirCondtionerEnvTemperatureProcessor.class);
		registProcessor(new Byte[]{0x31 , 0x05 , 0x05}, IRemoteConstantDefine.DEVICE_TYPE_DEHUMIDIFY, EnvHumidityReportProcessor.class);
		
		registProcessor(new Byte[]{0x40 , 0x03}, IRemoteConstantDefine.DEVICE_TYPE_FRESH_AIR_IN , FreshAirModeReportProcessor.class);
		registProcessor(new Byte[]{0x44 , 0x03}, IRemoteConstantDefine.DEVICE_TYPE_FRESH_AIR_IN , FreshAirFanModeReportProcessor.class);
		registProcessor(new Byte[]{0x31 , 0x05 , 0x01}, IRemoteConstantDefine.DEVICE_TYPE_FRESH_AIR_IN, AirCondtionerEnvTemperatureProcessor.class);
		registProcessor(new Byte[]{0x31 , 0x05 , 0x05}, IRemoteConstantDefine.DEVICE_TYPE_FRESH_AIR_IN, EnvHumidityReportProcessor.class);
		
		registProcessor(new Byte[]{0x26 , 0x03}, IRemoteConstantDefine.DEVICE_TYPE_BGM, BGMVolumnReportProcessor.class);
		registProcessor(new Byte[]{(byte)0x91 , 0x02 , 0x1c }, IRemoteConstantDefine.DEVICE_TYPE_BGM, BGMReportProcessor.class);
		
		registProcessor(new Byte[]{0x20 , 0x03} ,IRemoteConstantDefine.DEVICE_TYPE_ARM_STATUS_SETTER , ArmStatusSetterReportProcessor.class);

		registProcessor(new Byte[]{0x26 , 0x03} ,IRemoteConstantDefine.DEVICE_TYPE_RGB_COLOR_SWITCH , ColorSwitchReportProcessor.class);
		registProcessor(new Byte[]{0x33 , (byte)0xA4} ,IRemoteConstantDefine.DEVICE_TYPE_RGB_COLOR_SWITCH , ColorSwitchSelfdefineReportProcessor.class);
		
		//registProcessor(new Byte[]{0x25 , 0x03} ,IRemoteConstantDefine.DEVICE_TYPE_DSC , DSCArmStatusReportProcessor.class);
		//registProcessor(new Byte[]{0x30 , 0x03} ,IRemoteConstantDefine.DEVICE_TYPE_DSC , DSCAlarmReportProcessor.class);

		registProcessor(new Byte[]{0x43 , 0x03} ,IRemoteConstantDefine.DEVICE_TYPE_HEATING_CONTROLLER , HeatingControllerTemperatureProcessor.class);
		registProcessor(new Byte[]{0x31 , 0x05 , 0x01}, IRemoteConstantDefine.DEVICE_TYPE_HEATING_CONTROLLER, AirCondtionerEnvTemperatureProcessor.class);

		registProcessor(new Byte[]{(byte)0x86 , 0x12} , DeviceVersionReporteProcessor.class);

		//garage door
		registProcessor(new Byte[]{0x66, 0x03},IRemoteConstantDefine.DEVICE_TYPE_GARAGEDOOR, GarageDoorStatusChangeProcessor.class);

		//WallReader
		registProcessor(new Byte[]{0x25 , 0x03}, IRemoteConstantDefine.DEVICE_TYPE_WALLREADER , DoorbellRingReportProcessor.class);
		registProcessor(new Byte[]{0x62 , 0x03}, IRemoteConstantDefine.DEVICE_TYPE_WALLREADER ,DoorlockReportProcessor.class);

		registProcessor(new Byte[]{0x63, 0x03, null, 0x01}, IRemoteConstantDefine.DEVICE_TYPE_WALLREADER, DoorlockPasswordUserCheckProccess.class);

		//Electric fence
		registProcessor(new Byte[]{0x20, 0x03}, IRemoteConstantDefine.DEVICE_TYPE_ELECTRIC_FENCE, ElectricFenceArmStatusReportProcessor.class );
		registProcessor(new Byte[]{0x20, 0x03}, IRemoteConstantDefine.DEVICE_TYPE_2_CHANNEL_ELECTRIC_FENCE, TwoChannelElectricFenceArmStatusReportProcessor.class);
		registProcessor(new Byte[]{(byte)0x80, 0x07, 0x00, (byte)0xE0, 0x10, 0x01, 0x07}, IRemoteConstantDefine.DEVICE_TYPE_ELECTRIC_FENCE, ElectricFenceWarningStatusReportProcessor.class);
		registProcessor(new Byte[]{(byte)0x80, 0x07, 0x00, (byte)0xE0, 0x10, 0x01, 0x07}, IRemoteConstantDefine.DEVICE_TYPE_2_CHANNEL_ELECTRIC_FENCE, ElectricFenceWarningStatusReportProcessor.class);
		registProcessor(new Byte[]{0x70, 0x06, null, 0x02}, IRemoteConstantDefine.DEVICE_TYPE_ELECTRIC_FENCE, ElectricFenceParameterReportProcessor.class );
		registProcessor(new Byte[]{0x70, 0x06, null, 0x02}, IRemoteConstantDefine.DEVICE_TYPE_2_CHANNEL_ELECTRIC_FENCE, TwoChannelElectricFenceParameterReportProcessor.class);
		registProcessor(new Byte[]{0x31, 0x05, 0x0f, 0x02}, IRemoteConstantDefine.DEVICE_TYPE_ELECTRIC_FENCE, ElectricFenceVoltageReportProcessor.class );
		registProcessor(new Byte[]{0x31, 0x05, 0x0f, 0x02}, IRemoteConstantDefine.DEVICE_TYPE_2_CHANNEL_ELECTRIC_FENCE, TwoChannelElectricFenceVoltageReportProcessor.class);
		registProcessor(new Byte[]{0x70, 0x09, null, null, null, 0x00, 0x02}, IRemoteConstantDefine.DEVICE_TYPE_ELECTRIC_FENCE, ElectricFenceParameterBatchReportProcessor.class );
		registProcessor(new Byte[]{0x70, 0x09, null, null, null, 0x00, 0x02}, IRemoteConstantDefine.DEVICE_TYPE_2_CHANNEL_ELECTRIC_FENCE, TwoChannelElectricFenceParameterBatchReportProcessor.class);

		registProcessor(new Byte[]{0x71 , 0x05 , null , null , null , (byte)0xff , 0x06 , 0x10} , IRemoteConstantDefine.DEVICE_TYPE_WALLREADER, DoorlockPasswordErrorAlarmProcessor.class);
		registProcessor(new Byte[]{0x71 , 0x05 , null , null , null , (byte)0xff , 0x06 , 0x01} , IRemoteConstantDefine.DEVICE_TYPE_WALLREADER, DoorlockCloseReportProcessor.class);
		registProcessor(new Byte[]{0x71 , 0x05 , null , null , null , (byte)0xff , 0x06 , 0x03} , IRemoteConstantDefine.DEVICE_TYPE_WALLREADER, DoorlockCloseReportProcessor.class);
		registProcessor(new Byte[]{0x71 , 0x05 , null , null , null , (byte)0xff , 0x06 , 0x05} , IRemoteConstantDefine.DEVICE_TYPE_WALLREADER, DoorlockCloseReportProcessor.class);
		registProcessor(new Byte[]{0x71 , 0x05 , null , null , null , (byte)0xff , 0x06 , 0x09} , IRemoteConstantDefine.DEVICE_TYPE_WALLREADER, DoorlockCloseReportProcessor.class);
		registProcessor(new Byte[]{0x71 , 0x05 , null , null , null , (byte)0xff , 0x06 , 0x02} , IRemoteConstantDefine.DEVICE_TYPE_WALLREADER, 	DoorlockOpenReportProcessor.class);
		registProcessor(new Byte[]{0x71 , 0x05 , null , null , null , (byte)0xff , 0x06 , 0x04} , IRemoteConstantDefine.DEVICE_TYPE_WALLREADER, DoorlockOpenReportProcessor.class);
		registProcessor(new Byte[]{0x71 , 0x05 , null , null , null , (byte)0xff , 0x06 , 0x06} , IRemoteConstantDefine.DEVICE_TYPE_WALLREADER, DoorlockOpenReportProcessor.class);

		registProcessor(new Byte[]{(byte)0x80 , 0x07 , 0x00 , (byte)0x80, 0x10 , 0x01 , 0x0A}, IRemoteConstantDefine.DEVICE_TYPE_WALLREADER, DeleteDoorlockPasswordUserProcessor.class);
		registProcessor(new Byte[]{(byte)0x80 , 0x07 , 0x00 , (byte)0xA0, 0x10 , 0x01 , 0x0A}, IRemoteConstantDefine.DEVICE_TYPE_WALLREADER, DeleteDoorlockFingerprintUserProcessor.class);
		registProcessor(new Byte[]{(byte)0x80 , 0x07 , 0x00 , (byte)0x80, 0x10 , 0x01 , 0x03}, IRemoteConstantDefine.DEVICE_TYPE_WALLREADER, AddDoorlockPasswordUserProcessor.class);
		registProcessor(new Byte[]{(byte)0x80 , 0x07 , 0x00 , (byte)0xA0, 0x10 , 0x01 , 0x0c}, IRemoteConstantDefine.DEVICE_TYPE_WALLREADER, AddDoorlockFingerprintUserProcessor.class);
		registProcessor(new Byte[]{(byte)0x80 , 0x07 , 0x00 , (byte)0xA0, 0x10 , 0x01 , 0x08}, IRemoteConstantDefine.DEVICE_TYPE_WALLREADER, AddDoorlockFingerprintUserProcessor.class);

		registProcessor(new Byte[]{(byte)0x80 , 0x07 , 0x00 , (byte)0xE0}, IRemoteConstantDefine.DEVICE_TYPE_WALLREADER, DoorlockSelfDefineWaringReportProcessor.class);

		registProcessor(new Byte[]{(byte)0x80 , 0x07 , 0x00 , (byte)0xB0,null,null,null,null,0x15}, IRemoteConstantDefine.DEVICE_TYPE_WALLREADER, DoorlockSelfDefineOpenwithPasswordReportProcessor.class);
		registProcessor(new Byte[]{(byte)0x80 , 0x07 , 0x00 , (byte)0xB0,null,null,null,null,0x20}, IRemoteConstantDefine.DEVICE_TYPE_WALLREADER, DoorlockSelfDefineOpenwithCardReportProcessor.class);
		registProcessor(new Byte[]{(byte)0x80 , 0x07 , 0x00 , (byte)0xB1,null,null,null,null,0x00}, IRemoteConstantDefine.DEVICE_TYPE_WALLREADER, DoorlockSelfDefineOpenwithPwandnoIdReportProcessor.class);
		registProcessor(new Byte[]{(byte)0x80 , 0x07 , 0x00 , (byte)0xB1,null,null,null,null,0x15}, IRemoteConstantDefine.DEVICE_TYPE_WALLREADER, DoorlockSelfDefineOpenwithPwandnoIdReportProcessor.class);
		registProcessor(new Byte[]{(byte)0x80 , 0x07 , 0x00 , (byte)0xB1,null,null,null,null,0x16}, IRemoteConstantDefine.DEVICE_TYPE_WALLREADER, DoorlockSelfDefineOpenwithPwandnoIdReportProcessor.class);
		registProcessor(new Byte[]{(byte)0x80 , 0x07 , 0x00 , (byte)0xB1,null,null,null,null,0x20}, IRemoteConstantDefine.DEVICE_TYPE_WALLREADER, DoorlockSelfDefineOpenwithPwandnoIdReportProcessor.class);
		registProcessor(new Byte[]{(byte)0x80 , 0x07 , 0x00 , (byte)0xB1,null,null,null,null,0x27}, IRemoteConstantDefine.DEVICE_TYPE_WALLREADER, DoorlockSelfDefineOpenwithPwandnoIdReportProcessor.class);
		registProcessor(new Byte[]{(byte)0x80 , 0x07 , 0x00 , (byte)0xB1,0x10,0x01,0x03,0x00,0x28}, IRemoteConstantDefine.DEVICE_TYPE_WALLREADER, DoorlockOpenInsideReportProcessor.class);

		registProcessor(new Byte[]{(byte)0x80,0x07,0x00,(byte)0xF0}, IRemoteConstantDefine.DEVICE_TYPE_WALLREADER, DoorlockTimeRequestProcessor.class);

		registProcessor(new Byte[]{(byte)0x80,0x09,0x07}, IRemoteConstantDefine.DEVICE_TYPE_WALLREADER, DoorlockOpenbyCardReportProcessor.class);

		registProcessor(new Byte[]{(byte)0x80 , 0x07 , 0x00 , (byte)0xB1,0x10,0x01,0x03,0x00,0x21}, IRemoteConstantDefine.DEVICE_TYPE_WALLREADER, DoorlockDoubleAuthReportProcessor.class);
		registProcessor(new Byte[]{(byte)0x80 , 0x07 , 0x00 , (byte)0xB1,0x10,0x01,0x03,0x00,0x22}, IRemoteConstantDefine.DEVICE_TYPE_WALLREADER, DoorlockDoubleAuthReportProcessor.class);
		registProcessor(new Byte[]{(byte)0x80 , 0x07 , 0x00 , (byte)0xB1,0x10,0x01,0x03,0x00,0x23}, IRemoteConstantDefine.DEVICE_TYPE_WALLREADER, DoorlockDoubleAuthReportProcessor.class);
		registProcessor(new Byte[]{(byte)0x80 , 0x07 , 0x00 , (byte)0xB1,0x10,0x01,0x03,0x00,0x24}, IRemoteConstantDefine.DEVICE_TYPE_WALLREADER, DoorlockDoubleAuthReportProcessor.class);
		registProcessor(new Byte[]{(byte)0x80 , 0x07 , 0x00 , (byte)0xB1,0x10,0x01,0x03,0x00,0x25}, IRemoteConstantDefine.DEVICE_TYPE_WALLREADER, DoorlockDoubleAuthReportProcessor.class);
		registProcessor(new Byte[]{(byte)0x80 , 0x07 , 0x00 , (byte)0xB1,0x10,0x01,0x03,0x00,0x26}, IRemoteConstantDefine.DEVICE_TYPE_WALLREADER, DoorlockDoubleAuthReportProcessor.class);

		registProcessor(new Byte[]{(byte)0x80 , 0x07 , 0x00 , (byte)0x60,0x10,0x01,0x04}, IRemoteConstantDefine.DEVICE_TYPE_WALLREADER, DoorlockVersionReportProcessor.class);

		registProcessor(new Byte[]{(byte)0x80, 0x07, 0x00, 0x60, 0x10, 0x01, 0x08, 0x00}, IRemoteConstantDefine.DEVICE_TYPE_WALLREADER, DoorlockForbidRemoteOpen.class);
		registProcessor(new Byte[]{(byte)0x80, 0x07, 0x00, 0x60, 0x10, 0x01, 0x08, 0x01}, IRemoteConstantDefine.DEVICE_TYPE_WALLREADER, DoorlockAllowRemoteOpen.class);

		registProcessor(new Byte[]{(byte)0x91, 0x02, 0x1c, 0x01, 0x03}, IRemoteConstantDefine.DEVICE_TYPE_PASS_THROUGH_DEVICE, PassThroughDeviceStatusProcessor.class);
		registProcessor(new Byte[]{(byte)0x91, 0x02, 0x1c, 0x01, 0x03}, IRemoteConstantDefine.DEVICE_TYPE_INFRARED_PASS_THROUGH_DEVICE, PassThroughDeviceStatusProcessor.class);
		registProcessor(new Byte[]{(byte)0x91, 0x02, 0x1c, 0x01, 0x06}, IRemoteConstantDefine.DEVICE_TYPE_INFRARED_PASS_THROUGH_DEVICE, PassThroughDeviceInfraredStudyProcessor.class);

		registProcessor(new Byte[]{0x20, 0x03}, IRemoteConstantDefine.DEVICE_TYPE_PLASMA_DRYER, PlasmaDryerStatusReportProcessor.class);
		registProcessor(new Byte[]{0x70, 0x06, 0x02, 0x02}, IRemoteConstantDefine.DEVICE_TYPE_PLASMA_DRYER, PlasmaDryerWorkModeReportProcessor.class);
		registProcessor(new Byte[]{0x31, 0x05, 0x01, 0x01}, IRemoteConstantDefine.DEVICE_TYPE_PLASMA_DRYER, PlasmaDryerTemperatureReportProcessor.class);

		registProcessor(new Byte[]{0x25 , 0x03}, IRemoteConstantDefine.DEVICE_TYPE_4_CHANNEL_BINARY_SWITCH , MultiChannelSubDeviceBinarySwitchReportProcessor.class);

		registProcessor(new Byte[]{0x31 , 0x05 , 0x01}, IRemoteConstantDefine.DEVICE_TYPE_DRESS_HELPER, AirCondtionerEnvTemperatureProcessor.class);
		registProcessor(new Byte[]{0x31 , 0x05 , 0x05}, IRemoteConstantDefine.DEVICE_TYPE_DRESS_HELPER, EnvHumidityReportProcessor.class);

		registProcessor(new Byte[]{0x25, 0x03}, IRemoteConstantDefine.DEVICE_TYPE_PLASMA_DRYER, PlasmaDryerStatusReportProcessor.class);
		registProcessor(new Byte[]{0x70, 0x06, 0x02, 0x02}, IRemoteConstantDefine.DEVICE_TYPE_PLASMA_DRYER, PlasmaDryerWorkModeReportProcessor.class);
		registProcessor(new Byte[]{0x31, 0x05, 0x01}, IRemoteConstantDefine.DEVICE_TYPE_PLASMA_DRYER, PlasmaDryerTemperatureReportProcessor.class);

		registProcessor(new Byte[]{0x25 , 0x03}, IRemoteConstantDefine.DEVICE_TYPE_4_CHANNEL_BINARY_SWITCH , MultiChannelSubDeviceBinarySwitchReportProcessor.class);
		registProcessor(new Byte[]{0x20 , 0x03}, IRemoteConstantDefine.DEVICE_TYPE_4_CHANNEL_BINARY_SWITCH , MultiChannelSubDeviceBinarySwitchReportProcessor.class);
	}
	
	public void registProcessor(Byte[] cmd ,  Class<? extends IZwaveReportProcessor> cls)
	{
		registProcessor(cmd , null , cls);
	}
	
	public void registProcessor(Byte[] cmd , String type , Class<? extends IZwaveReportProcessor> cls)
	{
		cmd = copyByteArray(cmd);
		if ( type == null || type.length() == 0 )
			type = DEFAULT_TYPE;
		clsmap.put(cmd, cls);
		
		List<Byte[]> lst = bytemap.get(type);
		if ( lst == null )
		{
			lst = new ArrayList<Byte[]>();
			bytemap.put(type, lst);
		}
		lst.add(cmd);
	}
	
	public IZwaveReportProcessor getProcessor(byte[] cmd, String type)
	{
		List<Byte[]> lst = bytemap.get(type);
		if ( lst == null )
			return getProcessor(cmd , DEFAULT_TYPE);
		for ( Byte[] b : lst )
		{
			if ( isByteMatch(b , cmd))
				return createProcessor(clsmap.get(b));
		}
		if ( !DEFAULT_TYPE.equals(type))
			return getProcessor(cmd , DEFAULT_TYPE);
		else 
		
			return null ;
	}
	
	private boolean isByteMatch(Byte[] key , byte[] cmd )
	{
		if ( key.length > cmd.length )
			return false ;
		for ( int i = 0 ; i < key.length ; i ++ )
			if ( key[i] != null && key[i] != cmd[i] )
				return false ;
		return true;
	}
	
	private Byte[] copyByteArray(Byte[] cmd)
	{
		Byte[] b = new Byte[cmd.length];
		for ( int i = 0 ; i < cmd.length ; i ++ )
			b[i] = cmd[i];
		return b ;
	}
	
	private IZwaveReportProcessor createProcessor(Class<? extends IZwaveReportProcessor> cls)
	{
		if ( cls == null )
			return null ;
		try {
			return cls.newInstance();
		} catch (InstantiationException e) {
			log.error(e.getMessage() ,e);
		} catch (IllegalAccessException e) {
			log.error(e.getMessage() ,e);
		}
		return null ;
	}

}