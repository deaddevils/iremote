package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Asthemusic extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Asthemusic";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//ª˙∂•∫– ¿÷ ”(As the music)-C21 1
"00e047003382348110286629652820291f29652865292028652820282028662865291f2a1f2965292028202820286628662966291f286529652965286628202820282028652920292029899b823c8086290000000000000000000000000000000000000000000000000000000000",
};
}