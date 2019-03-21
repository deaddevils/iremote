package com.iremote.vo;

public class Userinfo
{
	private String name;
	private String avatar;
	private String hometitle;
	private AddressVo addressvo;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getHometitle() {
		return hometitle;
	}

	public void setHometitle(String hometitle) {
		this.hometitle = hometitle;
	}

	public AddressVo getAddressvo() {
		return addressvo;
	}

	public void setAddressvo(AddressVo addressvo) {
		this.addressvo = addressvo;
	}
	
	
}
