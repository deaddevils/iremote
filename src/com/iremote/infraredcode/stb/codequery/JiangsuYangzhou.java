package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class JiangsuYangzhou extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "JiangsuYangzhou";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ ½­ËÕÑïÖÝ(Jiangsu Yangzhou) 1
"00e0470033823481102865291f291f292028202820282028202866291f29662965296629652965286728202820286529662965292028652965286628662820281f291f2966291f291f29899c823c8086280000000000000000000000000000000000000000000000000000000000",
};
}