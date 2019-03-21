package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Radix extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Radix";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 雷迪克斯(Radix) 1
"00e0470033823481102920286628652866281f29652965281f28652920291f291f2965281f291f2965282028202866281f2864281f2820282028652866281f2865291f296528652965288994823a8087280000000000000000000000000000000000000000000000000000000000",
};
}