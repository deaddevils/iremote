package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Yunnandigital extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Yunnandigital";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ ÔÆÄÏÊý×Ö(Yunnan digital) 1
"00e02b003731646c636d63393039306c3038643830382f383139944d38636e636d63382f38316d3039623831393038303700000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}