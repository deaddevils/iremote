package com.iremote.infraredcode.liberay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import com.iremote.infraredcode.ac.ACCodeLiberayVO;
import com.iremote.infraredcode.vo.CodeLiberayVo;
import com.iremote.infraredcode.vo.RemoteControllerCode;

public class ProductorCodeLiberay {

	protected CodeLiberayVo liberayvo;
	protected Map<String , Set<String>> combineProductor = new HashMap<String , Set<String> >();
	
	public ACCodeLiberayVO[] getProductorCodeLiberay(String productor)
	{
		Integer[] pi = getProductorLibery(productor);
		if ( pi == null )
			return null;
		
		List<ACCodeLiberayVO> codeliberys = new ArrayList<ACCodeLiberayVO>();  
		
		for ( int i = 0 ; i < pi.length ; i ++ )
		{
			int[] cli = liberayvo.getProductorindex()[pi[i]];
			for ( int j = 1 ; j < cli.length ; j ++  )
			{
				int[] cl = composeCodeLiberay(cli[j] , getCodeLiberay(cli[j])) ;
				if ( cl == null )
					continue;
				
				codeliberys.add(new ACCodeLiberayVO(getcodeid(cli[j]) , cl)); 
			}
		}
		
		return codeliberys.toArray(new ACCodeLiberayVO[0]);
	}
	
	public RemoteControllerCode[] getRemoteControllerCode(String productor)
	{
		if ( productor == null )
			return null ;
		Set<String> set = getCombineProductor(productor);
		if ( set == null )
		{
			RemoteControllerCode[] rcc = liberayvo.getProductorRemoteCode().get(productor);
			if ( rcc == null )
				rcc = liberayvo.getProductorRemoteCode().get(productor.toLowerCase());
			return rcc;
		}
		else 
		{
			for ( String str : set )
			{
				RemoteControllerCode[] rcc = liberayvo.getProductorRemoteCode().get(str);
				if ( rcc == null )
					rcc = liberayvo.getProductorRemoteCode().get(str.toLowerCase());
				if ( rcc != null )
					return rcc;	
			}
			return null ;
		}
	}
	
	public int[] getCodeLiberay(int index)
	{
		if ( index >= liberayvo.getCodeliberay().length )
			return null ;
		return liberayvo.getCodeliberay()[index];
	}
	
	protected int[] composeCodeLiberay(int index ,int[] src)
	{
		return null ;
	}
	
	protected String getcodeid(int index)
	{
		return null ;
	}
	
	private Integer[] getProductorLibery(String productor)
	{
		if ( productor == null )
			return null;

		Set<String> set = getCombineProductor(productor);
		
		Set<Integer> iset = new HashSet<Integer>();
		for ( String str : set )
		{
			Integer i = liberayvo.getProductorIndexMap().get(str);
			if ( i == null )
				i = liberayvo.getProductorIndexMap().get(str.toLowerCase());
			if ( i == null || iset.contains(i) )
				continue;
			iset.add(i);
		}
		
		return iset.toArray(new Integer[0]);
	}
	
	private Set<String> getCombineProductor(String productor)
	{
		Set<String> set = null ;
		
		if ( combineProductor != null )
			set = combineProductor.get(productor.toLowerCase());
		if ( combineProductor != null && set == null )
			set = combineProductor.get(productor);
		if ( set != null )
			return set;
		set = new HashSet<String>();
		set.add(productor);
		return set ;
	}

	public void setLiberayvo(CodeLiberayVo liberayvo) {
		this.liberayvo = liberayvo;
	}

	public void setCombineProductor(Map<String, Set<String>> combineProductor) {
		this.combineProductor = combineProductor;
	}
	
	public void regestCombileProductor(String p1 , String[] p2)
	{
		Set<String> set = new HashSet<String>() ;
		set.add(p1);
		for ( int i = 0 ; i < p2.length ; i ++ )
			set.add(p2[i]);
		
		combineProductor.put(p1.toLowerCase(), set);
	}
}
