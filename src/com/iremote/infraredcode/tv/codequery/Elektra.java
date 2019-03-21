package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Elektra extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Elektra";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” ∞¨¿ˆø®(Elektra) 1
"00e04b003381ef80f1241c241c2459241b2458251b251b251b2480f22358231b2358241b2458241c241c231b2385c581f580f2231c231d2359231b2458251b251b251b2580f22357231b2358231b2358241b241b241b230000000000000000000000000000000000000000000000",
};
}