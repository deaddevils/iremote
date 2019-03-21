package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Gericom extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Gericom";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” Gericom(Gericom) 1
"00a06300338232811028652965281f281f281f291f292029202820291f2865286528662965286628662865281f291f2a1f2a6528202820282028202865286628652820286628652865288992823b810f28652865291f292028202820282028202820282028652866296528662800",
};
}