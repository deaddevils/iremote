package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Foxconn extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Foxconn";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 富士康(Foxconn) 1
"00a0650034116e182c182b182c182d182c186e186d192b186d182c172b182c186d192b198a68186c182e182e182d182f196c182d182e186c192e196b186c196d182e176b178994186f182c182c182c182c182c186e186f182c186d192b192b182b186e172b198a69186d192e1700",
};
}