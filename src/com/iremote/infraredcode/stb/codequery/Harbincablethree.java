package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Harbincablethree extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Harbincablethree";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 哈尔滨有线三(Harbin cable three) 1
"00e04700338234810f281f291f2866291f291f291f291f291f286529662a1f296529652864286528652965281f29662a652820291f281f281f281f2a652820291f2965286529652865298995823b8086290000000000000000000000000000000000000000000000000000000000",
};
}