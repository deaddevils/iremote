package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class HenanVCOM extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "HenanVCOM";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 河南威科姆(Henan VCOM) 1
"00e04700338234811028652920291f291f291f281f281f291f291f29652965286529652866286528652820281f29652965281f2920281f291f29652965281f281f2a65296528652865288996823c8087280000000000000000000000000000000000000000000000000000000000",
};
}