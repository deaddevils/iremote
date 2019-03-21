package com.iremote.action.chain;

import com.opensymphony.xwork2.Action;

public class ChainTestActionTwo {

	private int two = 2 ;
	
	public String execute()
	{
		return Action.SUCCESS;
	}

	public int getTwo() {
		return two;
	}

	public void setTwo(int two) {
		this.two = two;
	}

}
