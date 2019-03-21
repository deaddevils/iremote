package com.iremote.action.data;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.iremote.common.Utils;
import com.iremote.common.Hibernate.HibernateUtil;
import com.iremote.domain.Room;
import com.iremote.domain.RoomAppliance;
import com.iremote.service.RoomService;
import com.iremote.vo.merge.RoomComparator;
import com.iremote.vo.merge.RoomMerge;

public class RoomTest {

	public static void main(String arg[])
	{
//		String myrooms = "[{\"appliancelist\":[{\"applianceid\":\"ccb8ed0dad964b9b8012782d44cd6989\",\"deviceid\":\"iRemote2005000000009\",\"devicetype\":\"AC\",\"majortype\":\"infrared\",\"name\":\"凯利\",\"nuid\":0},{\"applianceid\":\"ccb8ed0dad964b9b8012782d44cd6989\",\"deviceid\":\"iRemote2005000000009\",\"devicetype\":\"AC\",\"majortype\":\"infrared\",\"name\":\"凯利\",\"nuid\":0}],\"name\":\"新房间01\",\"phonenumber\":\"15012492081\",\"roomid\":\"6e6e97bd927246f496b545ddb634082f\"}]";
//		
//		List<Room> pglst = JSON.parseArray(myrooms, Room.class);
//		
//		System.out.println(pglst.size());
		
		RoomService rs = new RoomService();
		List<Room> room = rs.querybyphoneuserid(1);
		
		System.out.println(JSON.toJSONString(room));
	}
	
	public static void roomtest(String arg[])
	{
		HibernateUtil.beginTransaction();
		
		String myrooms = "[{\"appliancelist\":[{\"applianceid\":\"ccb8ed0dad964b9b8012782d44cd6989\",\"deviceid\":\"iRemote2005000000009\",\"devicetype\":\"AC\",\"majortype\":\"infrared\",\"name\":\"凯利\",\"nuid\":0},{\"applianceid\":\"ccb8ed0dad964b9b8012782d44cd6989\",\"deviceid\":\"iRemote2005000000009\",\"devicetype\":\"AC\",\"majortype\":\"infrared\",\"name\":\"凯利\",\"nuid\":0}],\"name\":\"新房间01\",\"phonenumber\":\"15012492081\",\"roomid\":\"61a53dac9f504319b95e6435bda6bcf2\"}]";
		
		int phoneuserid = 1 ;
		
		RoomService rs = new RoomService();
		List<Room> dblst = rs.querybyphoneuserid(phoneuserid);
		List<Room> pglst = JSON.parseArray(myrooms, Room.class);
		
		List<Room> rlst = new ArrayList<Room>();
		rlst.addAll(dblst);
		
		Utils.merge(dblst, pglst, new RoomComparator() , new RoomMerge());
		
		for ( Room r : dblst)
		{
			if ( r.getPhoneuserid() == 0 )
			{
				r.setPhonenumber("21324");
				r.setPhoneuserid(phoneuserid);
			}
			for ( RoomAppliance ra :r.getAppliancelist())
				if ( ra.getRoom() == null )
					ra.setRoom(r);
			rs.saveOrUpdate(r);
		}
		
		rlst.removeAll(dblst);
		
		for ( Room r : rlst)
			rs.delete(r);
		
		HibernateUtil.commit();
		HibernateUtil.closeSession();
	}
}
