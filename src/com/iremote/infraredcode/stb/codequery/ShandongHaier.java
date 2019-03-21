package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class ShandongHaier extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "ShandongHaier";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 山东海尔(Shandong Haier) 1
"00e047003382358110291f29202865281f29202820291f291f2865296529202865286628662965286628652965291f29652820282028202820281f281f2965291f296528662966286629899b823b8087290000000000000000000000000000000000000000000000000000000000",
};
}