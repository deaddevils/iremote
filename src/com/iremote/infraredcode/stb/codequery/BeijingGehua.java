package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class BeijingGehua extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "BeijingGehua";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 北京 歌华(Beijing Gehua) 1
"00a065003480a333201821192034202e5318201821333c342018211820182018211820182118201920183c352018201920193c19203420193d1821182018213320183c1821912a80aa34201821192134212e5319201821333c342018211922182018211821182019201920183c00",
};
}