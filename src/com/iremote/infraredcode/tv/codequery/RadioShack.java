package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class RadioShack extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "RadioShack";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µçÊÓ ÆÌ×Ó(RadioShack) 1
"00e047003382348110281f281f2920286628652965281f29202865296628662820281f282028662965291f2965281f281f29642820282028202866281f286629652920296528662865288994823b8085280000000000000000000000000000000000000000000000000000000000",
};
}