package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class AviationTechnology extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "AviationTechnology";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ º½¿Æ(Aviation Technology) 1
"00e04b003381ee80f3241b2458231b241b241b2459231b231b2380f3241b241b241b241b241b241c231d231b2385cb81f780f3231b2358251b251b241b2459231c231b2380f3241b241b251b241b241b241b241c241c230000000000000000000000000000000000000000000000",
};
}