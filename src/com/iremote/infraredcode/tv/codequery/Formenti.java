package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Formenti extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Formenti";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 福尔门蒂(Formenti) 1
"00a06500340e6d192b192b192b182b182c186e186e182b196d192b182b182b186e182d188a69186c182e192e182e192d186b182f182e186c192e186c186c186b182f186c198994186d192b192b192b182b182b186e176d192b196d192b192b182b186e172b198a69186c182e1900",
//电视 福尔门蒂(Formenti) 2
"00a0650034116d172b172b172b192b192b196d196d172b176d182b182b192b196d182c188a69186b182f192f182e192e186c182e192e186c182e186c186c186c182e196b198994186d192b192b192b182b172b176d186d192b196d182c182c192b186d182b188a6a186c192d1900",
};
}