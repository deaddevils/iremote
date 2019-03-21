package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Guangxiradioandtelevision extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Guangxiradioandtelevision";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 广西广电(Guangxi radio and television) 1
"00e0470033823381112920281f292028202820281f291f292029652865286529652867286628662965291f291f286528652965282028202820286628652920291f291f29662865296528899b823c8087280000000000000000000000000000000000000000000000000000000000",
//机顶盒 广西广电(Guangxi radio and television) 2
"00e0470033823481102820281f281f291f2a1f291f291f291f2866286628662966286628652a6529652820281f2866286629662820281f281f29652965291f2820282128662866296529899b823c8086290000000000000000000000000000000000000000000000000000000000",
};
}