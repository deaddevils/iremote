package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Liuzhou extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Liuzhou";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//ЛњЖЅКа Сјжн(Liuzhou) 1
"00e047003382328110291f291f291f291f281f281f291f29652965286528642866296529652865291f292028662820282028642820282028662865291f286628652a1f29652865281f288996823b8086280000000000000000000000000000000000000000000000000000000000",
};
}