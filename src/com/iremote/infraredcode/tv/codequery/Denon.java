package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Denon extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Denon";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” ÃÏ¡˙(Denon) 1
"00a0650034106d192b182b182b182c182d186e186d192b196d182b182c182c186e182b198a6a186b182f192e192e182e186b192d182f196c172d186c196d186b192d186d188995186e192c192b182c182c182c186e186e192c196e182c182c182c186e182c188a6a186d182e1700",
};
}