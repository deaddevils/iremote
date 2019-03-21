package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Action extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Action";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 作用(Action) 1
"00a065003480d4682116204e20162015201620162016201620162016211620162016204e211620172116201621162116211720172016214e2016201620162015201621162017211620482215214920492048204821152115214820152048204920482149201520492091ba80db00",
};
}