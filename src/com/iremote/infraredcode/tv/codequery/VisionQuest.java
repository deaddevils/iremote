package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class VisionQuest extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "VisionQuest";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” ª√œÎÃΩÀ˜(Vision Quest) 1
"00e047003582328110281f291f2a1f2965281f281f291f2a1f296529652865291f2a652965286428662866282028652820281f282028202820281f2865282028662865286528662965298995823a8085280000000000000000000000000000000000000000000000000000000000",
};
}