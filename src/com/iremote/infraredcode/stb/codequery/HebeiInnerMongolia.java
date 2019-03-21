package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class HebeiInnerMongolia extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "HebeiInnerMongolia";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 河北/内蒙古(Hebei Inner Mongolia) 1
"00e0470033823481102820281f281f2865291f291f291f2820282028662866291f29202a65296528652965286628662820281f291f2965291f291f292028202866286528652820296528899b823c8086280000000000000000000000000000000000000000000000000000000000",
};
}