package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Blaupunkt extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Blaupunkt";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” ¿∂±¶(Blaupunkt) 1
"00e03f0042218098261c261c251d261c261c261d261b251b261c2684e6278098263b483d261c261c261d261c261b251b269cfe288099261c261d261c251b261c261c261c261c261c2600000000000000000000000000000000000000000000000000000000000000000000000000",
};
}