package com.iremote.action.helper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.iremote.common.schedule.ScheduleManager;
import com.iremote.domain.Command;
import com.iremote.service.CommandService;
import com.iremote.service.SceneService;
import com.iremote.task.devicecommand.ExecuteDeviceCommands;

public class SceneHelper {
	
	private static final int[] INT_TO_WEEK = new int[]{0,64,32,16,8,4,2,1};

	@Deprecated
	public static void executeScene(List<Command> cmdlst , int operatetype , String operator)
	{
		if ( cmdlst == null )
			return ;
		
		Calendar cl = Calendar.getInstance();
		int currentsecond = ( cl.get(Calendar.HOUR_OF_DAY) * 60 + cl.get(Calendar.MINUTE)) * 60 + cl.get(Calendar.SECOND);
		int currentweek = cl.get(Calendar.DAY_OF_WEEK);
		
		int second = 0 ;
		List<Command> lst = new ArrayList<Command>();
		
		for (Command c : cmdlst)
		{
			int delay = c.getDelay() ;
			if ( delay <= 2 )
				delay = 0 ;
			second += delay ;
			
			if ( checkCommandValidtime(c , currentweek , currentsecond) == false )
				continue;

			if ( delay == 0 )
				lst.add(c);
			else 
			{
				ScheduleManager.excutein(second, new ExecuteDeviceCommands(lst , operatetype , operator));
				lst.clear();
				lst.add(c);
			}
		}
		if ( lst.size() > 0 )
			ScheduleManager.excutein(second, new ExecuteDeviceCommands(lst, operatetype , operator));
	}
	
	private static boolean checkCommandValidtime(Command c , int weekday , int currentsecond)
	{
		if ( c.getWeekday() == null || c.getStartsecond() == null || c.getEndsecond() == null )
			return true ;
		
		if ( c.getStartsecond() <= currentsecond  && currentsecond <= c.getEndsecond() && (INT_TO_WEEK[weekday] & c.getWeekday()) != 0 )
			return true ;
		
		if ( c.getEndsecond() < c.getStartsecond()  && c.getStartsecond() <= currentsecond && (INT_TO_WEEK[weekday] & c.getWeekday()) != 0 )
			return true;
		
		if ( c.getEndsecond() < c.getStartsecond() 	&&  currentsecond <= c.getEndsecond()   )// start time > end time
		{
			if ( weekday != 1 )
				return ((INT_TO_WEEK[weekday - 1 ] & c.getWeekday()) != 0);
			else 
				return ((INT_TO_WEEK[7] & c.getWeekday()) != 0);
		}
		
		return false ;
	}
	
	public static void clearOnDeleteScene(int scenedbid)
	{
		CommandService cs = new CommandService();
		SceneService ss = new SceneService();
		
		List<Command> lst = cs.querybyLaunchscenedbid(scenedbid);
		if ( lst == null || lst.size() == 0 )
			return ;
		
		for ( Command c : lst )
		{
			if ( c.getScene() == null || c.getScene().getCommandlist() == null )
			{
				cs.delete(c);
				continue;
			}
			c.getScene().getCommandlist().remove(c);
			if ( c.getScene().getCommandlist().size() == 0 )
				ss.delete(c.getScene());
		}
	}
}
