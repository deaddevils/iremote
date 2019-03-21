package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Packard extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Packard";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µçÊÓ ÅÁ¿¨µÂ-±´¶û(Packard) 1
"00a065003780a333201920182233573321352018211920192019201821182018221820193c34201820183d35201821193c3420183d3421192019201920183c19203420182190c280aa33201820182034583421342019201820182018201920192119201920183c34201920193d00",
};
}