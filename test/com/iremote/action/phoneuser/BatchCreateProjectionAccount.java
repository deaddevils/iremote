package com.iremote.action.phoneuser;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.iremote.action.qrcode.PowerFreeDeviceQrcodeCreator;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.service.UserService;

public class BatchCreateProjectionAccount 
{
	private FileOutputStream fsql ;
	private FileOutputStream fuser ;
	private FileOutputStream furl ;

	private static int platform = IRemoteConstantDefine.PLATFORM_DORLINK;
	private static String prefix = "th16";
	private static String[] build = new String[]{"a","b","c","d",} ;
	private static int[] floor = new int[]{6 , 6 , 8 , 9};
	private static int[] room = new int[]{1 , 2 , 1, 1};
	
	public static void main1(String arg[])
	{
		BatchCreateProjectionAccount ba = new BatchCreateProjectionAccount();
		
		ba.init();
		
		for ( int b = 0 ; b < build.length ; b ++)
		{
			for ( int f = 1 ; f <= floor[b] ; f ++ )
			{
				for ( int r = 1 ; r <= room[b] ; r ++)
				{
					String pn = String.format("%s%s%d%02d", prefix ,build[b] , f , r).toLowerCase();
					ba.createAccount(pn,Utils.createsecuritytoken(6).toLowerCase(),Utils.createsecuritytoken(6),platform);
				}
			}
			
		}
		

		ba.close();
	}

	public static void main(String arg[])
	{
		BatchCreateProjectionAccount ba = new BatchCreateProjectionAccount();
		
		ba.init();
		
		for ( String pn : phonenumbers)
			ba.createAccount(pn,"666888",Utils.createsecuritytoken(6),platform);
		
		ba.close();
	}
	
