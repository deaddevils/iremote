package com.iremote.event.association;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.alibaba.fastjson.JSONObject;
import com.iremote.domain.Partition;
import com.iremote.service.PartitionService;
import com.iremote.service.RemoteService;
import com.iremote.service.UserShareService;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.jms.ITextMessageProcessor;
import com.iremote.domain.Notification;
import com.iremote.service.NotificationService;


public class DeviceReportNotificationAssociation extends Notification implements ITextMessageProcessor {
	private static Log log = LogFactory.getLog(DeviceReportNotificationAssociation.class);
	
	private List<Integer> nidlst = new ArrayList<Integer>();
	
	@Override
	public void run() 
	{
		NotificationService service = new NotificationService();
		if(this.getPhoneuserid() != null && this.getPhoneuserid() != 0 && this.getEclipseby() == 0 )
		{			
			Set<Integer> phoneId = queryPhoneuserid();   
			
			if(phoneId != null && phoneId.size() > 0)
			{
				for(Integer id : phoneId)
				{
					Notification n = new Notification();
					try 
					{
						PropertyUtils.copyProperties(n, this);
						n.setPhoneuserid(id);
						if ( !this.getPhoneuserid().equals(id))
							n.setAppendjson(null);
						nidlst.add(service.save(n));
						service.saveByDeviceType(n);
					} 
					catch (Throwable e) 
					{
						log.error(e.getMessage(), e);
					}
				}
			}
			if ( IRemoteConstantDefine.DEVICE_MAJORTYPE_ZWAVE.equals(super.getMajortype())
					&& IRemoteConstantDefine.DEVICE_TYPE_LIST_LIKE_DOOR_LOCK.contains(super.getDevicetype()))
				processDoorlockReport();
		}
		else
		{
			Notification n = new Notification();
			try {
				PropertyUtils.copyProperties(n, this);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
			service.save(n);
		}
		
	}
	
	private void processDoorlockReport()
	{
		NotificationIdCacheWrap wrap = NotificationIdCacheManager.getInstance().get(super.getZwavedeviceid());
		if ( wrap != null && wrap.getMessage().equals(super.getMessage()))
		{
			NotificationService service = new NotificationService();
			service.setNotificationEclipse(wrap.getNidlst());
			service.setNotificationEclipse(super.getMajortype(), super.getDevicetype(), wrap.getNidlst());
		}
		
		NotificationIdCacheManager.getInstance().put(super.getZwavedeviceid(), super.getMessage(), this.nidlst);
	}

	private Set<Integer> queryPhoneuserid()
	{
		Set<Integer> set = new HashSet<Integer>();
		
		if ( StringUtils.isNotBlank(this.getDeviceid()))
		{
			List<Integer> phoneId = PhoneUserHelper.queryPhoneuseridByDeviceid(this.getDeviceid(), this.getZwavedeviceid());
			if ( phoneId != null )
				set.addAll(phoneId);
		}
		else if ( IRemoteConstantDefine.WARNING_TYPE_USER_ARM.equals(this.getMessage())
				|| IRemoteConstantDefine.WARNING_TYPE_USER_INHOME_ARM.equals(this.getMessage())
				|| IRemoteConstantDefine.WARNING_TYPE_USER_DISARM.equals(this.getMessage())
				|| IRemoteConstantDefine.MESSAGE_PARTITION_ARM.equals(this.getMessage())
				|| IRemoteConstantDefine.MESSAGE_PARTITION_HOME_ARM.equals(this.getMessage())
				|| IRemoteConstantDefine.MESSAGE_PARTITION_DIS_ARM.equals(this.getMessage())
				|| IRemoteConstantDefine.MESSAGE_PARTITION_ARM_USER_CODE.equals(this.getMessage())
				|| IRemoteConstantDefine.MESSAGE_PARTITION_DIS_ARM_USER_CODE.equals(this.getMessage())
				|| IRemoteConstantDefine.MESSAGE_PARTITION_ARM_WITHOUT_CODE.equals(this.getMessage()))
		{
			if ( this.getFamilyid() == null )
			{
				set.add(this.getPhoneuserid());
			}
			else
			{
				List<Integer> lst = PhoneUserHelper.queryPhoneuseridbyfamilyid(this.getFamilyid());
				if ( lst != null )
					set.addAll(lst);
			}

			addDeviceLevelSharePhoneuseridByPartitionid(set);
		}
		else 
		{
			set.add(this.getPhoneuserid());
		}
		set.remove(0);
		return set ;

	}

	private void addDeviceLevelSharePhoneuseridByPartitionid(Set<Integer> set) {
		if (this.getAppendjsonstring() != null) {
            JSONObject jsonObject = JSONObject.parseObject(this.getAppendjsonstring());

            if (jsonObject.containsKey("partitionid")) {
                int partitionid = jsonObject.getIntValue("partitionid");
                PartitionService ps = new PartitionService();
                Partition partition = ps.query(partitionid);

                if (partition != null) {
                    Integer phoneuserid = null;

                    if (partition.getPhoneuser() != null){
                        phoneuserid = partition.getPhoneuser().getPhoneuserid();
                    } else if (partition.getZwavedevice() != null) {
                        RemoteService rs = new RemoteService();
                         phoneuserid = rs.queryOwnerId(partition.getZwavedevice().getDeviceid());
                    }

                    if (phoneuserid != null) {
                        set.add(phoneuserid);
                        UserShareService uss = new UserShareService();
                        List<Integer> phoneuseridList = uss.queryTouseridListByShareuseridAndType(phoneuserid,
                                IRemoteConstantDefine.USER_SHARE_TYPE_NORMAL, IRemoteConstantDefine.USER_SHARE_DEVICE_TYPE_ALL);
                        set.addAll(phoneuseridList);
                    }
                }
            }
        }
	}

	@Override
	public String getTaskKey() {
		return super.getDeviceid();
	}

}
