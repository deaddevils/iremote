package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Yuxiradioandtelevision extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Yuxiradioandtelevision";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//������ ��Ϫ���(Yuxi radio and television) 1
"00e0470033823581102a1f2920282028662820281f28652820296528652965291f286628662865281f29202866281f2865291f291f2920281f286728202866281f296529652966286628899b823c8087280000000000000000000000000000000000000000000000000000000000",
//������ ��Ϫ���(Yuxi radio and television) 2
"00e047003382348110281f282028202866281f281f2865291f2965286628662820286629662965281f2a1f2965291f2866282028202820281f28652a1f29652920286629662866286628899c823b8087280000000000000000000000000000000000000000000000000000000000",
};
}