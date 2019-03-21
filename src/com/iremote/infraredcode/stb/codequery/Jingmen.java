package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Jingmen extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Jingmen";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//������ ����(Jingmen) 1
"00e04700338234811029662820281f281f29202820282028202866281f286628652965296528642865281f291f296529652865291f2966286628652866291f291f291f2965281f281f288993823b8087280000000000000000000000000000000000000000000000000000000000",
//������ ����(Jingmen) 2
"00e0470033823281102866291f291f2920281f281f282028202865291f296528652865296628652965281f281f2865296628662820286528662965286628202820281f28652820281f298994823a8086290000000000000000000000000000000000000000000000000000000000",
//������ ����(Jingmen) 3
"00e04700338233810f291f291f291f2920282028202865291f291f291f2865286428202865291f292028652920286629202920291f29652820281f29652820286628652866281f2965288994823b8086290000000000000000000000000000000000000000000000000000000000",
//������ ����(Jingmen) 4
"00e0470033823381102965291f291f291f281f281f281f2a1f2965291f296528642865286529652865281f291f2a652965286428202865296529652865291f291f2a1f2965281f281f288994823b8086280000000000000000000000000000000000000000000000000000000000",
};
}