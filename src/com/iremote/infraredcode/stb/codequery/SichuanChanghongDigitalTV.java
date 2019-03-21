package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class SichuanChanghongDigitalTV extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "SichuanChanghongDigitalTV";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 四川长虹数字电视(Sichuan ChanghongDigitalTV) 1
"00e0470033823481102820281f291f2920291f29652920281f29662820286628662820281f281f2a65291f2965282028662865281f292028202965281f2966291f292028652866286628899b823b8087280000000000000000000000000000000000000000000000000000000000",
};
}