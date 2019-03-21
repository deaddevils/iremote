package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class AIDisplay extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "AIDisplay";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” AIDisplay(AIDisplay) 1
"00a0630033823481102820282028202820281f281f2966282028662865286629652965286529652866282028202820281f291f29202820282029652865286628652965286528652966288996823b810f291f281f291f291f291f291f2866291f2965296528652965286529652900",
};
}