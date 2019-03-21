package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Hefeicable extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Hefeicable";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 合肥有线(Hefei cable) 1
"00e037003382348110291f291f281f2863291f291f29642820291f281f281f281f296329202864282028642863286328202864291f2963291f298bc0823b808728000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}