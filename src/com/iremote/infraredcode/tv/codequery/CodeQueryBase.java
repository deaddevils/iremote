package com.iremote.infraredcode.tv.codequery;

import com.iremote.infraredcode.CodeLiberayHelper;

public abstract class CodeQueryBase {

	protected int[][] querystring = null ;
	
	public abstract String getProductor();
	public abstract String[] getQueryCodeLiberay();
	
	public void initQueryString()
	{
		this.querystring = CodeLiberayHelper.string2int(getQueryCodeLiberay());
	}

	public int queryCodeLiberay(String code)
	{
		if ( querystring == null )
			return -1;
		int[] ic = CodeLiberayHelper.code2int(code);
		int max = 0 ;
		int sequence = -1 ;
		for ( int i = 0 ; i < querystring.length ; i ++ )
		{
			int m = CodeLiberayHelper.compare(ic , querystring[i]);
			if ( m <= max )
				continue;
			max = m ;
			sequence = i ;
		}
		
		return sequence ;
	}

}
