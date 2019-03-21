package com.iremote.action.data;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.TimeZone;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.device.operate.IOperationTranslator;
import com.iremote.device.operate.OperationTranslatorStore;
import com.iremote.domain.Associationscene;
import com.iremote.domain.Command;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.Remote;
import com.iremote.domain.Scene;
import com.iremote.domain.ZWaveDevice;
import com.iremote.service.CameraService;
import com.iremote.service.InfraredDeviceService;
import com.iremote.service.IremotepasswordService;
import com.iremote.service.PhoneUserService;
import com.iremote.service.SceneService;
import com.iremote.service.ZWaveDeviceService;
import com.iremote.test.db.Db;
import com.iremote.vo.Appliance;
import com.iremote.vo.RemoteData;
import com.iremote.vo.merge.AssociationsceneComparator;
import com.iremote.vo.merge.AssociationsceneMerge;

public class SynchronizeDataActionTest {

	public static void main(String arg[])
	{
//		SynchronizeDataAction a = new SynchronizeDataAction();
//		a.execute();
//		
//		System.out.print(JSON.toJSONString(a));
//		SynchronizeDataActionTest t = new SynchronizeDataActionTest();
//		t.updatedRemote();
		
//		commandtest();
		
//		for ( int i= 0 ; i < 30 ; i ++ )
//		{
//			Calendar cal = Calendar.getInstance();
//			TimeZone timeZone = cal.getTimeZone();
//			System.out.println(Utils.formatTime(cal.getTime()));
//			System.out.println(timeZone.getID());
//			System.out.println(timeZone.getDisplayName());
//			System.out.println(timeZone.getRawOffset()/3600000);
//			System.out.println(cal.get(Calendar.DST_OFFSET)/3600000);
//			System.out.println(cal.get(Calendar.ZONE_OFFSET)/3600000);
//			
//			
//			System.out.println((cal.get(Calendar.ZONE_OFFSET) + cal.get(Calendar.DST_OFFSET))/3600000);
//			
//			Utils.sleep(5000);
//		}
		
//		aoidassocationwritetest();
		test();
	}
	
