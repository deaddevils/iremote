package com.iremote.infraredcode;

import com.alibaba.fastjson.JSON;
import com.iremote.infraredcode.liberay.ACProductorCodeLiberay;
@SuppressWarnings("unused")
public class DownloadproductorcodeliberayTest {

	public static void main(String arg[])
	{
//		ACProductorCodeLiberay lib = Downloadproductorcodeliberay.acliberay;
//		for ( int i = 0 ; i < lib.liberayvo.getCodeliberay().length ; i ++ )
//		{
//			if ( i % 10 == 0 )
//				System.out.println(i);
//			int[] cl = lib.liberayvo.getCodeliberay()[i];
//			StringBuffer sb = new StringBuffer();
//			sb.append("{");
//			for ( int j = 0 ; j < cl.length ; j ++ )
//			{
//				sb.append("0x");
//				String c = Integer.toHexString(cl[j]);
//				if ( c.length() == 1 )
//					sb.append("0");
//				sb.append(c);
//				sb.append(",");
//			}
//			sb.deleteCharAt(sb.length() - 1);
//			sb.append("}");
//			System.out.println(sb);
//		}
		
		
		Downloadproductorcodeliberay action = new Downloadproductorcodeliberay();
//		action.setDevicetype("tv");
//		action.setProductor("TCL");
//		action.setDevicetype("AC");
//		action.setProductor("Chigo");
		action.setDevicetype("AC");
		action.setProductor("Gree");
		action.execute();
//		
		System.out.println(JSON.toJSON(action));
	}
}
