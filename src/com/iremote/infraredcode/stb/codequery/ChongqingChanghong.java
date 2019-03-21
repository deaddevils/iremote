package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class ChongqingChanghong extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "ChongqingChanghong";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//������ ���쳤��(Chongqing Changhong) 1
"00e04700338234811028662820281f291f29202820291f291f281f2966291f2965296529652a652965281f28652a1f291f2965281f291f291f2965291f29662965291f296528652965288997823b8087280000000000000000000000000000000000000000000000000000000000",
//������ ���쳤��(Chongqing Changhong) 2
"00e0470033823481102966281f281f291f29202820291f291f281f28652a1f29652965286529662965281f28652920291f2965281f281f291f2a65291f286528652920286628652865288996823b8087280000000000000000000000000000000000000000000000000000000000",
};
}