package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class PennStateRadioandtelevision extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "PennStateRadioandtelevision";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 宾州广电(Penn State Radio and television) 1
"00e0550034816b7e26422741262027212742262126422720282026422721274226412820284227212720272028422742282027212641272080e680d92620262027432720274327202742272027422742272026202642274126212641270000000000000000000000000000000000",
};
}