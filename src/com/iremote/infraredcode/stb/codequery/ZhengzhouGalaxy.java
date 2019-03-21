package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class ZhengzhouGalaxy extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "ZhengzhouGalaxy";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 郑州银河(Zhengzhou Galaxy) 1
"00e04700338234811028662820281f281f29202820282028202865291f296528662865286528662965291f291f286629652965281f2865296528662865281f28202820286628202820288995823b8087290000000000000000000000000000000000000000000000000000000000",
//机顶盒 郑州银河(Zhengzhou Galaxy) 2
"00e04700338232810f2865291f28202820282028202820282028652820286628662865286629652965291f281f29662a652965281f286529662866286528202820282028662820281f288997823a8086290000000000000000000000000000000000000000000000000000000000",
};
}