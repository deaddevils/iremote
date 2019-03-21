package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Shenzhenprintritehd extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "ShenzhenPrintRiteHD";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 深圳天威高清(Shenzhen Print-Rite HD) 1
"00e0550034816b7c27202742272127422621264126212842274126212641262128422720284227212720262128202720284126422742274380e580d92720282027422820274327202743272028422741264227422721272127202820270000000000000000000000000000000000",
//机顶盒 深圳天威高清(Shenzhen Print-Rite HD) 2
"00e0550033816a7c27212742282027412720264227202642274228202641272026422720264227212720282027212620274126412742274280e580d92720272127422820274228202743272027432741264127422620272026212620270000000000000000000000000000000000",
//机顶盒 深圳天威高清(Shenzhen Print-Rite HD) 3
"00e055003481697c26412742262126202742282027422820272126412621264127422720264126212620272027422742282026212641272080e680d92620272028422620274226202741272027412741272027202743274126202741260000000000000000000000000000000000",
//机顶盒 深圳天威高清(Shenzhen Print-Rite HD) 4
"00e055003481667c27422641272026202741262127432720282026412720264227422721274128202620262027422743272028202743282080e480d92721272026422720274227212742272127422841272027202742274127202741270000000000000000000000000000000000",
};
}