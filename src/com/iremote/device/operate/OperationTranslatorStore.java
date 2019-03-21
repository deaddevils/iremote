package com.iremote.device.operate;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.processorstore.ClassMapper;
import com.iremote.device.operate.infrareddevice.ACOperationTranslator;
import com.iremote.device.operate.infrareddevice.TvStbOperationTranslator;
import com.iremote.device.operate.zwavedevice.*;

public class OperationTranslatorStore extends ClassMapper<IOperationTranslator> 
{
	private static OperationTranslatorStore instance = new OperationTranslatorStore();

	public static OperationTranslatorStore getInstance()
	{
		return instance ;
	} 
	
	protected OperationTranslatorStore()
	{
		this.registProcessor(makekey(IRemoteConstantDefine.DEVICE_MAJORTYPE_INFRARED , IRemoteConstantDefine.DEVICE_TYPE_AC), ACOperationTranslator.class);
		this.registProcessor(makekey(IRemoteConstantDefine.DEVICE_MAJORTYPE_INFRARED , IRemoteConstantDefine.DEVICE_TYPE_TV), TvStbOperationTranslator.class);
		this.registProcessor(makekey(IRemoteConstantDefine.DEVICE_MAJORTYPE_INFRARED , IRemoteConstantDefine.DEVICE_TYPE_STB), TvStbOperationTranslator.class);
		
		this.registProcessor(makekey(IRemoteConstantDefine.DEVICE_MAJORTYPE_ZWAVE , IRemoteConstantDefine.DEVICE_TYPE_2_CHANNEL_BINARY_SWITCH), MultiChannelBinarySwitchOperationTranslator.class);
		this.registProcessor(makekey(IRemoteConstantDefine.DEVICE_MAJORTYPE_ZWAVE , IRemoteConstantDefine.DEVICE_TYPE_3_CHANNEL_BINARY_SWITCH), MultiChannelBinarySwitchOperationTranslator.class);
		this.registProcessor(makekey(IRemoteConstantDefine.DEVICE_MAJORTYPE_ZWAVE , IRemoteConstantDefine.DEVICE_TYPE_2_CHANNEL_SWITCH_SCENE_PANNEL), SwitchOperationTranslator.class);
		this.registProcessor(makekey(IRemoteConstantDefine.DEVICE_MAJORTYPE_ZWAVE , IRemoteConstantDefine.DEVICE_TYPE_3_CHANNEL_SWITCH_SCENE_PANNEL), MultiChannelBinarySwitchOperationTranslator.class);
		this.registProcessor(makekey(IRemoteConstantDefine.DEVICE_MAJORTYPE_ZWAVE , IRemoteConstantDefine.DEVICE_TYPE_4_CHANNEL_SWITCH_SCENE_PANNEL), MultiChannelBinarySwitchOperationTranslator.class);
	
		this.registProcessor(makekey(IRemoteConstantDefine.DEVICE_MAJORTYPE_ZWAVE , IRemoteConstantDefine.DEVICE_TYPE_SMOKE), SmokeOperationTranslator.class);
		this.registProcessor(makekey(IRemoteConstantDefine.DEVICE_MAJORTYPE_ZWAVE , IRemoteConstantDefine.DEVICE_TYPE_WATER), WaterOperationTranslator.class);
		this.registProcessor(makekey(IRemoteConstantDefine.DEVICE_MAJORTYPE_ZWAVE , IRemoteConstantDefine.DEVICE_TYPE_GAS), GasOperationTranslator.class);
		this.registProcessor(makekey(IRemoteConstantDefine.DEVICE_MAJORTYPE_ZWAVE , IRemoteConstantDefine.DEVICE_TYPE_DOOR_SENSOR), DoorsensorOperationTranslator.class);
		this.registProcessor(makekey(IRemoteConstantDefine.DEVICE_MAJORTYPE_ZWAVE , IRemoteConstantDefine.DEVICE_TYPE_DOOR_LOCK), DoorlockOperationTranslator.class);
		this.registProcessor(makekey(IRemoteConstantDefine.DEVICE_MAJORTYPE_ZWAVE , IRemoteConstantDefine.DEVICE_TYPE_MOVE), MoveOperationTranslator.class);
		this.registProcessor(makekey(IRemoteConstantDefine.DEVICE_MAJORTYPE_ZWAVE , IRemoteConstantDefine.DEVICE_TYPE_BINARY_SWITCH), SwitchOperationTranslator.class);
		this.registProcessor(makekey(IRemoteConstantDefine.DEVICE_MAJORTYPE_ZWAVE , IRemoteConstantDefine.DEVICE_TYPE_OUTLET), SwitchOperationTranslator.class);
		this.registProcessor(makekey(IRemoteConstantDefine.DEVICE_MAJORTYPE_ZWAVE , IRemoteConstantDefine.DEVICE_TYPE_ROBOTIC_ARM), SwitchOperationTranslator.class);
		this.registProcessor(makekey(IRemoteConstantDefine.DEVICE_MAJORTYPE_ZWAVE , IRemoteConstantDefine.DEVICE_TYPE_CURTAIN), CurtainOperationTranslator.class);
		this.registProcessor(makekey(IRemoteConstantDefine.DEVICE_MAJORTYPE_ZWAVE , IRemoteConstantDefine.DEVICE_TYPE_SOS_ALARM), SosOperationTranslator.class);
		this.registProcessor(makekey(IRemoteConstantDefine.DEVICE_MAJORTYPE_ZWAVE , IRemoteConstantDefine.DEVICE_TYPE_PEEPHOLE), SwitchOperationTranslator.class);
		this.registProcessor(makekey(IRemoteConstantDefine.DEVICE_MAJORTYPE_ZWAVE , IRemoteConstantDefine.DEVICE_TYPE_LOCK_CYLINDER), DoorlockOperationTranslator.class);
		this.registProcessor(makekey(IRemoteConstantDefine.DEVICE_MAJORTYPE_ZWAVE , IRemoteConstantDefine.DEVICE_TYPE_DIMMER), DimmerOperationTranslator.class);
		this.registProcessor(makekey(IRemoteConstantDefine.DEVICE_MAJORTYPE_ZWAVE , IRemoteConstantDefine.DEVICE_TYPE_ALARM), AlarmOperationTranslator.class);
		this.registProcessor(makekey(IRemoteConstantDefine.DEVICE_MAJORTYPE_ZWAVE , IRemoteConstantDefine.DEVICE_TYPE_AIR_CONDITIONER), ZWaveACOperationTranslator.class);
		this.registProcessor(makekey(IRemoteConstantDefine.DEVICE_MAJORTYPE_ZWAVE , IRemoteConstantDefine.DEVICE_TYPE_AIR_QUALITY), AirQualityOperationTranslator.class);
		this.registProcessor(makekey(IRemoteConstantDefine.DEVICE_MAJORTYPE_ZWAVE , IRemoteConstantDefine.DEVICE_TYPE_NEW_WIND), NewWindOperationTranslator.class);
		this.registProcessor(makekey(IRemoteConstantDefine.DEVICE_MAJORTYPE_ZWAVE , IRemoteConstantDefine.DEVICE_TYPE_KARAOK), KaraoOperationTranslator.class);
		this.registProcessor(makekey(IRemoteConstantDefine.DEVICE_MAJORTYPE_ZWAVE , IRemoteConstantDefine.DEVICE_TYPE_BGM), BackgroudMusicTranslator.class);
		
		this.registProcessor(makekey(IRemoteConstantDefine.DEVICE_MAJORTYPE_ZWAVE , IRemoteConstantDefine.DEVICE_TYPE_POWER_FREE_SWITCH), SwitchOperationTranslator.class);
		this.registProcessor(makekey(IRemoteConstantDefine.DEVICE_MAJORTYPE_ZWAVE , IRemoteConstantDefine.DEVICE_TYPE_POWER_FREE_2_CHANNEL_SWITCH), SwitchOperationTranslator.class);
		this.registProcessor(makekey(IRemoteConstantDefine.DEVICE_MAJORTYPE_ZWAVE , IRemoteConstantDefine.DEVICE_TYPE_POWER_FREE_3_CHANNEL_SWITCH), MultiChannelBinarySwitchOperationTranslator.class);
		this.registProcessor(makekey(IRemoteConstantDefine.DEVICE_MAJORTYPE_ZWAVE , IRemoteConstantDefine.DEVICE_TYPE_POWER_FREE_4_CHANNEL_SWITCH), MultiChannelBinarySwitchOperationTranslator.class);
	
		this.registProcessor(makekey(IRemoteConstantDefine.DEVICE_MAJORTYPE_ZWAVE , IRemoteConstantDefine.DEVICE_TYPE_DEHUMIDIFY), DehumidifyOperationTranslator.class);
		this.registProcessor(makekey(IRemoteConstantDefine.DEVICE_MAJORTYPE_ZWAVE , IRemoteConstantDefine.DEVICE_TYPE_FRESH_AIR_IN), FreshAirOperationTranslator.class);
		this.registProcessor(makekey(IRemoteConstantDefine.DEVICE_MAJORTYPE_ZWAVE , IRemoteConstantDefine.DEVICE_TYPE_RGB_COLOR_SWITCH), ColorSwitchOperationTranslator.class);

		this.registProcessor(makekey(IRemoteConstantDefine.DEVICE_MAJORTYPE_ZWAVE , IRemoteConstantDefine.DEVICE_TYPE_1_GAN_PORTABLE_SCENE_PANNEL), PortableScenePanelOperationTranslator.class);
		this.registProcessor(makekey(IRemoteConstantDefine.DEVICE_MAJORTYPE_ZWAVE , IRemoteConstantDefine.DEVICE_TYPE_2_GAN_PORTABLE_SCENE_PANNEL), PortableScenePanelOperationTranslator.class);
		this.registProcessor(makekey(IRemoteConstantDefine.DEVICE_MAJORTYPE_ZWAVE , IRemoteConstantDefine.DEVICE_TYPE_3_GAN_PORTABLE_SCENE_PANNEL), PortableScenePanelOperationTranslator.class);
		this.registProcessor(makekey(IRemoteConstantDefine.DEVICE_MAJORTYPE_ZWAVE , IRemoteConstantDefine.DEVICE_TYPE_4_GAN_PORTABLE_SCENE_PANNEL), PortableScenePanelOperationTranslator.class);

		this.registProcessor(makekey(IRemoteConstantDefine.DEVICE_MAJORTYPE_ZWAVE , IRemoteConstantDefine.DEVICE_TYPE_ARM_STATUS_SETTER), ArmStatusSetterTranslator.class);
		
		this.registProcessor(makekey(IRemoteConstantDefine.DEVICE_MAJORTYPE_ZWAVE , IRemoteConstantDefine.DEVICE_TYPE_HEATING_CONTROLLER), HeatingControllerOperationTranslator.class);

		this.registProcessor(makekey(IRemoteConstantDefine.DEVICE_MAJORTYPE_ZWAVE, IRemoteConstantDefine.DEVICE_TYPE_GARAGEDOOR), GarageDoorOperationTranslator.class);
		this.registProcessor(makekey(IRemoteConstantDefine.DEVICE_MAJORTYPE_ZWAVE , IRemoteConstantDefine.DEVICE_TYPE_WALLREADER), DoorlockOperationTranslator.class);

		this.registProcessor(makekey(IRemoteConstantDefine.DEVICE_MAJORTYPE_ZWAVE, IRemoteConstantDefine.DEVICE_TYPE_ELECTRIC_FENCE), ElectricFenceOperationTranslator.class);
		this.registProcessor(makekey(IRemoteConstantDefine.DEVICE_MAJORTYPE_ZWAVE, IRemoteConstantDefine.DEVICE_TYPE_2_CHANNEL_ELECTRIC_FENCE), TwoChannelElectricFenceOperationTranslator.class);

		this.registProcessor(makekey(IRemoteConstantDefine.DEVICE_MAJORTYPE_ZWAVE, IRemoteConstantDefine.DEVICE_TYPE_PASS_THROUGH_DEVICE), PassThroughDeviceTranslator.class);
		this.registProcessor(makekey(IRemoteConstantDefine.DEVICE_MAJORTYPE_ZWAVE, IRemoteConstantDefine.DEVICE_TYPE_INFRARED_PASS_THROUGH_DEVICE), PassThroughDeviceTranslator.class);

		this.registProcessor(makekey(IRemoteConstantDefine.DEVICE_MAJORTYPE_ZWAVE , IRemoteConstantDefine.DEVICE_TYPE_PLASMA_DRYER), PlasmaDryerOperationTranslator.class);
		this.registProcessor(makekey(IRemoteConstantDefine.DEVICE_MAJORTYPE_ZWAVE , IRemoteConstantDefine.DEVICE_TYPE_4_CHANNEL_BINARY_SWITCH), MultiChannelBinarySwitchOperationTranslator.class);
	}
	
	public IOperationTranslator getOperationTranslator(String majortype , String type)
	{
		return super.getProcessor(makekey(majortype , type));
	}
	
	private String makekey(String majortype , String type)
	{
		return String.format("%s_%s", majortype , type);
	}
}
