package com.iremote.common.thread;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public interface ISynchronizeRequest {

	String getkey();
	void sendRequest() throws IOException,TimeoutException;
}
