package com.iremote.action.helper;

import java.util.ArrayList;
import java.util.List;

import com.iremote.domain.Command;

public class SceneHelperTest
{

	public static void main(String arg[])
	{
		List<Command> lst = new ArrayList<Command>();
		
		Command c = new Command();
		c.setWeekday(8);
		c.setStartsecond(72000);
		c.setEndsecond(71940);
		
		lst.add(c);
		
		SceneHelper.executeScene(lst, 1, "fjds");
	}
}
