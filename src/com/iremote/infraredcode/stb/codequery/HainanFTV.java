package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class HainanFTV extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "HainanFTV";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 海南民视(Hainan FTV) 1
"00e04700338233811128202820281f281f291f2a1f296529202820282028662866291f2966291f281f29662966281f2820282028202820291f281f281f29652965286628662866296528899c823c8087280000000000000000000000000000000000000000000000000000000000",
};
}