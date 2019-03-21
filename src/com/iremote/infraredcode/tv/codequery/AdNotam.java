package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class AdNotam extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "AdNotam";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 广告(Ad Notam) 1
"00e04700358233810f292028202865291f291f291f2820282028662965291f296629652a65296528642820281f282028662820281f281f281f286529652965281f29662a6529652865288996823a8087280000000000000000000000000000000000000000000000000000000000",
};
}