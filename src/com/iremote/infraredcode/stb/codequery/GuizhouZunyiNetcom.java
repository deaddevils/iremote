package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class GuizhouZunyiNetcom extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "GuizhouZunyiNetcom";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 贵州遵义金网通(Guizhou Zunyi Netcom) 1
"00e047003382348110281f2920281f291f292028202820291f2966296528652965296528662966286629202866281f2865291f291f291f281f286728202866281f296529652966296529899b823c8087280000000000000000000000000000000000000000000000000000000000",
};
}