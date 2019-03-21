package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Guest extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Guest";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ À´±ö(Guest) 1
"00e047003382328110281f291f2a1f291f291f291f281f28652966286628652866286529652965281f281f2965282028202866281f281f28662865291f29652865291f296628662820288994823b8086280000000000000000000000000000000000000000000000000000000000",
};
}