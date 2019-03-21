package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Yangquan extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Yangquan";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//������ ��Ȫ(Yangquan) 1
"00e0470033823381102820282028652920291f291f2965281f29662865282128662866291f291f2a652965286529202865282028202820291f281f281f2965291f296528672866286529899c823b8086290000000000000000000000000000000000000000000000000000000000",
//������ ��Ȫ(Yangquan) 2
"00e047003582328110281f29202965281f2920281f2966282028662866281f28652a65291f292029662866286528202866281f281f281f2a1f291f291f29652820286628662965296528899b823c8086290000000000000000000000000000000000000000000000000000000000",
//������ ��Ȫ(Yangquan) 3
"00e0470034823381112765281f291f281f291f29202820281f29202920296528652965296528662866286629202820291f291f281f2920281f291f296628662866286529652965286528899b823c8087280000000000000000000000000000000000000000000000000000000000",
//������ ��Ȫ(Yangquan) 4
"00e047003382338110291f291f29202820281f281f2920282029652865296529652867286628662966291f281f2865291f29652820282028202866286529202a65291f29662966286528899d823b8086280000000000000000000000000000000000000000000000000000000000",
//������ ��Ȫ(Yangquan) 5
"00e04700338233811128662820281f291f2a1f291f291f292028662820286628652866286528652965292028202867286628652820296528652a6529652820281f28202866281f291f29899c823c80862a0000000000000000000000000000000000000000000000000000000000",
//������ ��Ȫ(Yangquan) 6
"00e04b003480b980b4255825582658258095265825582580962659255825592559255925809625582580952680962580f025859080c180b2255825582558268096255725582580952658265925592558255825809525582680962580952680f02500000000000000000000000000",
};
}