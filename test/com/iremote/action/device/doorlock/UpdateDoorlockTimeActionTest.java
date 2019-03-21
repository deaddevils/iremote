package com.iremote.action.device.doorlock;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

import org.apache.commons.codec.binary.Base64;

import com.iremote.action.helper.GatewayHelper;
import com.iremote.common.Utils;
import com.iremote.common.encrypt.AES;
import com.iremote.common.encrypt.Tea;
import com.iremote.domain.ZWaveDevice;
import com.iremote.service.ZWaveDeviceService;
import com.iremote.test.db.Db;
import com.mysql.jdbc.Util;

public class UpdateDoorlockTimeActionTest {

	public static void main(String[] args) {
		//Db.init();
		/*ZWaveDeviceService zds = new ZWaveDeviceService();
		ZWaveDevice zwaveDevice = zds.query(16847);
		if(zwaveDevice == null){
			return ;
		}
		String deviceid = zwaveDevice.getDeviceid();
		RemoteService rs = new RemoteService();
		Remote remote = rs.getIremotepassword(deviceid);
		String remoteTimezoneId = GatewayHelper.getRemoteTimezoneId(deviceid);
		if(remoteTimezoneId==null){//Europe/Sarajevo
			System.out.println("");
		}*/

		byte[] key2b = new byte[]{127, 17, -103, 5, -27, 109, 96, -88, -99, 117, 79, -85, -29, -44, -9, 17};
		byte[] key2 = Tea.encryptByTea(key2b, AES.decrypt("Paf48UyL1yAb81tjowj9frHvDkvTw4vk54GQHk9TjSk"), 32);
		String key2indb = AES.encrypt(key2);
		
		System.out.println("3");
		


		/*TimeZone timeZone = TimeZone.getTimeZone("Asia/Shanghai");
		String id = TimeZone.getDefault().getID();
		ZoneId america = ZoneId.of(remoteTimezoneId);
		LocalDateTime localTime = LocalDateTime.now(america);
		int year = localTime.getYear();
		int month = localTime.getMonthValue()-1;
		int day = localTime.getDayOfMonth();
		int hour = localTime.getHour();
		int min = localTime.getMinute();
		int sec = localTime.getSecond(); 
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
		String formattedDateTime = localTime.format(formatter);
		String[] timeArray = formattedDateTime.split("-");
		int year1 = Integer.parseInt(timeArray[0]);
		int month1 = Integer.parseInt(timeArray[1]);
		int day1 = Integer.parseInt(timeArray[2]);
		int hour1 = Integer.parseInt(timeArray[3]);
		int min1 = Integer.parseInt(timeArray[4]);
		int sec1 = Integer.parseInt(timeArray[5]);
		System.out.println(year/256+":"+((byte)(year%256) & 0xff));
		System.out.println((byte)(160) & 0xff);
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("1-"+formattedDateTime);
		//F0FE6B4C6E0A
		System.out.println(year+":"+month+day+hour+min+sec);
		Db.commit();*/
	}
}
