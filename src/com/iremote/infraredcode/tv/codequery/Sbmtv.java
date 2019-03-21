package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Sbmtv extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Sbmtv";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//���� LT360W(sbmtv) 1
"00e02b00366f6777323d323e323e313d333e323e663d3277323e9475766777323e323d333d323e323d323e673e3278323d00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
//���� LT360W(sbmtv) 2
"00e02f00376f313e333d323d323e323d323e323e313d683e3277323e947577333d323e323d313d333e323d323d333d673d3276313d000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
//���� LT360W(sbmtv) 3
"00e04700358232811029202866291f291f291f291f292028202866291f296529652865296628202866281f281f281f2820282028202866281f28662865296529652865296628202965288995823a8086280000000000000000000000000000000000000000000000000000000000",
//���� LT360W(sbmtv) 4
"00e05b00338234810f291e2963291e28632962281f281e281f2963291e291e281e291f2864281e2863291e281e286428632963291e28632962286428632a1e2863281f281f2863291f291e281e2963281e28642864281e2963286428632985bd823b811029000000000000000000",
};
}