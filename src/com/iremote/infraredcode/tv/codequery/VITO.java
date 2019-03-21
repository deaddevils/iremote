package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class VITO extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "VITO";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µçÊÓ ¾°ÐÂ(VITO) 1
"00e04700338234811028652920291f291f2865281f291f2a1f291f296528652965282029652864286628202866286528202866291f2920282028652820281f29652920286528662965298995823b8086280000000000000000000000000000000000000000000000000000000000",
};
}