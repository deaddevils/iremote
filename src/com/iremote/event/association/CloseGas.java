package com.iremote.event.association;

import java.util.List;

import com.iremote.common.jms.ITextMessageProcessor;
import com.iremote.common.jms.vo.ZWaveDeviceEvent;
import com.iremote.common.schedule.ScheduleManager;
import com.iremote.domain.ZWaveDevice;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvByteUnit;
import com.iremote.infraredtrans.tlv.TlvIntUnit;
import com.iremote.service.ZWaveDeviceService;
import com.iremote.task.devicecommand.ExecuteDeviceCommand;

public class CloseGas extends ZWaveDeviceEvent implements ITextMessageProcessor
{

	@Override
	public void run()
	{
		closeGas();
	}

	@Override
	public String getTaskKey()
	{
		return getDeviceid();
	}
	
	private void closeGas()
	{
		ZWaveDeviceService zds = new ZWaveDeviceService();
		List<ZWaveDevice> lst = zds.queryRoboticArmDevice(getDeviceid());
		if ( lst == null || lst.size() == 0 )
			return ;
		
		for ( ZWaveDevice d : lst )
		{
			CommandTlv ct = createColseRoboticArmCommand(d);
			ScheduleManager.excutein(0, new ExecuteDeviceCommand(getDeviceid(), ct));
		}
	}
	
	private CommandTlv createColseRoboticArmCommand(ZWaveDevice d)
	{
		CommandTlv ct = new CommandTlv(30 , 7);
		ct.addUnit(new TlvIntUnit(71 , d.getNuid() , 1));
		ct.addUnit(new TlvByteUnit(70 , new byte[]{0x25,0x01,(byte)0x00}));
		ct.addUnit(new TlvIntUnit(72 , 0 , 2));
		ct.addUnit(new TlvByteUnit(74 , new byte[]{0x25,0x03,(byte)0x00}));
		return ct ;
	}

}
