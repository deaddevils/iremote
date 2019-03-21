package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Anlucable extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Anlucable";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 安陆有线(Anlu cable) 1
"00e02b003730636d636d64383138316c31396338313830393038944e38646c636d643830382f6c30386338303830392f3800000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}