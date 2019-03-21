package com.iremote.thirdpart.wcj.action;

import com.iremote.domain.DoorlockUser;
import com.iremote.domain.ZWaveDevice;

public interface DeleteDoorlockCardUserInterface {

	boolean sendDeleteCommand();

	void setValue(ZWaveDevice lock, DoorlockUser doorlockUser);

	int getResultCode();

}
