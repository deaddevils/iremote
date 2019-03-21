package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Suihuacable extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Suihuacable";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 绥化有线(Suihua cable) 1
"00e04700338234811029202820281f281f2920282028652821282028202866286528202866282028202865292028662820281f281f29652820291f2965281f296529652865291f296528899882398087280000000000000000000000000000000000000000000000000000000000",
};
}