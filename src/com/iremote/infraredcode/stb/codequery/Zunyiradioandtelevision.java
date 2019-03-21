package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Zunyiradioandtelevision extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Zunyiradioandtelevision";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 遵义广电(Zunyi radio and television) 1
"00e047003382338110281f291f2965281f281f291f291f2820281f2967271f296528662865281f28652920291f286529662667271f291f291f2967276528202820281f286826652964288992823a8087290000000000000000000000000000000000000000000000000000000000",
};
}