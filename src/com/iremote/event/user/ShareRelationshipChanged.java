package com.iremote.event.user;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.iremote.common.jms.ITextMessageProcessor;
import com.iremote.common.jms.vo.ShareRelationshipChange;
import com.iremote.common.push.PushMessage;
import com.iremote.service.PhoneUserService;

public class ShareRelationshipChanged extends ShareRelationshipChange implements ITextMessageProcessor {

	@SuppressWarnings("unused")
	private static Log log = LogFactory.getLog(ShareRelationshipChanged.class);
	
	@Override
	public void run() 
	{
		
		PhoneUserService pus = new PhoneUserService();
		
		List<String> al = pus.queryAlias( Arrays.asList(getPhoneuserid()));
		PushMessage.pushInfoChangedMessage(al.toArray(new String[0]) , getPlatform());
	}

	@Override
	public String getTaskKey() {
		return null;
	}

}
