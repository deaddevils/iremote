package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Lanzhou extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Lanzhou";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//������ ����(Lanzhou) 1
"00e04700338232811028652820282028202820281f28212920286628202865286628652a652965286528202820286529652965281f286529662866286528202820281f2965291f291f298994823b80872a0000000000000000000000000000000000000000000000000000000000",
//������ ����(Lanzhou) 2
"00e04700338234810f28652820281f281f291f291f2920281f2866281f296529652865296428652965291f291f29662865296628202866286528662965291f291f281f2865291f2920288995823c8087280000000000000000000000000000000000000000000000000000000000",
//������ ����(Lanzhou) 3
"00e04700358233810f2920291f2a65291f291f281f2865291f296628652920286529662a1f2a1f29652965286428202865291f291f2920281f282028202866291f2965296528642865288994823a8087280000000000000000000000000000000000000000000000000000000000",
//������ ����(Lanzhou) 4
"00e04700358232810f29652920291f291f291f281f281f281f29652820286529652965286428652865291f291f29652865296528202865296528652964281f281f281f2965291f291f288993823a8086280000000000000000000000000000000000000000000000000000000000",
//������ ����(Lanzhou) 5
"00e04700338234810f2965291f291f291f281f281f291f291f291f296528652864286529652965286529652866282028202820281f2820282028202820286628652966296528662866288994823c8087290000000000000000000000000000000000000000000000000000000000",
//������ ����(Lanzhou) 6
"00e0470033823381102866291f291f291f291f292029202820281f296529652866286528662a652866286628652920291f291f291f291f291f291f281f286529642866296529652864288994823b8086280000000000000000000000000000000000000000000000000000000000",
};
}