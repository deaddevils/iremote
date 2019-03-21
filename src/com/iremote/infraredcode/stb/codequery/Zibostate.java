package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Zibostate extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Zibostate";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//»ú¶¥ºÐ ×Í²©Í¬ÖÝ(Zibo state) 1
"00e04700338234811028202820281f29202920291f291f281f2866296628652866286628652a652965291f281f2966286528662820281f281f2965296529202820281f28662866296629899b823b8087280000000000000000000000000000000000000000000000000000000000",
};
}