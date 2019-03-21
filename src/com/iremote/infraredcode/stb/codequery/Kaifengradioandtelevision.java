package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Kaifengradioandtelevision extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Kaifengradioandtelevision";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 开封广电(Kaifeng radio and television) 1
"00e04700338234811029202820281f28652920281f28202820286529652965292028652965296528652920296628202865281f2920292028202965281f286529202866286628652866298995823b8086280000000000000000000000000000000000000000000000000000000000",
//机顶盒 开封广电(Kaifeng radio and television) 2
"00e04700338233810f281f291f29202866291f291f291f291f296629652a65291f2865296528662865291f2866281f2865291f291f291f281f2865291f2a65291f2865296528662865298995823b8086280000000000000000000000000000000000000000000000000000000000",
};
}