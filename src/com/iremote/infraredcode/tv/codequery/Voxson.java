package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Voxson extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Voxson";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” Voxson(Voxson) 1
"00e045003382108101255d255d251c251c251c251c261d251d265d255d255d261d255e251c261d251d26855a255d265d251c251d261c261d251d251c255d255e255d251c255d261d251d251c25000000000000000000000000000000000000000000000000000000000000000000",
};
}