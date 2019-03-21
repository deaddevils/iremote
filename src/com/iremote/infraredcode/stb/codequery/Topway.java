package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Topway extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "TOPWAY";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 深圳天威视讯(TOPWAY) 1
"00e0550034816c7c28422743282026212641262128422720282026422721274126412721274226202720272026412741262126202742282080e580d92720272028422620274228202742282027432742262027202742274328202743270000000000000000000000000000000000",
};
}