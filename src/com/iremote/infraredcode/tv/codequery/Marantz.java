package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Marantz extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Marantz";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 马兰士(Marantz) 1
"00e03700338234810f28202863281f2863291f296228202863291f2963286428202863291f291f291f2863281f291f29202963281f29632963288bbd823a808629000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
};
}