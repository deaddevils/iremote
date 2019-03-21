package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class QinghaiXiningJiulian extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "QinghaiXiningJiulian";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 青海西宁九联(Qinghai  Xining Jiulian) 1
"00e0470033823481112820291f291f281f2920281f291f292028652866286628652a65296529662966281f28202866286628652a1f291f291f2966286628202820281f2965296528652a899b823b8086290000000000000000000000000000000000000000000000000000000000",
};
}