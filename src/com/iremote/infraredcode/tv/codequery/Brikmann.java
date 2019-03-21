package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Brikmann extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Brikmann";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//���� Brikmann(Brikmann) 1
"00e02d003c821b82211182211282221282211382211282201281551282211281551281551289941282041182211182211282211282211282221282211281551282211281541381551200000000000000000000000000000000000000000000000000000000000000000000000000",
//���� Brikmann(Brikmann) 2
"00e02f003c0b8205128156128221128221128220128221118221128155128221128155128154128994128204138156138221128221138222128221128221118154138222128155128155130000000000000000000000000000000000000000000000000000000000000000000000",
};
}