package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class WithaFoshan extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "WithaFoshan";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//������ ��ɽͬ��һ(With a Foshan) 1
"00e047003382348110281f291f29202820291f291f281f29202865296529652865296628662865286628202820286528652866291f2920281f28652866291f291f291f286528662965298995823b80872a0000000000000000000000000000000000000000000000000000000000",
};
}