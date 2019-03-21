package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Optoma extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Optoma";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µçÊÓ °ÂÍ¼Âë(Optoma) 1
"00e04700358232810f291f2965281f291f2a1f2a1f291f291f2965281f2965282028662865286628652965291f281f281f281f2a1f2a1f291f291f29652865296528652965296529662a8994823b8086290000000000000000000000000000000000000000000000000000000000",
};
}