package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class TianjinChanghong extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "TianjinChanghong";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ Ìì½ò³¤ºç(Tianjin Changhong) 1
"00e0470033823481102865281f2920291f291f291f292028202866282028662966296528652a6529652820281f296628662966291f2865296529652966281f2920282028662920282029899b823c8087280000000000000000000000000000000000000000000000000000000000",
};
}