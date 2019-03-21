package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class ChungHsin extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "ChungHsin";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//电视 中兴(Chung Hsin) 1
"00e043003416781d781d781d341d341d341c781d341d341d781d341d331d341e341d351d341d86b31e781d781d781d341e341d331d781d341d351d791d331c331d341d341d331d341e00000000000000000000000000000000000000000000000000000000000000000000000000",
};
}