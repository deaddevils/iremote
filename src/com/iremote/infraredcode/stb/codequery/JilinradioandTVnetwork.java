package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class JilinradioandTVnetwork extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "JilinradioandTVnetwork";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 吉林广电网络(Jilin radio and TV network) 1
"00e0470033823581102965291f2820282028202820291f281f2865291f296528662866286528652866281f281f2a652965286628202866286529662965281f2a1f291f29652820282028899b823b8088280000000000000000000000000000000000000000000000000000000000",
//机顶盒 吉林广电网络(Jilin radio and TV network) 2
"00e04700338233811029652820282028202820281f291f2a1f296529202866286628652866286628652a1f291f29652866296628202865286628652965291f291f292028662820282028899c823b8087280000000000000000000000000000000000000000000000000000000000",
};
}