package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class SAKAISIO extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "SAKAISIO";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” æ∆æÆ(SAKAI SIO) 1
"00a06500340c6b182c182c182d182c182c186e186e182c186e182d182c182c186e182c188a6a186c182e182e192d182f196c172d182e186c182f186c186c186c182e186c198996186e192c182c182c182c182c186e196e192b186d182b182c182c186e192c198a6b186c192e1900",
};
}