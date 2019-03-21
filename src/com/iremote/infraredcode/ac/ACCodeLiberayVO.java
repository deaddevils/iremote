package com.iremote.infraredcode.ac;

public class ACCodeLiberayVO 
{
	private String codeid;
	private int[] codelibery;
	
	public ACCodeLiberayVO(String codeid, int[] codelibery) {
		super();
		this.codeid = codeid;
		this.codelibery = codelibery;
	}
	public String getCodeid() {
		return codeid;
	}
	public void setCodeid(String codeid) {
		this.codeid = codeid;
	}
	public int[] getCodelibery() {
		return codelibery;
	}
	public void setCodelibery(int[] codelibery) {
		this.codelibery = codelibery;
	}
}
