package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Standard extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Standard";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 斯坦达德(Standard) 1
"00a051003480cc80d33b303a80973a80983b303a80973a303a303a80963a303a80973a313a303a80983a303b303a80983a303a80973a80973b303a80973a303b80973a80973a882480d480d23b2f3a80983a80973a303a80963a303a2f3a80973b2f3a80983a303b303b80963a00",
};
}