	private void init()
	{
		try 
		{
			fsql = new FileOutputStream((String.format("E:\\projectionuser\\%s.sql", prefix)));
			fuser = new FileOutputStream((String.format("E:\\projectionuser\\%s_user.txt", prefix)));
			furl = new FileOutputStream((String.format("E:\\projectionuser\\%s_url.txt", prefix)));
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
	}
	
	private void close()
	{
		try 
		{
			fsql.close();
			fuser.close();
			furl.close();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}

	}
	
	private void createAccount(String phonenumber , String password , String password2, int platform)
	{
		UserService svr = new UserService();
		String ep = svr.encryptPassword(phonenumber, password);
		String ep2 = svr.encryptPassword(phonenumber, password2);
		
		String sql = String.format("Insert into phoneuser ( countrycode , phonenumber , password , alias , username , platform , token , usertype , createtime ,lastupdatetime )"
				+ "values ('86', '%s', '%s','%s','%s','%d','%s','%d' , now() , now());",phonenumber , ep , Utils.createtoken(),phonenumber , platform,ep2 , IRemoteConstantDefine.PHONEUSER_USER_TYPE_PROJECTION_TEMP_USER) ;
		String qrv = String.format("https://iremote.isurpass.com.cn/iremote/phoneuser/transfer?srcuser=%s&password=%s", phonenumber , password2);
		String qr = String.format("%s\thttps://iremote.isurpass.com.cn/iremote/phoneuser/transfer?srcuser=%s&password=%s", phonenumber , phonenumber , password2);
		
		String user = String.format("%s\t%s", phonenumber , password);
		try 
		{
			fsql.write(sql.getBytes());
			fuser.write(user.getBytes());
			furl.write(qr.getBytes());
			
			fsql.write("\n".getBytes());
			fuser.write("\n".getBytes());
			furl.write("\n".getBytes());
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		PowerFreeDeviceQrcodeCreator.createCode(qrv , String.format("E:\\projectionuser\\qrcode\\%s.png", phonenumber));
	}
	
	private static String[] phonenumbers = new String[]{"TH1601301",
			"TH1601302",
			"TH1601303",
			"TH1601304",
			"TH1601305",
			"TH1601306",
			"TH1601307",
			"TH1601308",
			"TH1601309",
			"TH1601310",
			"TH1601311",
			"TH1601312",
			"TH1601313",
			"TH1601314",
			"TH1601315",
			"TH1601316",
			"TH1601317",
			"TH1601318",
			"TH1601401",
			"TH1601402",
			"TH1601403",
			"TH1601404",
			"TH1601405",
			"TH1601406",
			"TH1601407",
			"TH1601408",
			"TH1601409",
			"TH1601410",
			"TH1601411",
			"TH1601412",
			"TH1601413",
			"TH1601414",
			"TH1601415",
			"TH1601416",
			"TH1601417",
			"TH1601418",
			"TH1601501",
			"TH1601502",
			"TH1601503",
			"TH1601504",
			"TH1601505",
			"TH1601506",
			"TH1601507",
			"TH1601508",
			"TH1601509",
			"TH1601510",
			"TH1601511",
			"TH1601512",
			"TH1601513",
			"TH1601514",
			"TH1601515",
			"TH1601516",
			"TH1601517",
			"TH1601518",
			"TH1601601",
			"TH1601602",
			"TH1601603",
			"TH1601604",
			"TH1601605",
			"TH1601606",
			"TH1601607",
			"TH1601608",
			"TH1601609",
			"TH1601610",
			"TH1601611",
			"TH1601612",
			"TH1601613",
			"TH1601614",
			"TH1601615",
			"TH1601616",
			"TH1601617",
			"TH1601618",
			"TH1601701",
			"TH1601702",
			"TH1601703",
			"TH1601704",
			"TH1601705",
			"TH1601706",
			"TH1601707",
			"TH1601708",
			"TH1601709",
			"TH1601710",
			"TH1601711",
			"TH1601712",
			"TH1601713",
			"TH1601714",
			"TH1601715",
			"TH1601716",
			"TH1601717",
			"TH1601718",
			"TH1601801",
			"TH1601802",
			"TH1601803",
			"TH1601804",
			"TH1601805",
			"TH1601806",
			"TH1601807",
			"TH1601808",
			"TH1601809",
			"TH1601810",
			"TH1601811",
			"TH1601812",
			"TH1601813",
			"TH1601814",
			"TH1601815",
			"TH1601816",
			"TH1601817",
			"TH1601818",
			"TH1601901",
			"TH1601902",
			"TH1601903",
			"TH1601904",
			"TH1601905",
			"TH1601906",
			"TH1601907",
			"TH1601908",
			"TH1601909",
			"TH1601910",
			"TH1601911",
			"TH1601912",
			"TH1601913",
			"TH1601914",
			"TH1601915",
			"TH1601916",
			"TH1601917",
			"TH1601918",
			"TH1602201",
			"TH1602202",
			"TH1602203",
			"TH1602204",
			"TH1602205",
			"TH1602206",
			"TH1602207",
			"TH1602208",
			"TH1602209",
			"TH1602210",
			"TH1602211",
			"TH1602212",
			"TH1602213",
			"TH1602214",
			"TH1602215",
			"TH1602216",
			"TH1602217",
			"TH1602218",
			"TH1602301",
			"TH1602302",
			"TH1602303",
			"TH1602304",
			"TH1602305",
			"TH1602306",
			"TH1602307",
			"TH1602308",
			"TH1602309",
			"TH1602310",
			"TH1602311",
			"TH1602312",
			"TH1602313",
			"TH1602314",
			"TH1602315",
			"TH1602316",
			"TH1602317",
			"TH1602318",
			"TH1602401",
			"TH1602402",
			"TH1602403",
			"TH1602404",
			"TH1602405",
			"TH1602406",
			"TH1602407",
			"TH1602408",
			"TH1602409",
			"TH1602410",
			"TH1602411",
			"TH1602412",
			"TH1602413",
			"TH1602414",
			"TH1602415",
			"TH1602416",
			"TH1602417",
			"TH1602418",
			"TH1602501",
			"TH1602502",
			"TH1602503",
			"TH1602504",
			"TH1602505",
			"TH1602506",
			"TH1602507",
			"TH1602508",
			"TH1602509",
			"TH1602510",
			"TH1602511",
			"TH1602512",
			"TH1602513",
			"TH1602514",
			"TH1602515",
			"TH1602516",
			"TH1602517",
			"TH1602518",
			"TH1602601",
			"TH1602602",
			"TH1602603",
			"TH1602604",
			"TH1602605",
			"TH1602606",
			"TH1602607",
			"TH1602608",
			"TH1602609",
			"TH1602610",
			"TH1602611",
			"TH1602612",
			"TH1602613",
			"TH1602614",
			"TH1602615",
			"TH1602616",
			"TH1602617",
			"TH1602618",
			"TH1602701",
			"TH1602702",
			"TH1602703",
			"TH1602704",
			"TH1602705",
			"TH1602706",
			"TH1602707",
			"TH1602708",
			"TH1602709",
			"TH1602710",
			"TH1602711",
			"TH1602712",
			"TH1602713",
			"TH1602714",
			"TH1602715",
			"TH1602716",
			"TH1602717",
			"TH1602718",
			"TH1602801",
			"TH1602802",
			"TH1602803",
			"TH1602804",
			"TH1602805",
			"TH1602806",
			"TH1602807",
			"TH1602808",
			"TH1602809",
			"TH1602810",
			"TH1602811",
			"TH1602812",
			"TH1602813",
			"TH1602814",
			"TH1602815",
			"TH1602816",
			"TH1602817",
			"TH1602818",
			"TH1602901",
			"TH1602902",
			"TH1602903",
			"TH1602904",
			"TH1602905",
			"TH1602906",
			"TH1602907",
			"TH1602908",
			"TH1602909",
			"TH1602910",
			"TH1602911",
			"TH1602912",
			"TH1602913",
			"TH1602914",
			"TH1602915",
			"TH1602916",
			"TH1602917",
			"TH1602918",
			"TH1603201",
			"TH1603202",
			"TH1603203",
			"TH1603204",
			"TH1603205",
			"TH1603206",
			"TH1603207",
			"TH1603208",
			"TH1603209",
			"TH1603210",
			"TH1603211",
			"TH1603212",
			"TH1603213",
			"TH1603214",
			"TH1603215",
			"TH1603216",
			"TH1603217",
			"TH1603218",
			"TH1603301",
			"TH1603302",
			"TH1603303",
			"TH1603304",
			"TH1603305",
			"TH1603306",
			"TH1603307",
			"TH1603308",
			"TH1603309",
			"TH1603310",
			"TH1603311",
			"TH1603312",
			"TH1603313",
			"TH1603314",
			"TH1603315",
			"TH1603316",
			"TH1603317",
			"TH1603318",
			"TH1603401",
			"TH1603402",
			"TH1603403",
			"TH1603404",
			"TH1603405",
			"TH1603406",
			"TH1603407",
			"TH1603408",
			"TH1603409",
			"TH1603410",
			"TH1603411",
			"TH1603412",
			"TH1603413",
			"TH1603414",
			"TH1603415",
			"TH1603416",
			"TH1603417",
			"TH1603418",
			"TH1603501",
			"TH1603502",
			"TH1603503",
			"TH1603504",
			"TH1603505",
			"TH1603506",
			"TH1603507",
			"TH1603508",
			"TH1603509",
			"TH1603510",
			"TH1603511",
			"TH1603512",
			"TH1603513",
			"TH1603514",
			"TH1603515",
			"TH1603516",
			"TH1603517",
			"TH1603518",
			"TH1603601",
			"TH1603602",
			"TH1603603",
			"TH1603604",
			"TH1603605",
			"TH1603606",
			"TH1603607",
			"TH1603608",
			"TH1603609",
			"TH1603610",
			"TH1603611",
			"TH1603612",
			"TH1603613",
			"TH1603614",
			"TH1603615",
			"TH1603616",
			"TH1603617",
			"TH1603618",
			"TH1603701",
			"TH1603702",
			"TH1603703",
			"TH1603704",
			"TH1603705",
			"TH1603706",
			"TH1603707",
			"TH1603708",
			"TH1603709",
			"TH1603710",
			"TH1603711",
			"TH1603712",
			"TH1603713",
			"TH1603714",
			"TH1603715",
			"TH1603716",
			"TH1603717",
			"TH1603718",
			"TH1603801",
			"TH1603802",
			"TH1603803",
			"TH1603804",
			"TH1603805",
			"TH1603806",
			"TH1603807",
			"TH1603808",
			"TH1603809",
			"TH1603810",
			"TH1603811",
			"TH1603812",
			"TH1603813",
			"TH1603814",
			"TH1603815",
			"TH1603816",
			"TH1603817",
			"TH1603818",
			"TH1603901",
			"TH1603902",
			"TH1603903",
			"TH1603904",
			"TH1603905",
			"TH1603906",
			"TH1603907",
			"TH1603908",
			"TH1603909",
			"TH1603910",
			"TH1603911",
			"TH1603912",
			"TH1603913",
			"TH1603914",
			"TH1603915",
			"TH1603916",
			"TH1603917",
			"TH1603918"};
}
