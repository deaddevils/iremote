package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Roadstar extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Roadstar";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” ≥®≈Ò(Roadstar) 1
"00a051003480ce80d13a303a80973a80983b303b80973a303a303a80963a303a80973a2f3a313b80973a303b303a80973a2f3b80973a80973b303b80973a313b80973b80973a882380d680d23b303b80973a80973a303a80973a303a313a80973b2f3a80983b303b303b80973a00",
};
}