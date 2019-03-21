package com.iremote.infraredcode;

import java.util.List;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.common.Hibernate.HibernateUtil;
import com.iremote.common.file.SerializeHelper;
import com.iremote.domain.InfreredCodeLiberay;
import com.iremote.domain.InfreredDeviceProductor;
import com.iremote.domain.InfreredDeviceProductorCodeMap;
import com.iremote.domain.InfreredDeviceProductorMap;
import com.iremote.infraredcode.stb.StbProductor;
import com.iremote.infraredcode.vo.CodeLiberayVo;
import com.iremote.service.InfreredCodeLiberayService;
import com.iremote.test.db.Db;

public class SaveCode
{
	public static void main(String arg[])
	{
		Db.init();
		
		InfreredCodeLiberayService svr = new InfreredCodeLiberayService();
		List<InfreredCodeLiberay> lst = svr.query();
		for ( InfreredCodeLiberay cl : lst )
		{
			int[] icl = Utils.jsontoIntArray(cl.getCode());
			if ( IRemoteConstantDefine.DEVICE_TYPE_AC.equals(cl.getDevicetype()))
				icl = CodeLiberayHelper.composeACCodeLiberay(cl.getCodeid(), icl);
			else if ( IRemoteConstantDefine.DEVICE_TYPE_STB.equals(cl.getDevicetype()))
				icl = CodeLiberayHelper.composeSTBCodeLiberay(icl);
			else if ( IRemoteConstantDefine.DEVICE_TYPE_TV.equals(cl.getDevicetype()))
				icl = CodeLiberayHelper.composeTVCodeLiberay(icl);
			cl.setCode(JSON.toJSONString(icl));
		}
		
		
		Db.commit();
	}
	
	public static void main2(String arg[])
	{
		Db.init();
		
		for ( String key : StbProductor.combineProductor.keySet())
		{
			Set<String> s = StbProductor.combineProductor.get(key);
			for ( String str : s )
			{
				if ( key.equals(str.toLowerCase()))
					continue;
				InfreredDeviceProductorMap idpm = new InfreredDeviceProductorMap();
				idpm.setDevicetype("STB");
				idpm.setProductor(key);
				idpm.setProductormap(str.toLowerCase());
				HibernateUtil.getSession().save(idpm);
			}
		}
		
		Db.commit();
	}
	
	public static void main1(String arg[])
	{
		CodeLiberayVo cv = SerializeHelper.getObject("resource/tv_parsed_data.ser", new CodeLiberayVo() );
		int[][] cl = cv.getCodeliberay();
		
		Db.init();
		
		for ( int i = 0 ; i < cl.length ; i++)
		{
			InfreredCodeLiberay icl = new InfreredCodeLiberay();
			icl.setCode(JSON.toJSONString(cl[i]));
			icl.setCodeid(i);
			icl.setDevicetype("TV");
			
			HibernateUtil.getSession().save(icl);
		}
		
		for ( String p : cv.getProductorIndexMap().keySet())
		{
			Integer index = cv.getProductorIndexMap().get(p);
			InfreredDeviceProductorCodeMap ipc = new InfreredDeviceProductorCodeMap();
			ipc.setDevicetype("TV");
			ipc.setProductor(p);
			ipc.setCodeids(JSON.toJSONString(cv.getProductorindex()[index]));
			HibernateUtil.getSession().save(ipc);
			
			InfreredDeviceProductor idp = new InfreredDeviceProductor();
			idp.setDevicetype("TV");
			idp.setProductor(p);
			HibernateUtil.getSession().save(idp);
			
		}
		
		
		
		Db.commit();
	}
}
