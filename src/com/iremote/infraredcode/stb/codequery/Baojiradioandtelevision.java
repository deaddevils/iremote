package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Baojiradioandtelevision extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Baojiradioandtelevision";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//������ �������(Baoji radio and television) 1
"00e047003382338110291f291f291f2866282028202866291f296629652865291f2965286628662820281f2965291f29652920281f291f2920286528202966291f28652a652965286628899b823b8087280000000000000000000000000000000000000000000000000000000000",
//������ �������(Baoji radio and television) 2
"00e047003382358110281f292028202866292028202965281f2965296528662820286628662966291f281f2865291f2965281f2820282028202866281f2965291f296528662966286528899b823b8086280000000000000000000000000000000000000000000000000000000000",
};
}