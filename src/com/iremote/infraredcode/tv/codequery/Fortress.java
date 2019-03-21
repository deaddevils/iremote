package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Fortress extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Fortress";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 堡垒(Fortress) 1
"00a06500340d6e172b172b182b182b192b196d196d182b176d182b182b192b196d192b188a6b186c192f182e192e182e186c182e182e196b172e186d196b186c182e196c188994186e182c182c182c192d192d186e186e182c186e182c182d192d196f182c188a6a186c192e1800",
//电视 堡垒(Fortress) 2
"00a06500340e6d182c182d182c182c182c186e186e182c186f182c182c182c186e182c188a6a186d182d182e182e182f186c182e182e176c182e196b186c186c182e186b198995186d182c182c182d182c182c186e186d182b186e182d182c182c186d192b188a69196d182e1800",
};
}