	private static void test()
	{
		Db.init();
		
		SynchronizeDataAction a = new SynchronizeDataAction();
		
		a.setMyremote("[{\"appliancelist\":[{\"appendmessage\":[],\"applianceid\":\"05174e96c778487cbaac775b8d01eb54\",\"applianceuuid\":\"\",\"associationscenelist\":[],\"capability\":[],\"codeid\":\"\",\"codelibery\":[],\"controlmodeid\":\"\",\"devicetype\":\"8\",\"majortype\":\"zWave\",\"name\":\"二位开关\",\"productorid\":\"\",\"subdevice\":[],\"timer\":[],\"codeindex\":0,\"infrareddeviceid\":0,\"nuid\":8,\"wakeuptype\":1,\"zwavedeviceid\":2794},{\"appendmessage\":[],\"applianceid\":\"ccbd0dbaf9e649e78f8e517db18471aa256103\",\"applianceuuid\":\"\",\"associationscenelist\":[],\"capability\":[],\"codeid\":\"\",\"codelibery\":[],\"controlmodeid\":\"\",\"devicetype\":\"32\",\"majortype\":\"zWave\",\"name\":\"二键无源开关\",\"productorid\":\"\",\"subdevice\":[],\"timer\":[],\"codeindex\":0,\"infrareddeviceid\":0,\"nuid\":11302,\"wakeuptype\":1,\"zwavedeviceid\":2800},{\"appendmessage\":[],\"applianceid\":\"d4ee24af0d57466f8176a8f40004155a075231\",\"applianceuuid\":\"\",\"associationscenelist\":[{\"commandlist\":[{\"applianceid\":\"05174e96c778487cbaac775b8d01eb54\",\"commandjson\":[{\"channelid\":1,\"operation\":\"close\"}],\"description\":\"开关1关闭 周二 00:00-23:59\",\"deviceid\":\"iRemote2005000004457\",\"endtime\":\"23:59\",\"starttime\":\"00:00\",\"delay\":0,\"index\":0,\"infrareddeviceid\":0,\"launchscenedbid\":0,\"weekday\":16,\"zwavedeviceid\":2794},{\"applianceid\":\"05174e96c778487cbaac775b8d01eb54\",\"commandjson\":[{\"channelid\":2,\"operation\":\"close\"}],\"description\":\"开关2关闭 周二 00:00-23:59\",\"deviceid\":\"iRemote2005000004457\",\"endtime\":\"23:59\",\"starttime\":\"00:00\",\"delay\":0,\"index\":1,\"infrareddeviceid\":0,\"launchscenedbid\":0,\"weekday\":16,\"zwavedeviceid\":2794}],\"devicestatus\":\"close\",\"associationsceneid\":6258,\"channelid\":0,\"scenedbid\":11117,\"status\":0},{\"commandlist\":[{\"applianceid\":\"05174e96c778487cbaac775b8d01eb54\",\"commandjson\":[{\"channelid\":1,\"operation\":\"open\"}],\"description\":\"开关1打开 周二 00:00-23:59\",\"deviceid\":\"iRemote2005000004457\",\"endtime\":\"23:59\",\"starttime\":\"00:00\",\"delay\":0,\"index\":0,\"infrareddeviceid\":0,\"launchscenedbid\":0,\"weekday\":16,\"zwavedeviceid\":2794},{\"applianceid\":\"05174e96c778487cbaac775b8d01eb54\",\"commandjson\":[{\"channelid\":2,\"operation\":\"open\"}],\"description\":\"开关2打开 周二 00:00-23:59\",\"deviceid\":\"iRemote2005000004457\",\"endtime\":\"23:59\",\"starttime\":\"00:00\",\"delay\":0,\"index\":1,\"infrareddeviceid\":0,\"launchscenedbid\":0,\"weekday\":16,\"zwavedeviceid\":2794},{\"applianceid\":\"ed2667b2796648f892d6cf567b2c581b\",\"commandjson\":[{\"channelid\":1,\"operation\":\"open\"}],\"description\":\"开关1打开 周二 00:00-23:59\",\"deviceid\":\"iRemote2005000004457\",\"endtime\":\"23:59\",\"starttime\":\"00:00\",\"delay\":0,\"index\":2,\"infrareddeviceid\":0,\"launchscenedbid\":0,\"weekday\":16,\"zwavedeviceid\":2809}],\"devicestatus\":\"open\",\"associationsceneid\":6257,\"channelid\":0,\"scenedbid\":11116,\"status\":255}],\"capability\":[],\"codeid\":\"\",\"codelibery\":[],\"controlmodeid\":\"\",\"devicetype\":\"31\",\"majortype\":\"zWave\",\"name\":\"一键无源开关\",\"productorid\":\"\",\"subdevice\":[],\"timer\":[],\"codeindex\":0,\"infrareddeviceid\":0,\"nuid\":11301,\"wakeuptype\":1,\"zwavedeviceid\":2799},{\"appendmessage\":[],\"applianceid\":\"dc0f744646db4136a132b9f1859cee19767272\",\"applianceuuid\":\"\",\"associationscenelist\":[{\"commandlist\":[{\"applianceid\":\"ed2667b2796648f892d6cf567b2c581b\",\"commandjson\":[{\"channelid\":1,\"operation\":\"close\"}],\"description\":\"开关1关闭 周二 00:00-23:59\",\"deviceid\":\"iRemote2005000004457\",\"endtime\":\"23:59\",\"starttime\":\"00:00\",\"delay\":0,\"index\":0,\"infrareddeviceid\":0,\"launchscenedbid\":0,\"weekday\":16,\"zwavedeviceid\":2809},{\"applianceid\":\"ed2667b2796648f892d6cf567b2c581b\",\"commandjson\":[{\"channelid\":2,\"operation\":\"close\"}],\"description\":\"开关2关闭 周二 00:00-23:59\",\"deviceid\":\"iRemote2005000004457\",\"endtime\":\"23:59\",\"starttime\":\"00:00\",\"delay\":0,\"index\":1,\"infrareddeviceid\":0,\"launchscenedbid\":0,\"weekday\":16,\"zwavedeviceid\":2809},{\"applianceid\":\"ed2667b2796648f892d6cf567b2c581b\",\"commandjson\":[{\"channelid\":3,\"operation\":\"close\"}],\"description\":\"开关3关闭 周二 00:00-23:59\",\"deviceid\":\"iRemote2005000004457\",\"endtime\":\"23:59\",\"starttime\":\"00:00\",\"delay\":0,\"index\":2,\"infrareddeviceid\":0,\"launchscenedbid\":0,\"weekday\":16,\"zwavedeviceid\":2809}],\"devicestatus\":\"close\",\"associationsceneid\":6234,\"channelid\":1,\"scenedbid\":11093,\"status\":0},{\"commandlist\":[{\"applianceid\":\"ed2667b2796648f892d6cf567b2c581b\",\"commandjson\":[{\"channelid\":1,\"operation\":\"open\"}],\"description\":\"开关1打开 周二 00:00-23:59\",\"deviceid\":\"iRemote2005000004457\",\"endtime\":\"23:59\",\"starttime\":\"00:00\",\"delay\":0,\"index\":0,\"infrareddeviceid\":0,\"launchscenedbid\":0,\"weekday\":16,\"zwavedeviceid\":2809},{\"applianceid\":\"ed2667b2796648f892d6cf567b2c581b\",\"commandjson\":[{\"channelid\":2,\"operation\":\"open\"}],\"description\":\"开关2打开 周二 00:00-23:59\",\"deviceid\":\"iRemote2005000004457\",\"endtime\":\"23:59\",\"starttime\":\"00:00\",\"delay\":0,\"index\":1,\"infrareddeviceid\":0,\"launchscenedbid\":0,\"weekday\":16,\"zwavedeviceid\":2809},{\"applianceid\":\"ed2667b2796648f892d6cf567b2c581b\",\"commandjson\":[{\"channelid\":3,\"operation\":\"open\"}],\"description\":\"开关3打开 周二 00:00-23:59\",\"deviceid\":\"iRemote2005000004457\",\"endtime\":\"23:59\",\"starttime\":\"00:00\",\"delay\":0,\"index\":2,\"infrareddeviceid\":0,\"launchscenedbid\":0,\"weekday\":16,\"zwavedeviceid\":2809}],\"devicestatus\":\"open\",\"associationsceneid\":6233,\"channelid\":1,\"scenedbid\":11092,\"status\":255}],\"capability\":[],\"codeid\":\"\",\"codelibery\":[],\"controlmodeid\":\"\",\"devicetype\":\"33\",\"majortype\":\"zWave\",\"name\":\"三键无源开关\",\"productorid\":\"\",\"subdevice\":[],\"timer\":[],\"codeindex\":0,\"infrareddeviceid\":0,\"nuid\":11303,\"wakeuptype\":1,\"zwavedeviceid\":2812},{\"appendmessage\":[],\"applianceid\":\"ed2667b2796648f892d6cf567b2c581b\",\"applianceuuid\":\"\",\"associationscenelist\":[],\"capability\":[],\"codeid\":\"\",\"codelibery\":[],\"controlmodeid\":\"\",\"devicetype\":\"9\",\"majortype\":\"zWave\",\"name\":\"三位\",\"productorid\":\"\",\"subdevice\":[],\"timer\":[],\"codeindex\":0,\"infrareddeviceid\":0,\"nuid\":14,\"wakeuptype\":1,\"zwavedeviceid\":2809},{\"appendmessage\":[],\"applianceid\":\"f8cb0aa76eae4856ad780bd255497506381734\",\"applianceuuid\":\"\",\"associationscenelist\":[],\"capability\":[],\"codeid\":\"\",\"codelibery\":[],\"controlmodeid\":\"\",\"devicetype\":\"35\",\"majortype\":\"zWave\",\"name\":\"六键情景面板\",\"productorid\":\"\",\"subdevice\":[],\"timer\":[],\"codeindex\":0,\"infrareddeviceid\":0,\"nuid\":11304,\"wakeuptype\":1,\"zwavedeviceid\":2813}],\"capability\":[{\"capabilitycode\":1},{\"capabilitycode\":10},{\"capabilitycode\":3}],\"deviceid\":\"iRemote2005000004457\",\"name\":\"无源网关\",\"phonenumber\":\"15229947382\",\"timezone\":{\"zoneid\":\" 8\",\"zonetext\":\"中国/北京\"},\"type\":0}]");
		a.setLastsyntime("2017-06-06 15:09:26");
		a.setLastupdatetime("2017-06-06 17:11:00");
		
		PhoneUserService pus = new PhoneUserService();
		a.setPhoneuser(pus.query(169));
		
		a.execute();
		
		Db.rollback();
	}
	
	private void updatedRemote()
	{
		IremotepasswordService svr = new IremotepasswordService();
		List<Remote> lst = svr.querybyPhoneUserid(Arrays.asList(new Integer[]{5}));
		for ( Remote r : lst )
		{
			RemoteData d = new RemoteData();
			d.setDeviceid(r.getDeviceid());
			d.setPhonenumber(r.getPhonenumber());
			d.setTimezone(r.getTimezone());
			
			List<Object> lo = new LinkedList<Object>();
			ZWaveDeviceService zds = new ZWaveDeviceService();
			CameraService cs = new CameraService();
			InfraredDeviceService ids = new InfraredDeviceService();

			lo.addAll(zds.querybydeviceid(r.getDeviceid()));
			lo.addAll(cs.querybydeviceid(r.getDeviceid()));
			lo.addAll(ids.querybydeviceid(r.getDeviceid()));
			
			for ( Object obj : lo )
			{
				
				try {
					Appliance a = new Appliance();
					PropertyUtils.copyProperties(a, obj);
					d.getAppliancelist().add(a);
				} catch (Throwable e) {
					e.printStackTrace();
				} 
			}
			System.out.println(JSON.toJSONString(lo));
			System.out.println(JSON.toJSONString(d));
		}
	}
	
