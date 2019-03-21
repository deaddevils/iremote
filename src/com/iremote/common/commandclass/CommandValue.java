package com.iremote.common.commandclass;

public class CommandValue {

	private int commandclass;
	private int command ;
	private byte[] cmd ;
	private int channelid ;
	private int type ;
	private int value;
	public int getCommandclass() {
		return commandclass;
	}
	public void setCommandclass(int commandclass) {
		this.commandclass = commandclass;
	}
	public int getCommand() {
		return command;
	}
	public void setCommand(int command) {
		this.command = command;
	}
	public byte[] getCmd() {
		return cmd;
	}
	public void setCmd(byte[] cmd) {
		this.cmd = cmd;
	}
	public int getChannelid() {
		return channelid;
	}
	public void setChannelid(int channelid) {
		this.channelid = channelid;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
}
