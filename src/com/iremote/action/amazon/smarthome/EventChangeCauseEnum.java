package com.iremote.action.amazon.smarthome;

public enum EventChangeCauseEnum {
    APP_INTERACTION,PHYSICAL_INTERACTION,PERIODIC_POLL,RULE_TRIGGER,VOICE_INTERACTION;
    /**
     *  APP_INTERACTION         	表示该事件是由客户与应用程序的交互引起的。例如，客户使用Alexa应用程序或设备供应商提供的应用程序打开灯或锁门。
     * PHYSICAL_INTERACTION	指示事件是由与端点的物理交互引起的。例如，手动打开灯或手动锁定门锁。
     * PERIODIC_POLL                	表示事件是由设备的定期轮询引起的，该设备发现值发生了变化。例如，您可以每小时轮询温度传感器，并将更新的温度发送给Alexa。
     * RULE_TRIGGER	                表示该事件是由应用设备规则引起的。例如，如果运动传感器检测到运动，则客户配置规则以打开灯。在这种情况下，Alexa从运动传感器接收事件，并从灯光接收另一个事件，以指示其状态更改是由规则引起的。
     * VOICE_INTERACTION        	表示该事件是由与Alexa的语音交互引起的。例如，用户正在与他们的Echo设备通话。
     */

}
