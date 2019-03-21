package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class AWa extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "AWa";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” ∞¢≤®(AWa) 1
"00e04b003381ee80f2251b23582358231b241c2558245823582380f1231c235823582358231c231c231c231b2385c781f680f1241d23582358231b241b2459235923592380f2241c245923582458241b241c241c241c240000000000000000000000000000000000000000000000",
};
}