package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class GehuaSTB extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "GehuaSTB";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//������ �軪���������(Gehua STB) 1
"00e04700338233810f291f291f291f281f2920281f291f291f29652865286528662965296528652964281f28202820282028202820281f282028652965296528652964286629652965288993823a8086290000000000000000000000000000000000000000000000000000000000",
//������ �軪���������(Gehua STB) 2
"00e04700338233810f2865291f291f281f281f281f291f2a1f2a652820286628652865296428652965291f281f286529642866291f2965286628652966291f291f292028672720281f298991823b8087290000000000000000000000000000000000000000000000000000000000",
//������ �軪���������(Gehua STB) 3
"00e047003382348110286529202820281f281f28202820281f2865291f296528652965286529652965281f281f296528662866281f28662865296628662820281f281f2865281f2920288993823a8086290000000000000000000000000000000000000000000000000000000000",
//������ �軪���������(Gehua STB) 4
"00a0650034809e35201920182034202e54342018201821192018201920182019201920193c3420192018201821192019201821183d182034201820001d1920192018203421183d1820911980ab34201820182033202e54342018201820192019201820192018221820193c342200",
//������ �軪���������(Gehua STB) 5
"00a065003480a334211921182034212e53342118201920182018201822182019201821193c332018211920183d34201820183d182133201820001d1920192018203420183d1821911980ab34211820192034212d53352019201820182018211820192019211920193c3422182000",
//������ �軪���������(Gehua STB) 6
"00e04b003480b880b22558255825582558255824582580942558265825582558265825809525582580952680952580ee25858a80c180b32559255825582658255826582580952558255825592558255925809525582580952580952580ef25000000000000000000000000000000",
//������ �軪���������(Gehua STB) 7
"00a065003480a335201920182033202e53332018211820182119201821192019201820183d3420182119211820182119221820193c19203420193d1821182017203421183c1821911980aa34201720192035202e5433201821192018201920182018211a201820183d3520182000",
//������ �軪���������(Gehua STB) 8
"00e04700338233810f281f282028202866291f291f291f291f286528642866291f2965296528642866281f2865291f296528202820281f28202866281f2965291f2965286428662965298992823b80872a0000000000000000000000000000000000000000000000000000000000",
//������ �軪���������(Gehua STB) 9
"00e04700338233810f291f291f281f2865291f292028202820286628652966291f296528662865296528652920292028202820281f281f281f291f2964286529652965286428662865298993823b8086280000000000000000000000000000000000000000000000000000000000",
};
}