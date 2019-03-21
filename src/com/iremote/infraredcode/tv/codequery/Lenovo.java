package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Lenovo extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Lenovo";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 联想(Lenovo) 1
"00e04700358231810f2865291f281f292029652a65291f281f281f286529652865291f291f286529662a652820291f281f281f281f291f29662820286628652966296528662866281f28899682398086280000000000000000000000000000000000000000000000000000000000",
};
}