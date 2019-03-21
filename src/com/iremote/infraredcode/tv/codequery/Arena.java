package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Arena extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Arena";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” ∞¢¿Ôƒ…(Arena) 1
"00e02f003736313d3177323d323e313e323e323e313e673e3277313d95ca3d323d3176333d323d313d333e323d323d683e3276323e000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}