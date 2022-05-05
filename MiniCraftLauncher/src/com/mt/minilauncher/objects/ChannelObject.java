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
	
	public ChannelObject(String channelFile) {
		Random rand = new Random();
		this.channelFile = channelFile;
		this.channelName = "Channel " + rand.nextInt(0, 256);
	}

	public ChannelObject(String channelFile, String channelName) {
		this.channelFile = channelFile;
		this.channelName = channelName;
	}
	@Override
	public String toString() {
		return channelName;
	}
	
	
}
