package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Dyon extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Dyon";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µçÊÓ ºÉ×Ó(Dyon) 1
"00e0470033823481102820282028202820281f281f29202920286628652965286529652820286628652920282028652a6528662820281f281f28652865291f291f291f296628652966288993823b8087290000000000000000000000000000000000000000000000000000000000",
};
}