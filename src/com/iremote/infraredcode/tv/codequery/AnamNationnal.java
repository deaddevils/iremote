package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class AnamNationnal extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "AnamNationnal";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 安南地区(Anam Nationnal) 1
"00a065003480d4682116204f21172016201620152016201620162016201620162216204f201620152016201620162016201520162016204e2117201620162016201620162015201620482115214820482148214821162116214820152048204821492049201620492091b980db00",
};
}