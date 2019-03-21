package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Haikou extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Haikou";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ º£¿Ú(Haikou) 1
"00e02b003731636d2f38636c2f3730383138636d303830382f38946e38636c2f37636c30382f393038636d30383039303900000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}