package com.mt.minilauncher;

import java.io.Serializable;

public class ChannelObject implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String channelFile;
	
	public ChannelObject(String channelFile) {
		this.channelFile = channelFile;
	}

	@Override
	public String toString() {
		return channelFile;
	}
	
	
}
