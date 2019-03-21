package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class WafangdianCityradioandtelevision extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "WafangdianCityradioandtelevision";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 瓦房店广电(Wafangdian City radio and television) 1
"00e04700338232811028652820291f281f2920281f291f29202820286629662965286529652965286529202865282028202866281f281f2a1f2965291f28662866282028652966296528899b823c8086290000000000000000000000000000000000000000000000000000000000",
//机顶盒 瓦房店广电(Wafangdian City radio and television) 2
"00e04700338234811028652820291f291f29202820281f29202820286629662965286529652965286629202865281f29202866281f281f291f2865292028652866282028652965296528899b823c8086290000000000000000000000000000000000000000000000000000000000",
};
}