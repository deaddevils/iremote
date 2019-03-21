package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Qinghairadioandtelevision extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Qinghairadioandtelevision";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 青海广电(Qinghai radio and television) 1
"00e0470033823481112820291f281f281f291f291f2966281f2821292028662865291f2a65291f2920286629202866281f292029202965281f292028652920286528662866281f296529899b823c8086290000000000000000000000000000000000000000000000000000000000",
};
}