package com.iremote.vo.merge;

import java.util.ArrayList;
import java.util.Comparator;

import org.apache.commons.lang3.StringUtils;

import com.iremote.domain.Associationscene;
import com.iremote.domain.Command;
import com.iremote.domain.Scene;

public class SceneComparator implements Comparator<Scene> , IMerge<Scene>
{
	@Override
	public int compare(Scene o1, Scene o2)
	{
		if ( o1 != null && o2 != null && StringUtils.isNotBlank(o1.getSceneid()) )
			if ( StringUtils.equals(o1.getSceneid(), o2.getSceneid()))
				return 0 ;
		return 1;
	}

	@Override
	public void merge(Scene desc, Scene src)
	{
		if ( desc == null || src == null )
			return ;
		desc.setIcon(src.getIcon());
		desc.setName(src.getName());
		
		if ( desc.getCommandlist() != null )
		{
			if ( desc.getAssociationscenelist() != null )
				for ( Associationscene as : desc.getAssociationscenelist())
					if ( as.getCommandlist() != null )
						as.getCommandlist().clear();
			desc.getCommandlist().clear();
		}
		else 
			desc.setCommandlist(new ArrayList<Command>());
		
		if ( src.getCommandlist() != null )
			desc.getCommandlist().addAll(src.getCommandlist());
	}



}
