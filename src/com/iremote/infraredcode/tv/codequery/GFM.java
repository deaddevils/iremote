package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class GFM extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "GFM";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 金融市场(GFM) 1
"00a052003480ce80d23a80983b303b2f3a80983a303b80973a2f3b303a303a303b303b80973a2f3a80973a80973a2f3a80973b303a80983a80973b80973b80973a80963a303a882280d680d23a80963a303a303a80963a303a80973a313a303a30392f3a303a80983a313a809700",
};
}