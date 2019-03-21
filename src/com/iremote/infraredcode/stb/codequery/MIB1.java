package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class MIB1 extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "MIB1";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//������ �差(MIB) 1
"00e04700338233810f2865291f291f281f2920291f291f291f2965281f286529652865296529652865291f29202866286628652820286529652965286428202820282028662820281f288994823b8087290000000000000000000000000000000000000000000000000000000000",
//������ �差(MIB) 2
"00e0470033823281102864282028202820282028202820281f286428202865296529652865296628662820281f286628652965291f2865286428662965291f291f291f2865291f2a1f2a8993823b8086280000000000000000000000000000000000000000000000000000000000",
//������ �差(MIB) 3
"00e047003582328110291f281f281f2a1f2a1f291f2965281f281f291f2a652965281f2864281f28202866282028652820282028202865291f291f28652820286529652965281f2965288995823c8087290000000000000000000000000000000000000000000000000000000000",
//������ �差(MIB) 4
"00e047003582328110286628202820281f28202820281f28202865291f2965296629652866286528652920281f296529652865281f2865286529652965281f281f291f296628202820288994823c8086290000000000000000000000000000000000000000000000000000000000",
};
}