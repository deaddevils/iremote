package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class ESA extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "ESA";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 欧空局(ESA) 1
"00a052003480cd80d23a80963a303a303a80963a303a80973a313a303a2f3a303a303a80973b303b80973a80983a303a80973a303a80973a80973a80983b80973b80973a2f3b882380d780d33b80973b2f3a313a80973b2f3a80983a303a303b303b303a303b80983a303a809600",
//电视 欧空局(ESA) 2
"00a052003480cf80d23a80973a303a2f3b80973a303a80973c2f3a313b303b2f3a313a80983a303b80973a80973a2f3a80973b303a80983b80973b80973a80963a80973a303a882480d680d13a80973a2f3a303a80973a2f3a80973b2f3a313a303b2f3a313a80983a303b809700",
};
}