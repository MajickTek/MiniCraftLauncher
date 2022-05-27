package com.mt.minilauncher.objects;

import java.io.Serializable;
import com.mt.minilauncher.LauncherWindow;

public class ChannelObject implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String channelFile, channelName, target, liveUsername, liveRepoName;
	
	
	LauncherWindow window;
	boolean isLive; // use dynamically-generated XML
	
	
	public ChannelObject() {
		this.channelFile = "";
		this.channelName = "";
		this.target = "";
		this.liveUsername = "";
		this.liveRepoName = "";
		this.window = null;
		this.isLive = false;
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

	
	public LauncherWindow getWindow() {
		return window;
	}

	public void setWindow(LauncherWindow window) {
		this.window = window;
	}

	public String getLiveUsername() {
		return liveUsername;
	}

	public void setLiveUsername(String liveUsername) {
		this.liveUsername = liveUsername;
	}

	public String getLiveRepoName() {
		return liveRepoName;
	}

	public void setLiveRepoName(String liveRepoName) {
		this.liveRepoName = liveRepoName;
	}

	@Override
	public String toString() {
		return channelName;
	}
	
	
}
