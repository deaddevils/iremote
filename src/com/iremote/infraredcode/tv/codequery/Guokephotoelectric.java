package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Guokephotoelectric extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Guokephotoelectric";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 国科光电(Guoke photoelectric) 1
"00e04b003381ef80f3251b241b2358231b231b231b235824592480f22359231c2359231b231b231b231b231c2385c781f680f2241b241b2358241b241b241c245924582480f22358231b2359251b251b241b231b231b230000000000000000000000000000000000000000000000",
//电视 国科光电(Guoke photoelectric) 2
"00e04b003381f080f2251b241b2358231b241b241b245825582380f22458241b2358231b231b241b241b251b2585c681f680f2231b231b2458241b241c241c245924582380f22358241b2558251b241b241b231b231b230000000000000000000000000000000000000000000000",
};
}