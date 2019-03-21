package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Radiotone extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Radiotone";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” Radiotone(Radiotone) 1
"00e03f0042218098261c261c261d251c261d271d271c261c251d2684e5258097261d271d261c251b261c263b471c253c269cfe288098271d271c261c251d261c261c261d261d261b2500000000000000000000000000000000000000000000000000000000000000000000000000",
};
}