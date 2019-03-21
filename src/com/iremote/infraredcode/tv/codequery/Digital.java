package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Digital extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Digital";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µçÊÓ 3SÊý×Ö(Digital) 1
"00e033003c1c78257724811024811025772481102478248110247825811024782581112487cb257825782581102581102678258110267825811026782481112578248111250000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}