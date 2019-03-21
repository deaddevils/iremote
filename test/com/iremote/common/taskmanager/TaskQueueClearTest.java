package com.iremote.common.taskmanager;

import java.util.Random;

import com.iremote.common.Utils;

public class TaskQueueClearTest implements Runnable
{
	private static QueueTaskManager<Runnable>[] tskmg = new QueueTaskManager[]{new QueueTaskManager<Runnable>(),new QueueTaskManager<Runnable>(),new QueueTaskManager<Runnable>(),new QueueTaskManager<Runnable>()};
	private static int[] codes = new int[1000000]; 
			
	private int code ;
	private int index ;
	private Random rand = new Random(this.hashCode());
	
	public static void main(String arg[])
	{
		Random r = new Random(System.currentTimeMillis());
		for ( int i = 0 ; i < codes.length ; i ++ )
		{
			tskmg[0].addTask(String.valueOf(r.nextInt(10000)) , new TaskQueueClearTest(i ,  0));
			Utils.sleep(0);
			
			if ( i % 5000 == 0 )
				countcodes("1 ");
		}
		
		for ( ; ; )
		{
			countcodes("2 ");
			Utils.sleep(3 * 1000);
		}
	}

	public TaskQueueClearTest(int code, int index)
	{
		super();
		this.code = code;
		this.index = index;
	}

	@Override
	public void run()
	{
		codes[code] ++ ;
		if ( index < tskmg.length - 1 )
			tskmg[index+1].addTask(String.valueOf(rand.nextInt(10000)) , new TaskQueueClearTest(code , index + 1));
		Utils.sleep(index);
	}
	
	private static void countcodes(String prefix)
	{
		int[] cv = new int[]{0,0,0,0,0};
		for ( int i = 0 ; i < codes.length ; i ++ )
		{
			cv[codes[i]] ++ ;
		}
		
		System.out.println(String.format("%s %d %d %d %d %d" ,prefix, cv[0], cv[1], cv[2], cv[3], cv[4]));
	}
}
