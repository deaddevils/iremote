package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Nikkai extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Nikkai";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 日本开闭器工业公司(Nikkai) 1
"00e04700338233810f291f281f281f291f2a1f291f2965291f286528642866296529652865281f2865281f2965291f291f2965281f291f292029662820286628652820286529652965288994823c8086280000000000000000000000000000000000000000000000000000000000",
};
}