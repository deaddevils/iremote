package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class KunmingYunguangnetwork extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "KunmingYunguangnetwork";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 昆明云广网络一(Kunming Yunguang network) 1
"00e04700358232811128202820291f281f2920291f291f292028652866286628652965296529662965291f281f28662866286529202a1f291f2965286629202820281f2865286628652a899b823b80862a0000000000000000000000000000000000000000000000000000000000",
};
}