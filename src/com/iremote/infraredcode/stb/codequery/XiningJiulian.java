package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class XiningJiulian extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "XiningJiulian";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ Î÷Äþ¾ÅÁª(Xining Jiulian) 1
"00e047003382348110281f291f291f291f2920281f28202820286628652965296529662865296528672820282028652965296529202820281f2966286629202820291f29652965296529899c823c8086280000000000000000000000000000000000000000000000000000000000",
};
}