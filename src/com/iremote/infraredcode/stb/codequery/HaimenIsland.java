package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class HaimenIsland extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "HaimenIsland";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//������ ����ͬ�� (Haimen Island) 1
"00e04700338234810f286528202820282028202820281f291f2965281f296529652865286528662a6528202820286628652966291f2965286529652965281f281f291f296628202820288993823a8085280000000000000000000000000000000000000000000000000000000000",
//������ ����ͬ�� (Haimen Island) 2
"00e04700338232810f2a65281f291f291f292028202820291f2865281f286528652965296528652965281f2920286529652865292029662866286528662a1f291f291f2965281f281f288993823c8087280000000000000000000000000000000000000000000000000000000000",
//������ ����ͬ�� (Haimen Island) 3
"007023001941194007143314101410140f140f14101410140f1432140f143214321432143214321432140f140f143214331432140f1432143214331432150f140f140f1432140f140f144449411d4043140000000000000000000000000000000000000000000000000000000000",
};
}