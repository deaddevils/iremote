package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class BeijingGehuacableHD extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "BeijingGehuacableHD";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//������ �����軪���߸���(Beijing Gehua cable HD) 1
"00a065003480a334211820182134202e5333211921182118201821192019201a201820193c34211922182019201820183d3420193c17203421182018211821183d182133211920910780ab34201821182034202f54332019201821192018201820182119211820183d3420182100",
//������ �����軪���߸���(Beijing Gehua cable HD) 2
"00a065003480a334201821192034202e54342019201820182118211821182018201821183c342019201820183d333d3420183e182034201a2019201920183d182034211920910780ab35201920192034212d53352019201720192019201820182018211920183d35201920182000",
};
}