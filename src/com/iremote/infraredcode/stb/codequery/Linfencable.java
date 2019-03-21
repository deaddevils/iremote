package com.iremote.infraredcode.stb.codequery;

import com.iremote.infraredcode.tv.codequery.CodeQueryBase;

public class Linfencable extends CodeQueryBase {

		@Override
		public String getProductor() {
			return "Linfencable";
		}
		@Override
		public String[] getQueryCodeLiberay() {
			return querycode;
		}
		private static String[] querycode = new String[]
{
//������ �ٷ�����(Linfen cable) 1
"00e04b003480ba80b2255926592558258095255825582580962559255925582659255925809625582580952580952580ef26859080c180b3265925582558258096255826592580962559255826582559255925809526592580962580952680f02600000000000000000000000000",
//������ �ٷ�����(Linfen cable) 2
"00e04b003480ba80b3265925592559258095255825592680962559255826582558255925809526592580952580952680f026859080c180b3255925582558258095255925592580962558255825582658255826809525582680952580962580f02500000000000000000000000000",
};
}