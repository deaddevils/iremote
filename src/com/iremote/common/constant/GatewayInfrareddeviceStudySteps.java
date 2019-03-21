package com.iremote.common.constant;

public enum GatewayInfrareddeviceStudySteps
{
	normal(0),
	study(1),
	finished(2),
	failed(10);

	private int step ;

	private GatewayInfrareddeviceStudySteps(int step) {
		this.step = step;
	}

	public int getStep() {
		return step;
	}
	
}
