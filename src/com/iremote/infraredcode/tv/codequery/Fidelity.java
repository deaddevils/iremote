package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Fidelity extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Fidelity";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 富达(Fidelity) 1
"00a052003480cf80d23a80973a30392f3a80973b303a80973b303b2f3a313b313a303a80973b303b80973a80973a2f3b80963a303a80963a80973a80973a80973a80983b303b882280d780d33b80973b2f3a313b80983b303b80973a303b303a303b303a303b80973a2f3b809700",
//电视 富达(Fidelity) 2
"00a052003480cf80d23a80973a303a303b80963a303a80973a303a303a2f392f3a303a80973a313a80973b80973a313b80973b303b80983a80973a80973a80973a80963a303a882280d680d13a80973a303a303a80963a303a80983a313a303b2f3a313a303a80983a313a809700",
};
}