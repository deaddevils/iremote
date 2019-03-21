package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Dongling extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Dongling";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” ∂´¡‚(Dongling) 1
"00e04b003381ee80f2241b251b2458231b231b231b235823572380f22458241c2459241b241b251b251b251b2585c581f780f2241c231c2359231c231d231c235923582480f22358231c2358231b231b231b231b241b240000000000000000000000000000000000000000000000",
};
}