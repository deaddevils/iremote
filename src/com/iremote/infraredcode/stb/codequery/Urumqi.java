package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Urumqi extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Urumqi";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//������ ��³ľ��(Urumqi) 1
"00e047003382338110291f291f29652820282028202866281f28652965291f296629662820281f286528662865291f2965291f292028202820282028202865291f2a6529652866296628899b823c8087280000000000000000000000000000000000000000000000000000000000",
//������ ��³ľ��(Urumqi) 2
"00e0470035823381112920282028662820281f291f2966291f296528652920286528662820281f28652a6529652920286529202820281f281f2920291f29652920296529652866286628899b823b8087280000000000000000000000000000000000000000000000000000000000",
//������ ��³ľ��(Urumqi) 3
"00e04700338233811028202820281f2965291f291f2865291f2965296528672820286629652965281f2920286529202865282128202820291f28652a1f2965291f286628662865286528899b823b8086280000000000000000000000000000000000000000000000000000000000",
};
}