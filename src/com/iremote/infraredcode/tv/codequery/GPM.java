package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class GPM extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "GPM";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 宏处理机(GPM) 1
"00a051003480cf80d23a303a80973a80973a2f3a80983b313a313b80973b303a80973a2f3b303a80963a303a303a80963a303a80983a80983b303b80973a303b80973a80973a882280d680d23a2f3a80973c80973a313b80973b303b303b80973a303a80963a303a303a80963a00",
};
}