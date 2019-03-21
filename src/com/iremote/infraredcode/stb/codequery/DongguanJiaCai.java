package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class DongguanJiaCai extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "DongguanJiaCai";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ ¶«Ý¸¼Ñ²Ê(Dongguan Jia Cai) 1
"00e037003382328110281f291f296328202963281f2863281f2a1f2a1f291f291f296329202963291f29632963296428202863291f2964281f288bc6823b808728000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}