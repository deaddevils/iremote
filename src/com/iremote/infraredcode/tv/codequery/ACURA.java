package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class ACURA extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "ACURA";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 极品(ACURA) 1
"00e04b003381f080f3231b2358251b251b251b241b241b231b2380f2251b245823582358241c241c241c251b2485c681f680f2231b2458241c241c241c241b241b241b2480f2231b235823592558231b231b231b231b230000000000000000000000000000000000000000000000",
//电视 极品(ACURA) 2
"00e04b003381ef80f2231c2558241b241b231b231b231b231b2380f2241b235823582359251b241b231b231b2385c781f680f2231b2458241c231d231c231c231b231b2380f2241c235923582358241c231c231d231b230000000000000000000000000000000000000000000000",
};
}