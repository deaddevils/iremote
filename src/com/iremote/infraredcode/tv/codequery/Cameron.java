package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Cameron extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Cameron";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//µçÊÓ ¿¨Ã·Â×(Cameron) 1
"00e047003382328110291f291f291f2865291f291f2a1f291f296528652965282028662865286628652965291f2865291f29202920291f291f281f2865291f29662866286528662865298995823b8086290000000000000000000000000000000000000000000000000000000000",
};
}