package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Benxiradioandtelevision extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Benxiradioandtelevision";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 本溪广电(Benxi radio and television) 1
"00e0470033823481112820291f28652a1f291f291f2965282028662866291f29652965281f281f28652965286728202866281f291f2a1f291f291f282028662820286628652866286529899b823c8086290000000000000000000000000000000000000000000000000000000000",
//机顶盒 本溪广电(Benxi radio and television) 2
"00e047003582328110281f29202965281f2920281f2866282028662866281f28652a65291f291f28662866286528202966291f281f2920291f291f2920286528202866286528652a6529899b823c8086290000000000000000000000000000000000000000000000000000000000",
};
}