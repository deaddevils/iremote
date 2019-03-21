package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class WenzhouZhongguangcable extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "WenzhouZhongguangcable";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 温州中广有线(Wenzhou Zhongguang cable) 1
"00e0470033823581102966281f281f282028202820291f281f281f2965296528202867286628652966291f2865291f291f2965281f282128202866281f28652a65291f29662866286528899c823b8086280000000000000000000000000000000000000000000000000000000000",
};
}