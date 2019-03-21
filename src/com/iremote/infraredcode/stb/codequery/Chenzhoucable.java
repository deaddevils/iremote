package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Chenzhoucable extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Chenzhoucable";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//������ ��������(Chenzhou cable) 1
"00e04700338233810f292028202820281f291f291f2965281f281f281f2865286628202865281f28202866291f2965291f281f281f29652a1f291f2965281f286529652865291f2965288993823a8086280000000000000000000000000000000000000000000000000000000000",
//������ ��������(Chenzhou cable) 2
"00e04700338233810f281f291f291f2820282028202865291f291f291f296528652920286628202820286528202866291f291f291f2965281f281f28652920286628662865292028652a8993823a8087280000000000000000000000000000000000000000000000000000000000",
};
}