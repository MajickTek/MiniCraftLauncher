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
	
	public ChannelObject(String channelFile) {
		Random rand = new Random();
		this.channelFile = channelFile;
		this.channelName = "Channel " + rand.nextInt(0, 256);
		this.target = "index" + rand.nextInt(0, 256) + ".xml";
	}

	public ChannelObject(String channelFile, String channelName) {
		Random rand = new Random();
		this.channelFile = channelFile;
		this.channelName = channelName;
		this.target = "index" + rand.nextInt(0, 256) + ".xml";
	}
	
	public ChannelObject(String channelFile, String channelName, String target) {
		this.channelFile = channelFile;
		this.channelName = channelName;
		this.target = target;
	}
	@Override
	public String toString() {
		return channelName;
	}
	
	
}