	private static void commandtest()
	{
		Db.init();
		String str = "[{\"appliancelist\":[{\"applianceid\":\"18801BDE-AA42-490B-8EB4-8A7CC53E9204\",\"applianceuuid\":\"\",\"associationscenelist\":[{\"devicestatus\":\"close\",\"commandlist\":[{\"applianceid\":\"F607D6D6-0689-4DF8-B5DF-AB36FDD1FB39\",\"commandjson\":[{\"operation\":\"doorlockopen\",\"channelid\":0,\"value\":0}],\"description\":\"关\",\"deviceid\":\"iRemote2005000000816\",\"endtime\":\"23:59\",\"zwavecommand\":[0,70,0,3,98,1,0,0,72,0,1,0,0,74,0,7,98,3,0,0,2,-2,-2,0,71,0,1,17],\"starttime\":\"00:00\",\"launchscenedbid\":0,\"infrareddeviceid\":0,\"weekday\":7,\"index\":0,\"delay\":0,\"zwavedeviceid\":1739}],\"channelid\":2,\"associationsceneid\":2081,\"scenedbid\":3314,\"status\":0},{\"devicestatus\":\"open\",\"commandlist\":[{\"applianceid\":\"F607D6D6-0689-4DF8-B5DF-AB36FDD1FB39\",\"commandjson\":[{\"operation\":\"doorlockopen\",\"channelid\":0,\"value\":0}],\"description\":\"开\",\"deviceid\":\"iRemote2005000000816\",\"endtime\":\"23:59\",\"zwavecommand\":[0,70,0,3,98,1,0,0,72,0,1,0,0,74,0,7,98,3,0,0,2,-2,-2,0,71,0,1,17],\"starttime\":\"00:00\",\"launchscenedbid\":0,\"infrareddeviceid\":0,\"weekday\":15,\"index\":0,\"delay\":0,\"zwavedeviceid\":1739}],\"channelid\":2,\"associationsceneid\":2082,\"scenedbid\":3315,\"status\":255},{\"devicestatus\":\"close\",\"commandlist\":[{\"applianceid\":\"\",\"commandjson\":[{\"operation\":\"launchscene\",\"channelid\":0,\"value\":3269}],\"description\":\"3关\",\"endtime\":\"23:59\",\"infraredcode\":[],\"starttime\":\"00:00\",\"launchscenedbid\":3269,\"infrareddeviceid\":0,\"weekday\":7,\"index\":0,\"delay\":0,\"zwavedeviceid\":0}],\"channelid\":3,\"associationsceneid\":2083,\"scenedbid\":3306,\"status\":0},{\"devicestatus\":\"open\",\"commandlist\":[{\"applianceid\":\"\",\"commandjson\":[{\"operation\":\"launchscene\",\"channelid\":0,\"value\":3266}],\"description\":\"3开\",\"endtime\":\"23:59\",\"infraredcode\":[],\"starttime\":\"00:00\",\"launchscenedbid\":3266,\"infrareddeviceid\":0,\"weekday\":7,\"index\":0,\"delay\":0,\"zwavedeviceid\":0}],\"channelid\":3,\"associationsceneid\":2084,\"scenedbid\":3307,\"status\":255}],\"capability\":[],\"codeid\":\"\",\"timer\":[],\"codelibery\":[],\"controlmodeid\":\"\",\"devicetype\":\"9\",\"productorid\":\"\",\"majortype\":\"zWave\",\"name\":\"开关3\",\"nuid\":10,\"infrareddeviceid\":0,\"codeindex\":0,\"wakeuptype\":1,\"zwavedeviceid\":1732}],\"deviceid\":\"iRemote2005000000816\",\"name\":\"000816\",\"phonenumber\":\"13266836350\",\"timezone\":{\"zoneid\":\"+8\",\"zonetext\":\"中国/北京\"},\"type\":0}]";
		
		SynchronizeDataAction a = new SynchronizeDataAction();
		a.setMyremote(str);
		a.setLastupdatetime(Utils.formatTime(new Date()));
		
		PhoneUserService pus = new PhoneUserService();
		a.setPhoneuser(pus.query(3));
		a.execute();
		
		Db.rollback();
	}
	
	private static void associationtest()
	{
		Db.init();
		
		PhoneUserService pus = new PhoneUserService();
		PhoneUser pu = pus.query(110);
		
		SynchronizeDataAction a = new SynchronizeDataAction();
		a.setPhoneuser(pu);
		
		a.execute();
		
		System.out.println(JSON.toJSONString(a));
		
	}
	
