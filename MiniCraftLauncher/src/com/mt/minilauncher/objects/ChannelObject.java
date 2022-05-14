package com.mt.minilauncher.objects;

import java.io.Serializable;
import java.util.Random;

public class ChannelObject implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String channelFile;
	public String channelName;
	public String target;
	boolean isLive; // use dynamically-generated XML
	
	public ChannelObject(String channelFile) {
		Random rand = new Random();
		this.channelFile = channelFile;
		this.channelName = "Channel " + rand.nextInt(0, 256);
		this.target = "index" + rand.nextInt(0, 256) + ".xml";
		isLive = false;
	}

	public ChannelObject(String channelFile, String channelName) {
		Random rand = new Random();
		this.channelFile = channelFile;
		this.channelName = channelName;
		this.target = "index" + rand.nextInt(0, 256) + ".xml";
		isLive = false;
	}
	
	public ChannelObject(String channelFile, String channelName, String target) {
		this.channelFile = channelFile;
		this.channelName = channelName;
		this.target = target;
		isLive = false;
	}
	
	
	public String getChannelFile() {
		return channelFile;
	}

	public void setChannelFile(String channelFile) {
		this.channelFile = channelFile;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public boolean isLive() {
		return isLive;
	}

	public void setLive(boolean isLive) {
		this.isLive = isLive;
	}

	@Override
	public String toString() {
		return channelName;
	}
	
	
}
