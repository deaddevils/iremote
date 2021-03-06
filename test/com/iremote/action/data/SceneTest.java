package com.iremote.action.data;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.iremote.domain.Scene;
import com.iremote.domain.SceneNotification;
import com.iremote.service.SceneService;
import com.iremote.test.db.Db;

public class SceneTest {

	public static void main(String arg[])
	{
//		String s = "[{\"commandlist\":[{\"applianceid\":\"3b32f61291e040acae6bab654107e162\",\"delay\":2,\"description\":\"关锁\",\"deviceid\":\"iRemote2005000000040\",\"index\":0,\"infraredcode\":[30,7,0,23,0,70,0,3,98,1,-1,0,72,0,1,0,0,71,0,1,33,0,31,0,2,-106,1,115],\"zwavecommand\":[30,7,0,23,0,70,0,3,98,1,-1,0,72,0,1,0,0,71,0,1,33,0,31,0,2,-106,1,115]},{\"applianceid\":\"adc56ba266774964be53fddba45a0c5b\",\"delay\":0,\"description\":\"开关1关闭|开关2关闭|开关3关闭\",\"deviceid\":\"iRemote2005000000040\",\"index\":1,\"infraredcode\":[30,7,0,42,0,70,0,7,96,13,0,1,37,1,0,0,12,0,11,0,74,0,7,96,13,1,0,37,3,0,0,72,0,1,0,0,71,0,1,29,0,31,0,2,-106,1,-94],\"zwavecommands\":[30,7,0,42,0,70,0,7,96,13,0,1,37,1,0,0,12,0,11,0,74,0,7,96,13,1,0,37,3,0,0,72,0,1,0,0,71,0,1,29,0,31,0,2,-106,1,-94,30,7,0,42,0,70,0,7,96,13,0,2,37,1,0,0,12,0,11,0,74,0,7,96,13,2,0,37,3,0,0,72,0,1,0,0,71,0,1,29,0,31,0,2,-106,1,-94,30,7,0,42,0,70,0,7,96,13,0,3,37,1,0,0,12,0,11,0,74,0,7,96,13,3,0,37,3,0,0,72,0,1,0,0,71,0,1,29,0,31,0,2,-106,1,-94]}],\"icon\":\"9\",\"name\":\"关门\",\"phonenumber\":\"18926482498\",\"sceneid\":\"a5af271300914942a0dd3c8aa67bf231\"},{\"commandlist\":[{\"applianceid\":\"3b32f61291e040acae6bab654107e162\",\"delay\":2,\"description\":\"开锁\",\"deviceid\":\"iRemote2005000000040\",\"index\":0,\"infraredcode\":[30,7,0,23,0,70,0,3,98,1,1,0,72,0,1,0,0,71,0,1,33,0,31,0,2,-106,1,-115],\"zwavecommand\":[30,7,0,23,0,70,0,3,98,1,1,0,72,0,1,0,0,71,0,1,33,0,31,0,2,-106,1,-115]},{\"applianceid\":\"adc56ba266774964be53fddba45a0c5b\",\"delay\":0,\"description\":\"开关1打开\",\"deviceid\":\"iRemote2005000000040\",\"index\":1,\"infraredcode\":[30,7,0,42,0,70,0,7,96,13,0,1,37,1,-1,0,12,0,11,0,74,0,7,96,13,1,0,37,3,-1,0,72,0,1,0,0,71,0,1,29,0,31,0,2,-106,1,-94],\"zwavecommands\":[30,7,0,42,0,70,0,7,96,13,0,1,37,1,-1,0,12,0,11,0,74,0,7,96,13,1,0,37,3,-1,0,72,0,1,0,0,71,0,1,29,0,31,0,2,-106,1,-94]}],\"icon\":\"8\",\"name\":\"开门\",\"phonenumber\":\"18926482498\",\"sceneid\":\"531d7a98bc894c8085d914acf6834f3a\"},{\"commandlist\":[{\"applianceid\":\"adc56ba266774964be53fddba45a0c5b\",\"delay\":0,\"description\":\"开关1打开|开关2打开|开关3打开\",\"deviceid\":\"iRemote2005000000040\",\"index\":0,\"infraredcode\":[30,7,0,42,0,70,0,7,96,13,0,1,37,1,-1,0,12,0,11,0,74,0,7,96,13,1,0,37,3,-1,0,72,0,1,0,0,71,0,1,29,0,31,0,2,-106,1,-94],\"zwavecommands\":[30,7,0,42,0,70,0,7,96,13,0,1,37,1,-1,0,12,0,11,0,74,0,7,96,13,1,0,37,3,-1,0,72,0,1,0,0,71,0,1,29,0,31,0,2,-106,1,-94,30,7,0,42,0,70,0,7,96,13,0,2,37,1,-1,0,12,0,11,0,74,0,7,96,13,2,0,37,3,-1,0,72,0,1,0,0,71,0,1,29,0,31,0,2,-106,1,-94,30,7,0,42,0,70,0,7,96,13,0,3,37,1,-1,0,12,0,11,0,74,0,7,96,13,3,0,37,3,-1,0,72,0,1,0,0,71,0,1,29,0,31,0,2,-106,1,-94]}],\"icon\":\"1\",\"name\":\"开灯\",\"phonenumber\":\"18926482498\",\"sceneid\":\"ee8a723e8dc845659f9f42ff36785fcc\"},{\"commandlist\":[{\"applianceid\":\"adc56ba266774964be53fddba45a0c5b\",\"delay\":0,\"description\":\"开关1关闭|开关2关闭|开关3关闭\",\"deviceid\":\"iRemote2005000000040\",\"index\":0,\"infraredcode\":[30,7,0,42,0,70,0,7,96,13,0,1,37,1,0,0,12,0,11,0,74,0,7,96,13,1,0,37,3,0,0,72,0,1,0,0,71,0,1,29,0,31,0,2,-106,1,-94],\"zwavecommands\":[30,7,0,42,0,70,0,7,96,13,0,1,37,1,0,0,12,0,11,0,74,0,7,96,13,1,0,37,3,0,0,72,0,1,0,0,71,0,1,29,0,31,0,2,-106,1,-94,30,7,0,42,0,70,0,7,96,13,0,2,37,1,0,0,12,0,11,0,74,0,7,96,13,2,0,37,3,0,0,72,0,1,0,0,71,0,1,29,0,31,0,2,-106,1,-94,30,7,0,42,0,70,0,7,96,13,0,3,37,1,0,0,12,0,11,0,74,0,7,96,13,3,0,37,3,0,0,72,0,1,0,0,71,0,1,29,0,31,0,2,-106,1,-94]}],\"icon\":\"11\",\"name\":\"关灯\",\"phonenumber\":\"18926482498\",\"sceneid\":\"55c35e7fea6e4d23bfc2125e57745a79\"},{\"commandlist\":[{\"applianceid\":\"3b32f61291e040acae6bab654107e162\",\"delay\":2,\"description\":\"开锁\",\"deviceid\":\"iRemote2005000000040\",\"index\":0,\"infraredcode\":[30,7,0,23,0,70,0,3,98,1,1,0,72,0,1,0,0,71,0,1,33,0,31,0,2,-106,1,-115],\"zwavecommand\":[30,7,0,23,0,70,0,3,98,1,1,0,72,0,1,0,0,71,0,1,33,0,31,0,2,-106,1,-115]},{\"applianceid\":\"adc56ba266774964be53fddba45a0c5b\",\"delay\":0,\"description\":\"开关1打开|开关2关闭|开关3打开\",\"deviceid\":\"iRemote2005000000040\",\"index\":1,\"infraredcode\":[30,7,0,42,0,70,0,7,96,13,0,1,37,1,-1,0,12,0,11,0,74,0,7,96,13,1,0,37,3,-1,0,72,0,1,0,0,71,0,1,29,0,31,0,2,-106,1,-94],\"zwavecommands\":[30,7,0,42,0,70,0,7,96,13,0,1,37,1,-1,0,12,0,11,0,74,0,7,96,13,1,0,37,3,-1,0,72,0,1,0,0,71,0,1,29,0,31,0,2,-106,1,-94,30,7,0,42,0,70,0,7,96,13,0,2,37,1,0,0,12,0,11,0,74,0,7,96,13,2,0,37,3,0,0,72,0,1,0,0,71,0,1,29,0,31,0,2,-106,1,-94,30,7,0,42,0,70,0,7,96,13,0,3,37,1,-1,0,12,0,11,0,74,0,7,96,13,3,0,37,3,-1,0,72,0,1,0,0,71,0,1,29,0,31,0,2,-106,1,-94]}],\"icon\":\"8\",\"name\":\"到家\",\"phonenumber\":\"18926482498\",\"sceneid\":\"7a96343d51304448a7f743a251ebed6f\"},{\"commandlist\":[{\"applianceid\":\"adc56ba266774964be53fddba45a0c5b\",\"delay\":2,\"description\":\"开关1打开\",\"deviceid\":\"iRemote2005000000040\",\"index\":0,\"infraredcode\":[30,7,0,42,0,70,0,7,96,13,0,1,37,1,-1,0,12,0,11,0,74,0,7,96,13,1,0,37,3,-1,0,72,0,1,0,0,71,0,1,29,0,31,0,2,-106,1,-94],\"zwavecommands\":[30,7,0,42,0,70,0,7,96,13,0,1,37,1,-1,0,12,0,11,0,74,0,7,96,13,1,0,37,3,-1,0,72,0,1,0,0,71,0,1,29,0,31,0,2,-106,1,-94]},{\"applianceid\":\"adc56ba266774964be53fddba45a0c5b\",\"delay\":2,\"description\":\"开关1关闭|开关2打开\",\"deviceid\":\"iRemote2005000000040\",\"index\":1,\"infraredcode\":[30,7,0,42,0,70,0,7,96,13,0,1,37,1,0,0,12,0,11,0,74,0,7,96,13,1,0,37,3,0,0,72,0,1,0,0,71,0,1,29,0,31,0,2,-106,1,-94],\"zwavecommands\":[30,7,0,42,0,70,0,7,96,13,0,1,37,1,0,0,12,0,11,0,74,0,7,96,13,1,0,37,3,0,0,72,0,1,0,0,71,0,1,29,0,31,0,2,-106,1,-94,30,7,0,42,0,70,0,7,96,13,0,2,37,1,-1,0,12,0,11,0,74,0,7,96,13,2,0,37,3,-1,0,72,0,1,0,0,71,0,1,29,0,31,0,2,-106,1,-94]},{\"applianceid\":\"adc56ba266774964be53fddba45a0c5b\",\"delay\":2," 
//			+ "\"description\":\"|开关3打开\",\"deviceid\":\"iRemote2005000000040\",\"index\":2,\"infraredcode\":[],\"zwavecommands\":[30,7,0,42,0,70,0,7,96,13,0,3,37,1,-1,0,12,0,11,0,74,0,7,96,13,3,0,37,3,-1,0,72,0,1,0,0,71,0,1,29,0,31,0,2,-106,1,-94]},{\"applianceid\":\"adc56ba266774964be53fddba45a0c5b\",\"delay\":2,\"description\":\"开关1关闭|开关2关闭|开关3关闭\",\"deviceid\":\"iRemote2005000000040\",\"index\":3,\"infraredcode\":[30,7,0,42,0,70,0,7,96,13,0,1,37,1,0,0,12,0,11,0,74,0,7,96,13,1,0,37,3,0,0,72,0,1,0,0,71,0,1,29,0,31,0,2,-106,1,-94],\"zwavecommands\":[30,7,0,42,0,70,0,7,96,13,0,1,37,1,0,0,12,0,11,0,74,0,7,96,13,1,0,37,3,0,0,72,0,1,0,0,71,0,1,29,0,31,0,2,-106,1,-94,30,7,0,42,0,70,0,7,96,13,0,2,37,1,0,0,12,0,11,0,74,0,7,96,13,2,0,37,3,0,0,72,0,1,0,0,71,0,1,29,0,31,0,2,-106,1,-94,30,7,0,42,0,70,0,7,96,13,0,3,37,1,0,0,12,0,11,0,74,0,7,96,13,3,0,37,3,0,0,72,0,1,0,0,71,0,1,29,0,31,0,2,-106,1,-94]},{\"applianceid\":\"adc56ba266774964be53fddba45a0c5b\",\"delay\":2,\"description\":\"开关1打开|开关3打开\",\"deviceid\":\"iRemote2005000000040\",\"index\":4,\"infraredcode\":[30,7,0,42,0,70,0,7,96,13,0,1,37,1,-1,0,12,0,11,0,74,0,7,96,13,1,0,37,3,-1,0,72,0,1,0,0,71,0,1,29,0,31,0,2,-106,1,-94],\"zwavecommands\":[30,7,0,42,0,70,0,7,96,13,0,1,37,1,-1,0,12,0,11,0,74,0,7,96,13,1,0,37,3,-1,0,72,0,1,0,0,71,0,1,29,0,31,0,2,-106,1,-94,30,7,0,42,0,70,0,7,96,13,0,3,37,1,-1,0,12,0,11,0,74,0,7,96,13,3,0,37,3,-1,0,72,0,1,0,0,71,0,1,29,0,31,0,2,-106,1,-94]},{\"applianceid\":\"adc56ba266774964be53fddba45a0c5b\",\"delay\":2,\"description\":\"开关1关闭|开关2打开\",\"deviceid\":\"iRemote2005000000040\",\"index\":5,\"infraredcode\":[30,7,0,42,0,70,0,7,96,13,0,1,37,1,0,0,12,0,11,0,74,0,7,96,13,1,0,37,3,0,0,72,0,1,0,0,71,0,1,29,0,31,0,2,-106,1,-94],\"zwavecommands\":[30,7,0,42,0,70,0,7,96,13,0,1,37,1,0,0,12,0,11,0,74,0,7,96,13,1,0,37,3,0,0,72,0,1,0,0,71,0,1,29,0,31,0,2,-106,1,-94,30,7,0,42,0,70,0,7,96,13,0,2,37,1,-1,0,12,0,11,0,74,0,7,96,13,2,0,37,3,-1,0,72,0,1,0,0,71,0,1,29,0,31,0,2,-106,1,-94]}],\"icon\":\"2\",\"name\":\"开关\",\"phonenumber\":\"18926482498\",\"sceneid\":\"522da030cad14f229d89f741f7498748\"}]";
//		
//		List<Scene> scene = JSON.parseArray(s , Scene.class);
//		
//		net.sf.json.JSONArray ja =net.sf.json.JSONArray.fromObject(scene); 
//		System.out.println(ja.toString());
		
		add();
		//delete();
	}
	
	public static void add()
	{
		Db.init();
		
		Scene s = new Scene();
		
		s.setName("test");
		s.setSceneid("fjdksfds");
		s.setScenetype(1);
		SceneNotification sn = new SceneNotification();
		s.setScenenotification(sn);
		sn.setScene(s);
		sn.setMail("fdksaf");
		
		
		SceneService ss = new SceneService();
		ss.save(s);
		
		Db.commit();
	}
	
	public static void delete()
	{
		Db.init();
		
		SceneService ss = new SceneService();

		ss.delete(ss.query(29085));
		
		Db.commit();
	}
}
