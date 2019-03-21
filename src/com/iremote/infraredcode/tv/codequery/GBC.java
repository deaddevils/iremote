package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class GBC extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "GBC";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 游戏机(GBC) 1
"00e03700331e5b2680d92580d92580d9265b255b25876b255c2580da2580d92580d9265b255b25876a265b2680d92580da2580d9265b255b248769255b2580d92580d92580d9255c255b250000000000000000000000000000000000000000000000000000000000000000000000",
};
}