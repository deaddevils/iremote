package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Sanya extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Sanya";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ ÈýÑÇ(Sanya) 1
"00e02b003731636d3038636c2f3831383039636c30382f383138947038636d3039636c30382f383139636c303830382f3800000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}