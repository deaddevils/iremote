package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Gehua extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Gehua";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//机顶盒 北京歌华有线(Gehua) 1
"00a065003480a133201920182034202e53342019201920182118201821192019201821193c332118211820193d353c3420183c18213520182118201920193c192035201920910480aa34201720192035202e54342218201920182018211820182118211820183e33201920192000",
//机顶盒 北京歌华有线(Gehua) 2
"00a065003480a434201920192034202e53342019201820182118211820192018201820193c33211920182118201821193c3420193c18213420182018211920183d182133201820910680aa34201921192133202e54352017201920182119201820192018211820193c3321192000",
//机顶盒 北京歌华有线(Gehua) 3
"00a065003480a334201821182033212f5533201821192018201920182119201a201720193c3520182118211920011e343d3320183d19203420192119201920183c182135201821910580aa34201820182133202e5434201820182118201820192018211a201820183d3520182100",
};
}