package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class WuhanHuawei extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "WuhanHuawei";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//������ �人��Ϊ(Wuhan Huawei) 1
"00e047003382348110281f281f291f2965291f291f2865281f296528662866281f286628652965291f281f2865291f296628202820281f281f29652820286628202865286628652965288994823b8086290000000000000000000000000000000000000000000000000000000000",
//������ �人��Ϊ(Wuhan Huawei) 2
"00e047003582328110281f292028202866282028202864281f286529652965281f2a6529652865281e281f296528202865291f281f282028202865291f2965292029662a6529652866288995823b8086280000000000000000000000000000000000000000000000000000000000",
};
}