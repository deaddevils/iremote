package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class ACE extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "ACE";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µÁ ” Õı≈∆(ACE) 1
"00e047003382338111281f291f291f2920291f291f2965281f291f2a1f291f291f291f29202866291f291f2965281f28652a1f291f291f291f2965281f2a65291f2965286529652866288995823b8086290000000000000000000000000000000000000000000000000000000000",
};
}