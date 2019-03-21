package com.iremote.infraredcode;

public class CodeLiberayHelper {
	
	public final static int[] tvsecurity = new int[]{7,38,31,84,63,83,14,32,76,28,32,3,36,85,90,14,57,92,16,23,85,63,54,82,26,
		83,59,27,49,8,29,86,57,96,47,85,78,74,34,95,86,24,143,71,58,42,64,68,55,20,51,90,111,23,42};
	public final static int[] acsecurity = new int[]{7,38,31,84,0,0,0,0,0,0,0,3,36,85,90,14,57,92,16,23,85,63,54,82,26};
	
	public static byte[] createAcCommandBase(int[] liberay)
	{
        byte[] command = new byte[liberay.length + 1];
        
		for (int i = 0; i < liberay.length; i++) {
			if (i < CodeLiberayHelper.acsecurity.length)
				command[i] = (byte) (liberay[i] ^ CodeLiberayHelper.acsecurity[i]);
			else
				command[i] = (byte) (liberay[i]);
		}

        return command ;
	}
	
	public static int[] composeACCodeLiberay(int index  ,int[] liberay )
	{
		int[] cl = new int[liberay.length + 12];
		cl[0] = 0x30;
		cl[1] = 0x01;
		cl[2] = (index >> 8) & 0xff ;
		cl[3] = index & 0xff;
		
		System.arraycopy(liberay, 0, cl, 11, liberay.length);
		cl[11] ++ ;
		cl[cl.length -1] = 0xff;
		
		for ( int j = 0 ; j < cl.length && j < acsecurity.length ; j ++ )
			cl[j] = cl[j] ^ acsecurity[j];
		
		return cl;
	}
	
	public static int[] composeTVCodeLiberay(int[] src )
	{
		if ( src == null )
			return null ;
		int[] cl = new int[src.length];
		System.arraycopy(src, 0, cl, 0, src.length);
		for(int i = 0 ; i < src.length && i < tvsecurity.length ; i ++ )
			cl[i] = cl[i] ^ tvsecurity[i];
		return cl;
	}
	
	public static int[] composeSTBCodeLiberay(int[] src )
	{
		if ( src == null )
			return null ;
		int[] cl = new int[src.length];
		System.arraycopy(src, 0, cl, 0, src.length);
		for(int i = 0 ; i < src.length && i < tvsecurity.length ; i ++ )
			cl[i] = cl[i] ^ tvsecurity[i];
		return cl;
	}
	
	public static int[] code2int(String code)
	{
		if ( code == null || code.length() < 2 )
			return null ;
		String[] s = code.substring(1,code.length() - 2).split(",");
		int[] ia = new int[s.length];
		
		for ( int i = 0 ; i < s.length ; i ++ )
			ia[i] = Integer.valueOf(s[i]);
		return ia;
	}
	
	public static int compare(int[] num1 , int[] num2)
	{
		int r = 0 ;
		for ( int i = 0 ; i < num1.length && i < num2.length ; i ++ )
			if ( num1[i] == num2[i] )
				r ++ ;
		return r ;
	}
	
	public static int[][] string2int(String[] str)
	{
		if ( str == null || str.length == 0 )
			return new int[0][0];
		int[][] r = new int[str.length][];
		for ( int i = 0 ; i < r.length ; i ++ )
			r[i] = string2int(str[i]);
		return r;
	}
	
	public static int[] string2int(String str)
	{
		if ( str == null || str.length() == 0 )
			return new int[0];
		int[] r = new int[str.length()/2];
		
		for ( int i = 0 ; i < r.length ; i ++ )
			r[i] = Integer.valueOf(str.substring(i*2, i*2+2), 16);
		
		return r;
	}
	
}
