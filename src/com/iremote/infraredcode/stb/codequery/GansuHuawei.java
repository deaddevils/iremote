package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class GansuHuawei extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "GansuHuawei";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ ¸ÊËà»ªÎª(Gansu Huawei) 1
"00e04700338233810f286529202820281f291f291f2920281f281f286628652965296528652966286529652966291f291f291f291f291f292028202820286529652965286529662866288995823b8086290000000000000000000000000000000000000000000000000000000000",
};
}