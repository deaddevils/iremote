package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class AccuView extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "AccuView";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 高画质(AccuView) 1
"00e04700338232811129202820281f291f291f296628662820286428652965296529662a1f291f29652965286529202820291f291f281f281f281f2a1f296529652865296628662865288996823b8086280000000000000000000000000000000000000000000000000000000000",
};
}