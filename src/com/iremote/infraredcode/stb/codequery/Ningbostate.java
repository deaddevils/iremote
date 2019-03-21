package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Ningbostate extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Ningbostate";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ Äþ²¨Í¬ÖÝ(Ningbo state) 1
"00e0470033823481102820281f281f291f2a1f291f291f291f2866286628662966296528652a6529652820281f2866286629662820281f281f29652965291f2820282128662866296529899a823b8086280000000000000000000000000000000000000000000000000000000000",
};
}