	private static void aoidassocationwritetest()
	{
		String myremote = "[{\"appliancelist\":[{\"applianceid\":\"eab2b9caf27d45389480f1b0f2f0960e254320\",\"associationscenelist\":[],\"capability\":[{\"capabilitycode\":1,\"devicecapabilityid\":4354},{\"capabilitycode\":2,\"devicecapabilityid\":4355},{\"capabilitycode\":3,\"devicecapabilityid\":4356},{\"capabilitycode\":4,\"devicecapabilityid\":4357},{\"capabilitycode\":5,\"devicecapabilityid\":4358},{\"capabilitycode\":6,\"devicecapabilityid\":4359}],\"codeid\":\"\",\"codeindex\":0,\"devicetype\":\"5\",\"infrareddeviceid\":0,\"majortype\":\"zWave\",\"name\":\"000004\",\"nuid\":11201,\"timer\":[],\"wakeuptype\":2,\"zwavedeviceid\":1592}],\"deviceid\":\"iRemote2005000000004\",\"name\":\"004\",\"phonenumber\":\"13502876070\",\"type\":1},{\"appliancelist\":[{\"applianceid\":\"67aa2488c54c4cc4ba4c0926db7e405e\",\"associationscenelist\":[],\"capability\":[],\"codeid\":\"\",\"codeindex\":0,\"devicetype\":\"3\",\"infrareddeviceid\":0,\"majortype\":\"zWave\",\"name\":\"燃气\",\"nuid\":5,\"timer\":[],\"wakeuptype\":1,\"zwavedeviceid\":390},{\"applianceid\":\"a0f8ef9d13604fc2ae1d16f23e5ad320\",\"associationscenelist\":[],\"capability\":[],\"codeid\":\"\",\"codeindex\":0,\"devicetype\":\"10\",\"infrareddeviceid\":0,\"majortype\":\"zWave\",\"name\":\"报警器\",\"nuid\":7,\"timer\":[],\"wakeuptype\":1,\"zwavedeviceid\":392},{\"applianceid\":\"cc24f7f963344415800e514ccf0e82b0\",\"associationscenelist\":[{\"associationsceneid\":99,\"channelid\":0,\"commandlist\":[],\"devicestatus\":\"close\",\"scenedbid\":740,\"scenetype\":2,\"status\":0,\"zwavedeviceid\":395},{\"associationsceneid\":100,\"channelid\":0,\"commandlist\":[],\"devicestatus\":\"open\",\"scenedbid\":741,\"scenetype\":2,\"status\":255,\"zwavedeviceid\":395}],\"capability\":[],\"codeid\":\"\",\"codeindex\":0,\"devicetype\":\"7\",\"infrareddeviceid\":0,\"majortype\":\"zWave\",\"name\":\"开关1\",\"nuid\":10,\"timer\":[],\"wakeuptype\":1,\"zwavedeviceid\":395},{\"applianceid\":\"999015169b1a40a3a31dfbfc416a7c87\",\"associationscenelist\":[],\"capability\":[],\"codeid\":\"\",\"codeindex\":0,\"devicetype\":\"8\",\"infrareddeviceid\":0,\"majortype\":\"zWave\",\"name\":\"开关2\",\"nuid\":11,\"timer\":[],\"wakeuptype\":1,\"zwavedeviceid\":396},{\"applianceid\":\"8efa54723cba41739f857a8bf319bf59\",\"associationscenelist\":[],\"capability\":[],\"codeid\":\"\",\"codeindex\":0,\"devicetype\":\"9\",\"infrareddeviceid\":0,\"majortype\":\"zWave\",\"name\":\"开关3\",\"nuid\":12,\"timer\":[],\"wakeuptype\":1,\"zwavedeviceid\":397},{\"applianceid\":\"38d3ebdd5608482fac05b1314bfda2e3\",\"associationscenelist\":[],\"capability\":[],\"codeid\":\"\",\"codeindex\":0,\"devicetype\":\"6\",\"infrareddeviceid\":0,\"majortype\":\"zWave\",\"name\":\"移动\",\"nuid\":13,\"timer\":[],\"wakeuptype\":1,\"zwavedeviceid\":398},{\"applianceid\":\"5ff4287e3a08415d8742459d9aa5e591\",\"associationscenelist\":[],\"capability\":[],\"codeid\":\"\",\"codeindex\":0,\"devicetype\":\"2\",\"infrareddeviceid\":0,\"majortype\":\"zWave\",\"name\":\"漏水\",\"nuid\":14,\"timer\":[],\"wakeuptype\":1,\"zwavedeviceid\":399},{\"applianceid\":\"f301cd644e3440f8b31df0830a63ffb6\",\"associationscenelist\":[],\"capability\":[],\"codeid\":\"\",\"codeindex\":0,\"devicetype\":\"1\",\"infrareddeviceid\":0,\"majortype\":\"zWave\",\"name\":\"烟感\",\"nuid\":15,\"timer\":[],\"wakeuptype\":1,\"zwavedeviceid\":400},{\"applianceid\":\"a3c4b7e94cfc48f6b6af1a2080f518cd\",\"associationscenelist\":[{\"associationsceneid\":97,\"channelid\":0,\"commandlist\":[],\"devicestatus\":\"open\",\"scenedbid\":738,\"scenetype\":2,\"status\":255,\"zwavedeviceid\":401},{\"associationsceneid\":98,\"channelid\":0,\"commandlist\":[],\"devicestatus\":\"close\",\"scenedbid\":739,\"scenetype\":2,\"status\":0,\"zwavedeviceid\":401}],\"capability\":[],\"codeid\":\"\",\"codeindex\":0,\"devicetype\":\"12\",\"infrareddeviceid\":0,\"majortype\":\"zWave\",\"name\":\"机械手\",\"nuid\":16,\"timer\":[],\"wakeuptype\":1,\"zwavedeviceid\":401},{\"applianceid\":\"3b222fa965ed4c1c96a642872a2687a6\",\"associationscenelist\":[],\"capability\":[],\"codeid\":\"\",\"codeindex\":0,\"devicetype\":\"5\",\"infrareddeviceid\":0,\"majortype\":\"zWave\",\"name\":\"门锁\",\"nuid\":18,\"timer\":[],\"wakeuptype\":1,\"zwavedeviceid\":433},{\"applianceid\":\"dbac1b06ac044662ad813eb8927b3936\",\"associationscenelist\":[],\"capability\":[],\"codeid\":\"\",\"codeindex\":0,\"devicetype\":\"4\",\"infrareddeviceid\":0,\"majortype\":\"zWave\",\"name\":\"门磁\",\"nuid\":19,\"timer\":[],\"wakeuptype\":1,\"zwavedeviceid\":434},{\"applianceid\":\"00a8b2c948db46c69bd19c96daba132c\",\"applianceuuid\":\"\",\"codeid\":\"\",\"codeindex\":0,\"devicetype\":\"1\",\"infrareddeviceid\":0,\"majortype\":\"camera\",\"name\":\"摄像头\",\"nuid\":0,\"productorid\":\"\",\"timer\":[],\"wakeuptype\":1,\"zwavedeviceid\":0},{\"applianceid\":\"3e4d183c73214923b9bc3ee49beda4a5\",\"codeid\":\"AC_389\",\"codeindex\":24,\"codelibery\":[55,39,30,209,0,0,0,0,0,0,0,6,36,84,88,30,198],\"controlmodeid\":\"\",\"devicetype\":\"AC\",\"infrareddeviceid\":31,\"majortype\":\"infrared\",\"name\":\"空调\",\"nuid\":0,\"productorid\":\"McQuay\",\"timer\":[],\"wakeuptype\":1,\"zwavedeviceid\":0}],\"deviceid\":\"iRemote2005000000485\",\"name\":\"000485\",\"phonenumber\":\"13502876070\",\"timezone\":{\"timezoneid\":4,\"zoneid\":\"+8\",\"zonetext\":\"中国/北京\"},\"type\":0},{\"appliancelist\":[{\"applianceid\":\"4A04BC04-5927-4E4A-B931-E8CE2302EE2F\",\"associationscenelist\":[],\"capability\":[],\"codeid\":\"\",\"codeindex\":0,\"devicetype\":\"4\",\"infrareddeviceid\":0,\"majortype\":\"zWave\",\"name\":\"门磁\",\"nuid\":2,\"timer\":[],\"wakeuptype\":1,\"zwavedeviceid\":1724},{\"applianceid\":\"B6263C5C-41F9-4B52-AC48-FDA25DBF6700\",\"associationscenelist\":[],\"capability\":[],\"codeid\":\"\",\"codeindex\":0,\"devicetype\":\"16\",\"infrareddeviceid\":0,\"majortype\":\"zWave\",\"name\":\"SOS\",\"nuid\":3,\"timer\":[],\"wakeuptype\":1,\"zwavedeviceid\":1725},{\"applianceid\":\"11369118-D561-4B9E-87EC-7F6699FEBC35\",\"associationscenelist\":[],\"capability\":[],\"codeid\":\"\",\"codeindex\":0,\"devicetype\":\"12\",\"infrareddeviceid\":0,\"majortype\":\"zWave\",\"name\":\"机械手\",\"nuid\":4,\"timer\":[],\"wakeuptype\":1,\"zwavedeviceid\":1726},{\"applianceid\":\"A8AC2513-DFB7-4A92-BFAF-FF7C1CC93980\",\"associationscenelist\":[],\"capability\":[],\"codeid\":\"\",\"codeindex\":0,\"devicetype\":\"18\",\"infrareddeviceid\":0,\"majortype\":\"zWave\",\"name\":\"猫眼\",\"nuid\":5,\"timer\":[],\"wakeuptype\":1,\"zwavedeviceid\":1727},{\"applianceid\":\"E25AF2BC-656C-4C52-98D1-522464A75D9B\",\"associationscenelist\":[],\"capability\":[],\"codeid\":\"\",\"codeindex\":0,\"devicetype\":\"10\",\"infrareddeviceid\":0,\"majortype\":\"zWave\",\"name\":\"报警器\",\"nuid\":6,\"timer\":[],\"wakeuptype\":1,\"zwavedeviceid\":1728},{\"applianceid\":\"699BA28F-FFD1-4979-BE6D-2FE6DAF47F55\",\"associationscenelist\":[],\"capability\":[],\"codeid\":\"\",\"codeindex\":0,\"devicetype\":\"19\",\"infrareddeviceid\":0,\"majortype\":\"zWave\",\"name\":\"锁芯\",\"nuid\":7,\"timer\":[],\"wakeuptype\":1,\"zwavedeviceid\":1729},{\"applianceid\":\"3DCAFAD3-22DC-466A-A5BD-B269D69B437E\",\"associationscenelist\":[],\"capability\":[],\"codeid\":\"\",\"codeindex\":0,\"devicetype\":\"8\",\"infrareddeviceid\":0,\"majortype\":\"zWave\",\"name\":\"开关2\",\"nuid\":9,\"timer\":[],\"wakeuptype\":1,\"zwavedeviceid\":1731},{\"applianceid\":\"18801BDE-AA42-490B-8EB4-8A7CC53E9204\",\"associationscenelist\":[{\"associationsceneid\":2081,\"channelid\":2,\"commandlist\":[{\"applianceid\":\"F607D6D6-0689-4DF8-B5DF-AB36FDD1FB39\",\"commandid\":36454,\"commandjson\":[{\"operation\":\"doorlockopen\",\"value\":0}],\"delay\":0,\"description\":\"关\",\"deviceid\":\"iRemote2005000000816\",\"endsecond\":86340,\"endtime\":\"23:59\",\"index\":0,\"startsecond\":0,\"starttime\":\"00:00\",\"weekday\":7,\"zwavecommand\":\"AEYAA2IBAABIAAEAAEoAB2IDAAAC/v4ARwABEQ==\",\"zwavecommandbase64\":\"AEYAA2IBAABIAAEAAEoAB2IDAAAC/v4ARwABEQ==\",\"zwavedeviceid\":1739}],\"devicestatus\":\"close\",\"operator\":\"eq\",\"scenedbid\":3314,\"scenetype\":2,\"status\":0,\"zwavedeviceid\":1732},{\"associationsceneid\":2082,\"channelid\":2,\"commandlist\":[{\"applianceid\":\"F607D6D6-0689-4DF8-B5DF-AB36FDD1FB39\",\"commandid\":36455,\"commandjson\":[{\"operation\":\"doorlockopen\",\"value\":0}],\"delay\":0,\"description\":\"开\",\"deviceid\":\"iRemote2005000000816\",\"endsecond\":86340,\"endtime\":\"23:59\",\"index\":0,\"startsecond\":0,\"starttime\":\"00:00\",\"weekday\":15,\"zwavecommand\":\"AEYAA2IBAABIAAEAAEoAB2IDAAAC/v4ARwABEQ==\",\"zwavecommandbase64\":\"AEYAA2IBAABIAAEAAEoAB2IDAAAC/v4ARwABEQ==\",\"zwavedeviceid\":1739}],\"devicestatus\":\"open\",\"operator\":\"eq\",\"scenedbid\":3315,\"scenetype\":2,\"status\":255,\"zwavedeviceid\":1732}],\"capability\":[],\"codeid\":\"\",\"codeindex\":0,\"devicetype\":\"9\",\"infrareddeviceid\":0,\"majortype\":\"zWave\",\"name\":\"开关3\",\"nuid\":10,\"timer\":[],\"wakeuptype\":1,\"zwavedeviceid\":1732},{\"applianceid\":\"69EEA1CA-4234-4989-AF64-F0E1DF691AB9\",\"associationscenelist\":[],\"capability\":[],\"codeid\":\"\",\"codeindex\":0,\"devicetype\":\"11\",\"infrareddeviceid\":0,\"majortype\":\"zWave\",\"name\":\"插座\",\"nuid\":11,\"timer\":[],\"wakeuptype\":1,\"zwavedeviceid\":1733},{\"applianceid\":\"8B1BC31B-794F-440F-8F6F-8AE781B563CF\",\"associationscenelist\":[],\"capability\":[],\"codeid\":\"\",\"codeindex\":0,\"devicetype\":\"3\",\"infrareddeviceid\":0,\"majortype\":\"zWave\",\"name\":\"燃气\",\"nuid\":12,\"timer\":[],\"wakeuptype\":1,\"zwavedeviceid\":1734},{\"applianceid\":\"332F142D-246A-445F-A315-CADA176368B3\",\"associationscenelist\":[],\"capability\":[],\"codeid\":\"\",\"codeindex\":0,\"devicetype\":\"6\",\"infrareddeviceid\":0,\"majortype\":\"zWave\",\"name\":\"移动\",\"nuid\":13,\"timer\":[],\"wakeuptype\":1,\"zwavedeviceid\":1735},{\"applianceid\":\"CE2A6F0E-CD31-4CD6-BB01-861DE03E8CF8\",\"associationscenelist\":[{\"associationsceneid\":2091,\"channelid\":0,\"commandlist\":[{\"applianceid\":\"11369118-D561-4B9E-87EC-7F6699FEBC35\",\"commandid\":36460,\"commandjson\":[{\"operation\":\"open\"}],\"delay\":0,\"description\":\"机械臂开\",\"deviceid\":\"iRemote2005000000816\",\"endsecond\":86340,\"endtime\":\"23:59\",\"index\":0,\"startsecond\":0,\"starttime\":\"00:00\",\"weekday\":6,\"zwavecommand\":\"AEYAAyUB/wBIAAEAAEoAAyUD/wBHAAEE\",\"zwavecommandbase64\":\"AEYAAyUB/wBIAAEAAEoAAyUD/wBHAAEE\",\"zwavedeviceid\":1726}],\"devicestatus\":\"waterleak\",\"operator\":\"eq\",\"scenedbid\":3329,\"scenetype\":2,\"status\":255,\"zwavedeviceid\":1736}],\"capability\":[],\"codeid\":\"\",\"codeindex\":0,\"devicetype\":\"2\",\"infrareddeviceid\":0,\"majortype\":\"zWave\",\"name\":\"漏水\",\"nuid\":14,\"timer\":[],\"wakeuptype\":1,\"zwavedeviceid\":1736},{\"applianceid\":\"B0CA373E-D3E1-4B40-B6BD-3F1EC923AA09\",\"associationscenelist\":[{\"associationsceneid\":2092,\"channelid\":0,\"commandlist\":[{\"applianceid\":\"11369118-D561-4B9E-87EC-7F6699FEBC35\",\"commandid\":36459,\"commandjson\":[{\"operation\":\"close\"}],\"delay\":0,\"description\":\"机械臂关\",\"deviceid\":\"iRemote2005000000816\",\"endsecond\":86340,\"endtime\":\"23:59\",\"index\":0,\"startsecond\":0,\"starttime\":\"00:00\",\"weekday\":15,\"zwavecommand\":\"AEYAAyUBAABIAAEAAEoAAyUDAABHAAEE\",\"zwavecommandbase64\":\"AEYAAyUBAABIAAEAAEoAAyUDAABHAAEE\",\"zwavedeviceid\":1726}],\"devicestatus\":\"smoke\",\"operator\":\"eq\",\"scenedbid\":3330,\"scenetype\":2,\"status\":255,\"zwavedeviceid\":1737}],\"capability\":[],\"codeid\":\"\",\"codeindex\":0,\"devicetype\":\"1\",\"infrareddeviceid\":0,\"majortype\":\"zWave\",\"name\":\"烟感\",\"nuid\":15,\"timer\":[],\"wakeuptype\":1,\"zwavedeviceid\":1737},{\"applianceid\":\"203CED4E-6506-4845-8DA3-6FD4BCCEA786\",\"associationscenelist\":[],\"capability\":[],\"codeid\":\"\",\"codeindex\":0,\"devicetype\":\"13\",\"infrareddeviceid\":0,\"majortype\":\"zWave\",\"name\":\"窗帘\",\"nuid\":16,\"timer\":[],\"wakeuptype\":1,\"zwavedeviceid\":1738},{\"applianceid\":\"F607D6D6-0689-4DF8-B5DF-AB36FDD1FB39\",\"associationscenelist\":[],\"capability\":[{\"capabilitycode\":1,\"devicecapabilityid\":5368}],\"codeid\":\"\",\"codeindex\":0,\"devicetype\":\"5\",\"infrareddeviceid\":0,\"majortype\":\"zWave\",\"name\":\"门锁\",\"nuid\":17,\"timer\":[],\"wakeuptype\":1,\"zwavedeviceid\":1739},{\"applianceid\":\"325E66A2-E6C8-4F5E-9FC5-5EF616665A51\",\"associationscenelist\":[],\"capability\":[],\"codeid\":\"\",\"codeindex\":0,\"devicetype\":\"7\",\"infrareddeviceid\":0,\"majortype\":\"zWave\",\"name\":\"开关1\",\"nuid\":18,\"timer\":[],\"wakeuptype\":1,\"zwavedeviceid\":1743},{\"applianceid\":\"DF0DAF42-214A-4A2C-A331-7BF142F02FF7\",\"codeid\":\"AC_389\",\"codeindex\":0,\"codelibery\":[55,39,30,209,0,0,0,0,0,0,0,6,36,84,88,30,198],\"devicetype\":\"AC\",\"infrareddeviceid\":81,\"majortype\":\"infrared\",\"name\":\"空调\",\"nuid\":0,\"productorid\":\"McQuay\",\"timer\":[],\"wakeuptype\":1,\"zwavedeviceid\":0}],\"deviceid\":\"iRemote2005000000816\",\"name\":\"816\",\"phonenumber\":\"13502876070\",\"type\":0}]";
		Db.init();
		
		PhoneUserService pus = new PhoneUserService();
		PhoneUser pu = pus.query(110);
		
		SynchronizeDataAction a = new SynchronizeDataAction();
		a.setPhoneuser(pu);
		a.setMyremote(myremote);
		a.setLastupdatetime(Utils.formatTime(new Date()));
		
		a.execute();
		
		Db.commit();
	}
	
