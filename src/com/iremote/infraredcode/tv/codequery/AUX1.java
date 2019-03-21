package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class AUX1 extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "AUX1";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 奥克斯(AUX1) 1
"00e04700338233810f291f291f291f291f291f281f2920281f296529652865286528662965291f2965281f291f291f292028202820291f281f28652965286529652965286428652865298993823b8086290000000000000000000000000000000000000000000000000000000000",
};
}