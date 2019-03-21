package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class FoshanTsinghuaTongfang extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "FoshanTsinghuaTongfang";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//������ ��ɽ�廪ͬ��(Foshan Tsinghua Tongfang) 1
"00e047003582338110281f2920291f2967271f281f2920281f29652967276827202866296529652868281f2967271f29652820281f282028202866271f286528202965286427662665288998823b8086280000000000000000000000000000000000000000000000000000000000",
//������ ��ɽ�廪ͬ��(Foshan Tsinghua Tongfang) 2
"00e0470035823381102820291f281f2865291f2920291f291f29652866266528202965286529662965281f2865292028672720281f281f281f296626202a65291f2865296626662866288997823b8086290000000000000000000000000000000000000000000000000000000000",
};
}