	private static void associationtest1()
	{
		String astring = "{\"applianceid\":\"B7C5DBC8-BBD5-4192-95E8-923912D564EA\",\"applianceuuid\":\"\",\"associationscenelist\":[{\"devicestatus\":\"close\",\"commandlist\":[{\"applianceid\":\"F3C94BE4-A95A-4C9E-8A15-B3D37686CBA0\",\"commandjson\":[{\"operation\":\"close\",\"channelid\":1}],\"description\":\"开关1关 一, 二, 三, 四, 五 00:00-23:59\",\"deviceid\":\"iRemote2005000000816\",\"endtime\":\"23:59\",\"zwavecommand\":[0,70,0,7,96,13,0,1,37,1,0,0,72,0,1,0,0,74,0,7,96,13,1,0,37,3,0,0,71,0,1,42,0,79,0,1,4],\"starttime\":\"00:00\",\"launchscenedbid\":0,\"infrareddeviceid\":0,\"weekday\":62,\"index\":0,\"delay\":0,\"zwavedeviceid\":2509},{\"applianceid\":\"F3C94BE4-A95A-4C9E-8A15-B3D37686CBA0\",\"commandjson\":[{\"operation\":\"close\",\"channelid\":3}],\"description\":\"开关3关 一, 二, 三, 四, 五 00:00-23:59\",\"deviceid\":\"iRemote2005000000816\",\"endtime\":\"23:59\",\"zwavecommand\":[0,70,0,7,96,13,0,3,37,1,0,0,72,0,1,0,0,74,0,7,96,13,3,0,37,3,0,0,71,0,1,42,0,79,0,1,4],\"starttime\":\"00:00\",\"launchscenedbid\":0,\"infrareddeviceid\":0,\"weekday\":62,\"index\":1,\"delay\":0,\"zwavedeviceid\":2509},{\"applianceid\":\"F3C94BE4-A95A-4C9E-8A15-B3D37686CBA0\",\"commandjson\":[{\"operation\":\"close\",\"channelid\":2}],\"description\":\"开关2关 一, 二, 三, 四, 五 00:00-23:59\",\"deviceid\":\"iRemote2005000000816\",\"endtime\":\"23:59\",\"zwavecommand\":[0,70,0,7,96,13,0,2,37,1,0,0,72,0,1,0,0,74,0,7,96,13,2,0,37,3,0,0,71,0,1,42,0,79,0,1,4],\"starttime\":\"00:00\",\"launchscenedbid\":0,\"infrareddeviceid\":0,\"weekday\":62,\"index\":2,\"delay\":0,\"zwavedeviceid\":2509},{\"applianceid\":\"74F28962-75E3-432A-8CD7-318F23D7DB84\",\"commandjson\":[{\"operation\":\"power\"}],\"description\":\"电源命令 一, 二, 三, 四, 五 00:00-23:59\",\"deviceid\":\"iRemote2005000000816\",\"endtime\":\"23:59\",\"infraredcode\":[4,1,0,14,0,40,0,10,48,0,1,72,-73,32,-33,0,0,47,0],\"starttime\":\"00:00\",\"launchscenedbid\":0,\"infrareddeviceid\":168,\"weekday\":62,\"index\":3,\"delay\":0,\"zwavedeviceid\":0}],\"channelid\":1,\"associationsceneid\":5149,\"scenedbid\":9708,\"status\":0},{\"devicestatus\":\"open\",\"commandlist\":[{\"applianceid\":\"F3C94BE4-A95A-4C9E-8A15-B3D37686CBA0\",\"commandjson\":[{\"operation\":\"open\",\"channelid\":1}],\"description\":\"开关1开 一, 二, 三, 四, 五 00:00-23:59\",\"deviceid\":\"iRemote2005000000816\",\"endtime\":\"23:59\",\"zwavecommand\":[0,70,0,7,96,13,0,1,37,1,-1,0,72,0,1,0,0,74,0,7,96,13,1,0,37,3,-1,0,71,0,1,42,0,79,0,1,4],\"starttime\":\"00:00\",\"launchscenedbid\":0,\"infrareddeviceid\":0,\"weekday\":62,\"index\":0,\"delay\":0,\"zwavedeviceid\":2509},{\"applianceid\":\"F3C94BE4-A95A-4C9E-8A15-B3D37686CBA0\",\"commandjson\":[{\"operation\":\"open\",\"channelid\":2}],\"description\":\"开关2开 一, 二, 三, 四, 五 00:00-23:59\",\"deviceid\":\"iRemote2005000000816\",\"endtime\":\"23:59\",\"zwavecommand\":[0,70,0,7,96,13,0,2,37,1,-1,0,72,0,1,0,0,74,0,7,96,13,2,0,37,3,-1,0,71,0,1,42,0,79,0,1,4],\"starttime\":\"00:00\",\"launchscenedbid\":0,\"infrareddeviceid\":0,\"weekday\":62,\"index\":1,\"delay\":0,\"zwavedeviceid\":2509},{\"applianceid\":\"F3C94BE4-A95A-4C9E-8A15-B3D37686CBA0\",\"commandjson\":[{\"operation\":\"open\",\"channelid\":3}],\"description\":\"开关3开 一, 二, 三, 四, 五 00:00-23:59\",\"deviceid\":\"iRemote2005000000816\",\"endtime\":\"23:59\",\"zwavecommand\":[0,70,0,7,96,13,0,3,37,1,-1,0,72,0,1,0,0,74,0,7,96,13,3,0,37,3,-1,0,71,0,1,42,0,79,0,1,4],\"starttime\":\"00:00\",\"launchscenedbid\":0,\"infrareddeviceid\":0,\"weekday\":62,\"index\":2,\"delay\":0,\"zwavedeviceid\":2509},{\"applianceid\":\"74F28962-75E3-432A-8CD7-318F23D7DB84\",\"commandjson\":[{\"operation\":\"power\"}],\"description\":\"电源命令 一, 二, 三, 四, 五 00:00-23:59\",\"deviceid\":\"iRemote2005000000816\",\"endtime\":\"23:59\",\"infraredcode\":[4,1,0,14,0,40,0,10,48,0,1,72,-73,32,-33,0,0,47,0],\"starttime\":\"00:00\",\"launchscenedbid\":0,\"infrareddeviceid\":168,\"weekday\":62,\"index\":3,\"delay\":0,\"zwavedeviceid\":0}],\"channelid\":1,\"associationsceneid\":5148,\"scenedbid\":9707,\"status\":255},{\"devicestatus\":\"close\",\"commandlist\":[{\"applianceid\":\"F3C94BE4-A95A-4C9E-8A15-B3D37686CBA0\",\"commandjson\":[{\"operation\":\"close\",\"channelid\":1}],\"description\":\"开关1关 一, 二, 三, 四, 五 00:00-23:59\",\"deviceid\":\"iRemote2005000000816\",\"endtime\":\"23:59\",\"zwavecommand\":[0,70,0,7,96,13,0,1,37,1,0,0,72,0,1,0,0,74,0,7,96,13,1,0,37,3,0,0,71,0,1,42,0,79,0,1,4],\"starttime\":\"00:00\",\"launchscenedbid\":0,\"infrareddeviceid\":0,\"weekday\":62,\"index\":0,\"delay\":0,\"zwavedeviceid\":2509},{\"applianceid\":\"F3C94BE4-A95A-4C9E-8A15-B3D37686CBA0\",\"commandjson\":[{\"operation\":\"close\",\"channelid\":2}],\"description\":\"开关2关 一, 二, 三, 四, 五 00:00-23:59\",\"deviceid\":\"iRemote2005000000816\",\"endtime\":\"23:59\",\"zwavecommand\":[0,70,0,7,96,13,0,2,37,1,0,0,72,0,1,0,0,74,0,7,96,13,2,0,37,3,0,0,71,0,1,42,0,79,0,1,4],\"starttime\":\"00:00\",\"launchscenedbid\":0,\"infrareddeviceid\":0,\"weekday\":62,\"index\":1,\"delay\":0,\"zwavedeviceid\":2509},{\"applianceid\":\"F3C94BE4-A95A-4C9E-8A15-B3D37686CBA0\",\"commandjson\":[{\"operation\":\"close\",\"channelid\":3}],\"description\":\"开关3关 一, 二, 三, 四, 五 00:00-23:59\",\"deviceid\":\"iRemote2005000000816\",\"endtime\":\"23:59\",\"zwavecommand\":[0,70,0,7,96,13,0,3,37,1,0,0,72,0,1,0,0,74,0,7,96,13,3,0,37,3,0,0,71,0,1,42,0,79,0,1,4],\"starttime\":\"00:00\",\"launchscenedbid\":0,\"infrareddeviceid\":0,\"weekday\":62,\"index\":2,\"delay\":0,\"zwavedeviceid\":2509}],\"channelid\":2,\"associationsceneid\":5151,\"scenedbid\":9715,\"status\":0},{\"devicestatus\":\"open\",\"commandlist\":[{\"applianceid\":\"F3C94BE4-A95A-4C9E-8A15-B3D37686CBA0\",\"commandjson\":[{\"operation\":\"close\",\"channelid\":1}],\"description\":\"开关1关 一, 二, 三, 四, 五 00:00-23:59\",\"deviceid\":\"iRemote2005000000816\",\"endtime\":\"23:59\",\"zwavecommand\":[0,70,0,7,96,13,0,1,37,1,0,0,72,0,1,0,0,74,0,7,96,13,1,0,37,3,0,0,71,0,1,42,0,79,0,1,4],\"starttime\":\"00:00\",\"launchscenedbid\":0,\"infrareddeviceid\":0,\"weekday\":62,\"index\":0,\"delay\":0,\"zwavedeviceid\":2509},{\"applianceid\":\"F3C94BE4-A95A-4C9E-8A15-B3D37686CBA0\",\"commandjson\":[{\"operation\":\"close\",\"channelid\":2}],\"description\":\"开关2关 一, 二, 三, 四, 五 00:00-23:59\",\"deviceid\":\"iRemote2005000000816\",\"endtime\":\"23:59\",\"zwavecommand\":[0,70,0,7,96,13,0,2,37,1,0,0,72,0,1,0,0,74,0,7,96,13,2,0,37,3,0,0,71,0,1,42,0,79,0,1,4],\"starttime\":\"00:00\",\"launchscenedbid\":0,\"infrareddeviceid\":0,\"weekday\":62,\"index\":1,\"delay\":0,\"zwavedeviceid\":2509},{\"applianceid\":\"F3C94BE4-A95A-4C9E-8A15-B3D37686CBA0\",\"commandjson\":[{\"operation\":\"close\",\"channelid\":3}],\"description\":\"开关3关 一, 二, 三, 四, 五 00:00-23:59\",\"deviceid\":\"iRemote2005000000816\",\"endtime\":\"23:59\",\"zwavecommand\":[0,70,0,7,96,13,0,3,37,1,0,0,72,0,1,0,0,74,0,7,96,13,3,0,37,3,0,0,71,0,1,42,0,79,0,1,4],\"starttime\":\"00:00\",\"launchscenedbid\":0,\"infrareddeviceid\":0,\"weekday\":62,\"index\":2,\"delay\":0,\"zwavedeviceid\":2509}],\"channelid\":2,\"associationsceneid\":5150,\"scenedbid\":9714,\"status\":255}],\"capability\":[],\"codeid\":\"\",\"timer\":[{\"description\":\"开关2关-开关1关\",\"zwavecommand\":[0,70,0,7,96,13,0,2,37,1,0,0,74,0,7,96,13,2,0,37,3,0,0,71,0,1,38,0,72,0,1,0,0,70,0,7,96,13,0,1,37,1,0,0,74,0,7,96,13,1,0,37,3,0,0,71,0,1,38,0,72,0,1,0],\"infraredcode\":[],\"time\":1287,\"timerid\":6524,\"valid\":1,\"weekday\":62,\"executor\":1},{\"description\":\"开关1开-开关2开\",\"zwavecommand\":[0,70,0,7,96,13,0,1,37,1,-1,0,74,0,7,96,13,1,0,37,3,-1,0,71,0,1,38,0,72,0,1,0,0,70,0,7,96,13,0,2,37,1,-1,0,74,0,7,96,13,2,0,37,3,-1,0,71,0,1,38,0,72,0,1,0],\"infraredcode\":[],\"time\":7,\"timerid\":6525,\"valid\":1,\"weekday\":62,\"executor\":1}],\"codelibery\":[],\"controlmodeid\":\"\",\"devicetype\":\"8\",\"productorid\":\"\",\"majortype\":\"zWave\",\"name\":\"开关2\",\"nuid\":38,\"infrareddeviceid\":0,\"codeindex\":0,\"wakeuptype\":1,\"zwavedeviceid\":2498}";
		Appliance a = JSON.parseObject(astring , Appliance.class) ;
		
		Db.init();
		SceneService ss = new SceneService();
		ZWaveDeviceService zds = new ZWaveDeviceService();
		ZWaveDevice z = zds.query(401) ;
		
		if ( z.getAssociationscenelist() == null )
			z.setAssociationscenelist(new ArrayList<Associationscene>());
		
		List<Associationscene> al = new ArrayList<Associationscene>();
		al.addAll(z.getAssociationscenelist());
		List<Associationscene> lst =Utils.merge(al, a.getAssociationscenelist(), new AssociationsceneComparator() , new AssociationsceneMerge() );

		for ( Associationscene ra : lst )
			if ( ra.getScenetype() == IRemoteConstantDefine.SCENE_TYPE_ASSOCIATION )
			{
				z.getAssociationscenelist().remove(ra);
				if ( ra.getScene() != null )
					ss.delete(ra.getScene());
			}
		for ( Associationscene ra : al )
			if ( !z.getAssociationscenelist().contains(ra))
			{
				ra.setScenetype(IRemoteConstantDefine.SCENE_TYPE_ASSOCIATION);
				z.getAssociationscenelist().add(ra);
			}
		
		
		if ( z.getAssociationscenelist() != null )
		{
			for ( Associationscene as : z.getAssociationscenelist())
			{
				if ( as.getScenetype() != IRemoteConstantDefine.SCENE_TYPE_ASSOCIATION )
					continue;
				if ( as.getZwavedevice() == null )
					as.setZwavedevice(z);
				
				Association2scene(z , as);
				
				if ( as.getScene().getCommandlist() != null )
				{
					for ( Command c : as.getScene().getCommandlist())
					{
						if ( c.getAssociationscene() == null )
							c.setAssociationscene(as);
						if ( c.getScene() == null )
							c.setScene(as.getScene());
						if ( StringUtils.isNotEmpty(c.getStarttime()))
							c.setStartsecond(Utils.time2second(c.getStarttime()));
						if ( StringUtils.isNotEmpty(c.getEndtime()))
							c.setEndsecond(Utils.time2second(c.getEndtime()));
						fillcommand(c);
					}
				}
				ss.saveOrUpdate(as.getScene());
			}
		}
		
		Db.commit();
	}
	
