package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Tonghua extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Tonghua";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ Í¨»¯(Tonghua) 1
"00e027003420652920281f28662865286628202865286529652966281f2920282028652820292029899b823c808629000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}