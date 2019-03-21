package com.iremote.action.chain;

import com.opensymphony.xwork2.Action;

public class ChainTestActionSon extends ChainTestActionTwo{

	private int son = 3 ;
	
	public String execute()
	{
		return Action.SUCCESS;
	}

	public int getSon() {
		return son;
	}

	public void setSon(int son) {
		this.son = son;
	}



}
