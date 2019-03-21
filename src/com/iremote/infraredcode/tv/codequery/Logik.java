package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Logik extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Logik";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 搜集(Logik) 1
"00e04700338234810f281f291f282028202865291f291f291f2965281f29652866282028652866286529652965286428662865291f291f291f282028202820281f291f296529652865298995823b8086280000000000000000000000000000000000000000000000000000000000",
//电视 搜集(Logik) 2
"00e0470033823281102820281f282028202865291f282028202865291f29652865281f28642866296529652864286628652965291f281f281f281f291f291f29202820286628652866288993823c8086280000000000000000000000000000000000000000000000000000000000",
};
}