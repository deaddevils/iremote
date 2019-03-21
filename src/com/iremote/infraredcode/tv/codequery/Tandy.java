package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Tandy extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Tandy";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” øµµœ(Tandy) 1
"00a06500340f6f192d182c182c182c182c186e186e192d196f182c182c182c186e182c188a6a186c182f182e192e182e186b182d182f196c172d186c196d186b172d186d188995186e182c182c182c182b182c186f186e182c186e182c182b182c186e182d188a6a186c182e1900",
};
}