package com.iremote.common.commandclass;

import java.util.HashMap;
import java.util.Map;

import com.iremote.common.Utils;

public class CommandParser {

	private static Map<String , int[]> CommandClassMap = new HashMap<String , int[]>();
	
	@SuppressWarnings("unused")
	private static int INDEX_CHANNELID = 0 ;
	private static int INDEX_TYPE = 1 ;
	private static int INDEX_VALUE = 2 ;
	
	static
	{
														// channel , type , value
		CommandClassMap.put(Utils.makekey(0x20, 0x01), new int[]{0 , 0 , 2});
		CommandClassMap.put(Utils.makekey(0x20, 0x03), new int[]{0 , 0 , 2});
		CommandClassMap.put(Utils.makekey(0x30, 0x03), new int[]{0 , 0 , 2});
		CommandClassMap.put(Utils.makekey(0x25, 0x03), new int[]{0 , 0 , 2});
		CommandClassMap.put(Utils.makekey(0x25, 0x01), new int[]{0 , 0 , 2});
		CommandClassMap.put(Utils.makekey(0x26, 0x03), new int[]{0 , 0 , 2});
		CommandClassMap.put(Utils.makekey(0x31, 0x05), new int[]{0 , 0 , 4});
		CommandClassMap.put(Utils.makekey(0x40, 0x03), new int[]{0 , 0 , 2});
		CommandClassMap.put(Utils.makekey(0x43, 0x03), new int[]{0 , 0 , 4});
		CommandClassMap.put(Utils.makekey(0x44, 0x03), new int[]{0 , 0 , 2});
		CommandClassMap.put(Utils.makekey(0x60, 0x0d), new int[]{2 , 0 , 0});
		CommandClassMap.put(Utils.makekey(0x62, 0x01), new int[]{0 , 0 , 2});
		CommandClassMap.put(Utils.makekey(0x62, 0x03), new int[]{0 , 0 , 2});
		CommandClassMap.put(Utils.makekey(0x71, 0x05), new int[]{0 , 0 , 7});
		CommandClassMap.put(Utils.makekey(0x80, 0x03), new int[]{0 , 0 , 2});
		CommandClassMap.put(Utils.makekey(0x84, 0x07), new int[]{});
		CommandClassMap.put(Utils.makekey(0x9c, 0x02), new int[]{0 , 3 , 4});
	}
	
	public static CommandValue parse(byte[] command)
	{
		if ( command == null || command.length < 2  )
			return null ;
		CommandValue cv = new CommandValue();
		
		cv.setCommandclass(command[0] & 0xff);
		cv.setCommand(command[1] & 0xff);
		cv.setCmd(command);
		
		int[] cd = CommandClassMap.get(Utils.makekey(cv.getCommandclass(), cv.getCommand()));
		
		if ( cd == null || cd.length == 0 )
			return cv ;
		
		cv.setChannelid(getChannelid(cd ,command));
		if ( cv.getChannelid() != 0 )
		{
			byte[] cmd = new byte[command.length - 4];
			System.arraycopy(command, 4, cmd, 0, cmd.length);
			CommandValue scv = parse(cmd);
			scv.setChannelid(cv.getChannelid());
			return scv;
		}
		else 
		{
			cv.setType(getValue(cd , INDEX_TYPE , command));
			cv.setValue(getValue(cd , INDEX_VALUE , command));
		}

		return cv ;
	}
	
	private static int getChannelid(int[] commanddefine , byte[] command )
	{
		if ( commanddefine[0] == 0 )
			return 0 ;
		int c = command[commanddefine[0]] & 0xff;
		if ( c != 0 )
			return c;
		return command[commanddefine[0] + 1 ] & 0xff;
	}
	
	private static int getValue(int[] commanddefine , int index , byte[] command)
	{
		if ( commanddefine[index] == 0 )
			return 0 ;
		return command[commanddefine[index]] & 0xff;
	}
	
	

}
