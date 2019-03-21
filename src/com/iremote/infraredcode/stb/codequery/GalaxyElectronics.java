package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class GalaxyElectronics extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "GalaxyElectronics";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 银河电子(Galaxy Electronics) 1
"00e027003731636d646d63383038316d636c303830393039948038636d636c63382f38306d636d3038303830390000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}