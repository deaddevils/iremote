package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Aventura extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Aventura";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 阿文图拉(Aventura) 1
"00a052003480cf80d33b80983a313b303b80973a2f3b80973a303a303a2f3a303a303a80973b303a80973b80973a303a80973b303a80983a80983a80973b80973b80973b303b882580d780d13a80973a2f3a303a80983a313a80983a303b303a303b2f3b303a80973a303a809600",
};
}