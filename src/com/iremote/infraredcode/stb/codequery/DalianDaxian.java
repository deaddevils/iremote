package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class DalianDaxian extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "DalianDaxian";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//������ ��������(Dalian Daxian) 1
"00e0470035823481112866281f281f291f2a1f291f291f291f282028202820286628652a65296529662966281f2820282028202820291f281f2920296529652866286628662965296528899b823c8086280000000000000000000000000000000000000000000000000000000000",
//������ ��������(Dalian Daxian) 2
"00e0470033823381102a65281f281f291f29202820281f282029202820291f286529652965286628662865281f29202820291f291f29202820281f296628662966286628652a65296528899b823c8086280000000000000000000000000000000000000000000000000000000000",
};
}