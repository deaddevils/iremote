package com.iremote.action.share;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class DeleteShareActionTest {
	public static void main(String[] args) {
		List<Integer> dlass = new ArrayList<>();
		dlass.add(1);
		dlass.add(2);
		dlass.add(3);
		List<Integer> slist = new ArrayList<>();
		slist.add(1);
		slist.add(2);
		for(Iterator<Integer> it = dlass.iterator(); it.hasNext(); ){
			Integer next = (Integer)it.next();
			if(slist.contains(next)){
				it.remove();
			}
		}
		System.out.println(dlass.size());
	}
}
