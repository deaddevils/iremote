package com.iremote.infraredtrans;

import java.io.IOException;
import java.nio.BufferOverflowException;

import com.iremote.infraredcode.ConnectionContextMock;

public class LoginProcessorThread implements Runnable {

	
	@Override
	public void run() 
	{
		LoginProcessor pro = new LoginProcessor();
		try {
			Remoter r = new Remoter();
			pro.process(new byte[]{100,2,0,89,0,1,0,2,0,0,0,2,0,20,105,82,101,109,111,116,101,52,48,48,53,48,48,48,48,48,48,50,54,49,0,101,0,16,(byte)150,82,38,(byte)233,(byte)249,(byte)188,82,104,59,(byte)244,(byte)238,119,(byte)176,(byte)143,(byte)240,32,0,105,0,4,0,0,0,15,0,3,0,2,0,22,0,4,0,6,49,46,50,46,51,48,0,108,0,1,0,0,109,0,1,76,0,31,0,1,1,(byte)206}, 
					new ConnectionContextMock("" , r ));
		} catch (BufferOverflowException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
