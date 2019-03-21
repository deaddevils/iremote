package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Electrograph extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Electrograph";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 传真(Electrograph) 1
"00a052003480cf80d23a2f3a80973b80973b2f3a80983c303a80963a303a303a2f3b303a303a80973a303a303a80973b2f3a80983a303a80973a80973a80973a80973a80983b882380d680d23a313a80973b80973a313b80983a303b80973a303a2f3a303a303a303a80973a2f00",
//电视 传真(Electrograph) 2
"00a052003380cf80d23a303b80973a80973a2f3b80973a303a80973b303a2f3a313a303a303a80973b2f3a313b80973b303a80983a303a80973a80963a80973b80973b80973a882480d680d23a313b80973b80973a303b80963a303a80963a303a303a2f3a303a303a80983b3000",
};
}