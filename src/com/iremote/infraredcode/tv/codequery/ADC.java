package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class ADC extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "ADC";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µçÊÓ °¢¶û´Ä(ADC) 1
"00e02f00340b81331181d11281d01281d21181d11381341281331281331281d2128132118133118c431281331281d01281d21181d11381d11181341181321181331281d0128133128134120000000000000000000000000000000000000000000000000000000000000000000000",
};
}