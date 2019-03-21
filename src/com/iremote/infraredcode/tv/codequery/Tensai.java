package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Tensai extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Tensai";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 天际(Tensai) 1
"00a051003480cf80d23a2f3a80983b80973b2f3a80983b303a2f3980973a303a80963a303a303a80983b303b2f3a80983a303b80973a80963a303a80973a313a80973b80973a882280d580d13a303a80973a80963a303a80983a313a313a80973a2f3b80973a303a303a80973a00",
};
}