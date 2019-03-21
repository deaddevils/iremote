package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class CommercialSolutions extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "CommercialSolutions";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” (Commercial Solutions) 1
"00e05d002384713529352970283428702935286f28362834283428352935286f28702935286f28352970293528702885fb80fd80ed286f297029702870283628352971293628702936296f283629342835293528352a702870293629702835297028352870290000000000000000",
};
}