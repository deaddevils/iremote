package com.iremote.common.constant;

public enum GatewayAddZWaveDeviceSteps 
{
	normal(0),
	delete(1),
	add(2),
	initdevice(3),
	finished(4),
	failed(10),
	unknowndevice(11),
	cancled(12);
	
	private int step ;

	private GatewayAddZWaveDeviceSteps(int step) {
		this.step = step;
	}

	public int getStep() {
		return step;
	}
	
}
