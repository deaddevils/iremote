package com.iremote.common.jms;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;

public class TextMessageBaseListener implements MessageListener {

	private static Log log = LogFactory.getLog(TextMessageBaseListener.class);
	private static final String DEFAULT_TASK_QUEUE = "defaulttaskqueue";
	
	private Class<? extends ITextMessageProcessor> cls ;

	public TextMessageBaseListener(Class<? extends ITextMessageProcessor> cls) {
		super();
		this.cls = cls;
	}

	@Override
	public void onMessage(Message message) 
	{
		if ( !( message instanceof TextMessage ) ) 
			return ;
		
		try
		{
			TextMessage tm = (TextMessage) message;
			
			if ( log.isInfoEnabled())
			{
				log.info(cls.getName());
				log.info(tm.getText());
			}
			
			ITextMessageProcessor pro = JSON.parseObject(tm.getText(), cls);

			String key = pro.getTaskKey();
			if ( key == null || key.length() == 0 )
				key = DEFAULT_TASK_QUEUE;
			JSMTaskManager.addTask(key, pro);
		}
		catch(Throwable t)
		{
			log.error(t.getMessage(), t);
		}
	}
}
