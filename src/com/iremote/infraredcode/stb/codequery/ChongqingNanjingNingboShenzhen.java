package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class ChongqingNanjingNingboShenzhen extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "ChongqingNanjingNingboShenzhen";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 重庆/南京/宁波/深圳(Chongqing Nanjing Ningbo Shenzhen) 1
"00e04700338235811029652820281f282028202820281f291f2966291f296528652965286728662866291f2920296528652865292028652866286628652920291f291f2965281f291f29899b823b80862a0000000000000000000000000000000000000000000000000000000000",
};
}