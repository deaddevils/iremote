package com.iremote.thirdpart.action;

import org.apache.commons.codec.binary.Base64;

public class DecodeBase64Test {

	public static void main(String[] args) {
		String fingerprint = "uUtDSAs8oehkTFfsXDNErhgJXK0UJtgso5bBQLfv29vsoZ83QKsuIcscoJYxRLTvXecsoukJQLQX2IzkoZxLQYyuVtudkJ+HXFcWXczVkAGEXErWpWv1np5CWamp3k+a05mwWKmvqWN60YZWQUmWKWRU0hmxR6mpqd9k0gLwX6mpqesn0+15WLZp05OP0xUUXFbVKV9c0xXRQKmQqXPU0BFxXEqS3z0BLAZtJsvLy0TFwxedIEXT0MwNijBKdlcJJ9T4eWZnZGViY2Bhbm9sbWpraGkWFxQVEhMQER4fHB0aGxgZBgcEBQIDAAEODwwNCgsICTY3NDUyMzAxPj88PTo7AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA=";
		byte[] content = Base64.decodeBase64(fingerprint);

			
		StringBuffer sb = new StringBuffer();
		for ( int i = 0 ; i < content.length ; i ++ )
		{
			String t = Integer.toHexString(content[i] & 0xff);
			if ( t.length() == 1 )
				sb.append("0");
			sb.append(t);
		}
		System.out.println(sb.toString());
		System.out.println(sb.length());

	}
}
