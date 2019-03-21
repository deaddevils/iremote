package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Yulin extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Yulin";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ ÓñÁÖ(Yulin) 1
"00e0470033823381102920281f291f291f291f291f282028662865296528652864286529652965281f281f29652920291f2965281f281f29652866282028652866291f28652965281f288993823b8085280000000000000000000000000000000000000000000000000000000000",
};
}