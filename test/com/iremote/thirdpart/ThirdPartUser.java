package com.iremote.thirdpart;

import com.iremote.common.Utils;
import com.iremote.service.ThirdPartService;

public class ThirdPartUser {

	public static void main(String arg[])
	{
		ThirdPartService svr = new ThirdPartService();
		String name = "tp_jwzh_qzhw";
		if ( arg != null && arg.length > 0)
			name = arg[0];
		String pw = Utils.createsecuritytoken(24);
		String epw = svr.encryptPassword(name, pw);
		
		System.out.println(String.format("name=%s,password=%s,encryped password=%s" , name , pw , epw));
		System.out.println(String.format("insert into thirdpart(code,name,password,platform,adminprefix) values('%s','name','%s',0,'prefix');" , name , epw));
		
		//name=thirdparter_jwzh,password=5761ae947f164bf7bdc979c390a18df3594199,encryped password=APvp4xTpAYN5Da+OxHYaFINCHdWSO+2r
		//name=thirdparter_wcj001,password=e1c58dd4d2d34d21b9e86c6ac35303e2141273,encryped password=aoKEuQybjmyZIyhuBqn1qnMqHh/DmgEP
		//name=thirdparter_zf_doorlink,password=f9b3a8605fec4cd1a24d6e417cb85d20125034,encryped password=2TwiF1lkuTXlZX8ZmyinChHUNfJHMZWC
		//name=thirdparter_Temp_test,password=9b73c1f5dc834ca0943310577c60dd1f515727,encryped password=TSLrpjFPJgAZHxUfVQu7MqDrNZQCAKk5

	}
}
