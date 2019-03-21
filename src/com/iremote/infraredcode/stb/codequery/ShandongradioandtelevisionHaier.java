package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class ShandongradioandtelevisionHaier extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "ShandongradioandtelevisionHaier";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 山东广电海尔(Shandong radio and television Haier) 1
"00e047003382358110291f29202865282029202820291f291f2865286529202865286628662865296629652965291f29652820282028202820281f281f2865291f296528662966286528899c823b8086280000000000000000000000000000000000000000000000000000000000",
};
}