package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Yoko extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Yoko";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 约科(Yoko) 1
"00e045003382108101255d255d251d251d251c241c251c251c255e255d255e251c265d251c251c251c25855c255e255e241c251c251c251c261d251d265d265d255d251c255d251d261c251d25000000000000000000000000000000000000000000000000000000000000000000",
//电视 约科(Yoko) 2
"00e045003382108102255e255d261c251d251c241d251d251c255d255f255e251c255d251c251c251c25855a245d255d251c251c251c251c251c251d265d255d255d251d265d251c251c251c26000000000000000000000000000000000000000000000000000000000000000000",
};
}