package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Electrohome extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Electrohome";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 Electrohome(Electrohome) 1
"00e045003382108100265d255d245d251c251c261d255e251c261d255e251c251c251c261d251d261c26855a255d255d255d251c251c251d265d251c251c255d251c251c251c261c251c261d25000000000000000000000000000000000000000000000000000000000000000000",
//电视 Electrohome(Electrohome) 2
"00e045003382108100265d265d255d251d251c251d255d261c261d255d251c261d251d251d251d261c258559265d265d255d251d261c251d255d261c261d255d251c261d241c261d251d261c25000000000000000000000000000000000000000000000000000000000000000000",
};
}