	private  static void Association2scene(ZWaveDevice z ,Associationscene as)
	{
		SceneService sceneservice = new SceneService();
		if ( as.getScene() == null )
		{
			as.setScenetype(IRemoteConstantDefine.SCENE_TYPE_ASSOCIATION);
			as.setScene(new Scene());
			
			as.getScene().setAssociationscenelist(Arrays.asList(new Associationscene[]{as}));
			as.getScene().setCommandlist(new ArrayList<Command>());
			as.getScene().getCommandlist().addAll(as.getCommandlist());
			//as.getScene().setPhoneuserid(this.phoneuser.getPhoneuserid());
			as.getScene().setSceneid(Utils.createtoken());
			as.getScene().setScenetype(IRemoteConstantDefine.SCENE_TYPE_ASSOCIATION);
			sceneservice.save(as.getScene());
			
			IOperationTranslator ot = OperationTranslatorStore.getInstance().getOperationTranslator(z.getMajortype(), z.getDevicetype());
			if ( ot != null )
			{
				ot.setZWavedevice(z);
				ot.setStatus(as.getStatus());
				as.setDevicestatus(ot.getDeviceStatus());
				as.setOperator(as.getOperator());
			}
		}
	}
	
	private static void fillcommand(Command c)
	{
		if ( c.getZwavedevice() == null && c.getInfrareddevice() == null ) 
		{
			if ( StringUtils.isNotBlank(c.getDeviceid()) && StringUtils.isNotBlank(c.getApplianceid()))
			{
				InfraredDeviceService ifs = new InfraredDeviceService();
				c.setInfrareddevice(ifs.query(c.getDeviceid(), c.getApplianceid()));
				if ( c.getInfrareddevice() == null )
				{
					ZWaveDeviceService zds = new ZWaveDeviceService();
					c.setZwavedevice(zds.querybydeviceidapplianceid(c.getDeviceid(), c.getApplianceid()));
				}
			}
		}
		
		IOperationTranslator ot = null ;
		if ( c.getZwavedevice() != null )
		{
			ot = OperationTranslatorStore.getInstance().getOperationTranslator(c.getZwavedevice().getMajortype(), c.getZwavedevice().getDevicetype());
			if ( ot != null )
			{
				ot.setZWavedevice(c.getZwavedevice());
				if ( c.getZwavecommand() != null )
					ot.setCommand(c.getZwavecommand());
				else if ( c.getZwavecommands() != null )
					ot.setCommand(c.getZwavecommands());
				if ( StringUtils.isNotBlank(c.getCommandjsonstr()))
					ot.setCommandjson(c.getCommandjsonstr());
				if ( c.getZwavecommand() == null && c.getZwavecommands() == null && c.getLaunchscenedbid() == null )
				{
					c.setZwavecommand(ot.getCommand());
					c.setZwavecommands(ot.getCommands());
				}
			}
		}
		else if ( c.getInfrareddevice() != null )
		{
			ot = OperationTranslatorStore.getInstance().getOperationTranslator(c.getInfrareddevice().getMajortype(), c.getInfrareddevice().getDevicetype());
			if ( ot != null )
			{
				ot.setInfrareddevice(c.getInfrareddevice());
				ot.setCommand(c.getInfraredcode());
				if ( StringUtils.isNotBlank(c.getCommandjsonstr()))
					ot.setCommandjson(c.getCommandjsonstr());
				if ( c.getInfraredcode() == null && c.getLaunchscenedbid() == null )
					c.setInfraredcode(ot.getCommand());
			}
		}
		if ( ot != null )
		{
			c.setCommandjsonstr(ot.getCommandjson());
		}
	}
}
