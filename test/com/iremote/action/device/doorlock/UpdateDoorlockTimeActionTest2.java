package com.iremote.action.device.doorlock;

import com.iremote.action.helper.GatewayHelper;
import com.iremote.action.helper.PhoneUserBlueToothHelper;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.NumberUtil;
import com.iremote.common.encrypt.AES;
import com.iremote.common.encrypt.Tea;
import com.iremote.domain.DeviceCapability;
import com.iremote.domain.ZWaveDevice;
import com.iremote.service.DeviceCapabilityService;
import com.iremote.service.ZWaveDeviceService;
import com.iremote.test.db.Db;
import org.apache.commons.codec.binary.Base64;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Set;
import java.util.TimeZone;

public class UpdateDoorlockTimeActionTest2 {

	public static void main(String[] args) {
		Db.init();
		ZWaveDeviceService zds = new ZWaveDeviceService();
		ZWaveDevice zwaveDevice = zds.query(16847);

		String deviceid = zwaveDevice.getDeviceid();
		String remoteTimezoneId = GatewayHelper.getRemoteTimezoneId(deviceid);

		TimeZone tz = TimeZone.getTimeZone(remoteTimezoneId);
		Set<String> availableZoneIds = ZoneId.getAvailableZoneIds();
		String[] availableIDs = TimeZone.getAvailableIDs();
		
		/*try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("d:\\eeeeesefsewweabbbccc.txt"));
			for (String s : availableIDs) {
				bw.write(s);
				bw.newLine();
				bw.flush();
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		ZoneId zone = ZoneId.of(remoteTimezoneId);
		TimeZone.getTimeZone(zone);
		LocalDateTime localTime = LocalDateTime.now(zone);
		int year = localTime.getYear();
		int month = localTime.getMonthValue()-1;
		int day = localTime.getDayOfMonth();
		int hour = localTime.getHour();
		int min = localTime.getMinute();
		int sec = localTime.getSecond(); 
		
		DeviceCapabilityService dcs = new DeviceCapabilityService();
		DeviceCapability macaddressca = dcs.query(16847, IRemoteConstantDefine.DEVICE_CAPABILITY_BLUE_TOOTH_LOCK_MAC_ADDRESS);
		DeviceCapability snca = dcs.query(16847, IRemoteConstantDefine.DEVICE_CAPABILITY_BLUE_TOOTH_LOCK_SEQUENCE);
		DeviceCapability key2ca = dcs.query(16847, IRemoteConstantDefine.DEVICE_CAPABILITY_BLUE_TOOTH_LOCK_KEY_2);
		
		byte[] macbytes = NumberUtil.hexToBytes(macaddressca.getCapabilityvalue());
		byte[] snbytes = NumberUtil.intToByte4(Integer.parseInt(snca.getCapabilityvalue()));
		byte[] yearbytes = new byte[]{(byte)(year/256),(byte)(year%256)};
		byte[] timePacket = new byte[18];
		System.arraycopy(macbytes, 0, timePacket, 0, 6);
		System.arraycopy(snbytes, 0, timePacket, 6, 4);
		System.arraycopy(yearbytes, 0, timePacket, 10, 2);
		System.arraycopy(new byte[]{(byte)month}, 0, timePacket, 12, 1);
		System.arraycopy(new byte[]{(byte)day}, 0, timePacket, 13, 1);
		System.arraycopy(new byte[]{(byte)hour}, 0, timePacket, 14, 1);
		System.arraycopy(new byte[]{(byte)min}, 0, timePacket, 15, 1);
		System.arraycopy(new byte[]{(byte)sec}, 0, timePacket, 16, 1);
		PhoneUserBlueToothHelper.addParityBit(timePacket);
		byte[] key2bytes1 = AES.decrypt(key2ca.getCapabilityvalue());
		byte[] encryptByTea = Tea.encryptByTea(timePacket, key2bytes1, 16);//Tea.encrypt(timePacket, 0, key2bytes2, 16);
		String timepackage = Base64.encodeBase64String(encryptByTea);
		
		byte[] decryptByTea = Tea.decryptByTea(Base64.decodeBase64(timepackage), key2bytes1, 16);
		
		byte[] test = new byte[]{49,50,51,52,53,54,55,56,49,50,51,52,53,54,55};
		byte[] b1 = NumberUtil.intToByte4(1);
		byte[] b2 = NumberUtil.intToByte4(2);
		byte[] b4 = NumberUtil.intToByte4(4);
		byte[] b8 = NumberUtil.intToByte4(8);
		byte[] b16 = new byte[16];
		System.arraycopy(b1, 0, b16, 0, 4);
		System.arraycopy(b2, 0, b16, 4, 4);
		System.arraycopy(b4, 0, b16, 8, 4);
		System.arraycopy(b8, 0, b16, 12, 4);
		byte[] encryptByTea2 = Tea.encryptByTea(test, b16, 16);
		
		byte[] decryptByTea22 = Tea.decryptByTea(encryptByTea2, b16,16);
		Db.commit();
	}
}
