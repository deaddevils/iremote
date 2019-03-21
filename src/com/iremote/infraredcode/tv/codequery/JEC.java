package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class JEC extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "JEC";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 国会(JEC) 1
"00e0470035823281102820281e281e28202820282028662820286529662965286628652965292029652920286628202820286528202820281f2865291f29652864281f296528652965298993823b8086280000000000000000000000000000000000000000000000000000000000",
};
}