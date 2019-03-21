package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Suzhouradioandtelevision extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Suzhouradioandtelevision";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 宿州广电(Suzhou radio and television) 1
"00e0370033823481112820281f286329202864281f286328202820281f291f29202863281f2864282028632963296329202964281f2863291f298bc2823c808629000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}