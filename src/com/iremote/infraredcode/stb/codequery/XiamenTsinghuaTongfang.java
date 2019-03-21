package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class XiamenTsinghuaTongfang extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "XiamenTsinghuaTongfang";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//������ �����廪ͬ��(Xiamen Tsinghua Tongfang) 1
"00e04700338232810f29202820281f2965291f291f29202920286529652965281f2864286529652965281f2865291f2a652820281f281f281f29652820286628202865286628652965288994823a8085280000000000000000000000000000000000000000000000000000000000",
//������ �����廪ͬ��(Xiamen Tsinghua Tongfang) 2
"00e047003382348110281e281f29202866282028202820281f286428652965291f29662865296628662820286528202865291f291f291f292028662a1f2965291f2965286428652865298994823c8087290000000000000000000000000000000000000000000000000000000000",
};
}