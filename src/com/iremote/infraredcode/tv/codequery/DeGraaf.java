package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class DeGraaf extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "DeGraaf";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 德格拉夫(De Graaf) 1
"00e04700338232811028202a1f291f296528642865281f2920286628652966291f2a1f291f29652864281f2920286628652965281f291f281f29652965281f281f291f296528662865298993823b80872a0000000000000000000000000000000000000000000000000000000000",
//电视 德格拉夫(De Graaf) 2
"00e047003382338110281f281f291f2966286628652820282028662965296528202820281f28642865291f291f2965286529642820281f291f29652965281f281f291f296628662865298994823b8086280000000000000000000000000000000000000000000000000000000000",
};
}