package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class HulunBuir extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "HulunBuir";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//������ ���ױ���(Hulun Buir) 1
"00e0470033823581102a1f292028202820282028202820281f29652965286528652965286728662866291f2966291f2865281f291f29202820286628202866281f286529652966286628899b823b8088280000000000000000000000000000000000000000000000000000000000",
//������ ���ױ���(Hulun Buir) 2
"00e0470033823581102a1f292028202820282028202820281f29652965286528652965286628662866291f2966291f2865281f291f29202820286628202866281f286529652966286529899b823b8087280000000000000000000000000000000000000000000000000000000000",
};
}