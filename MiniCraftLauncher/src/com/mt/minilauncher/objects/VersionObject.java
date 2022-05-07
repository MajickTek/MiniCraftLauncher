package com.mt.minilauncher.objects;

import java.io.Serializable;

import com.mt.minilauncher.LauncherWindow;
import com.mt.minilauncher.launchwrap.LauncherWrapper;
import com.mt.minilauncher.launchwrap.VanillaWrap;

public class VersionObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String url;
	public String version;
	public boolean isDownloaded;
	
	public boolean canEdit;
	
	public boolean isRunning;
	
	public LauncherWrapper launcherWrapper;
	
	public VersionObject(String url, String version) {
		this.url = url;
		this.version = version;
		this.isDownloaded = false;
		this.canEdit = false;
		this.isRunning = false;
		launcherWrapper = new LauncherWrapper(new VanillaWrap(), LauncherWindow.instance);
	}
	
	public VersionObject(String url, String version, boolean canEdit) {
		this.url = url;
		this.version = version;
		this.isDownloaded = false;
		this.canEdit = canEdit;
		this.isRunning = false;
		launcherWrapper = new LauncherWrapper(new VanillaWrap(), LauncherWindow.instance);
	}
	
	public VersionObject(String url, String version, boolean canEdit, boolean isRunning) {
		this.url = url;
		this.version = version;
		this.isDownloaded = false;
		this.canEdit = canEdit;
		this.isRunning = isRunning;
		launcherWrapper = new LauncherWrapper(new VanillaWrap(), LauncherWindow.instance);
	}
	
	public VersionObject() {
		this.url = "UNKNOWN";
		this.version = "UNKNOWN";
		this.isDownloaded = false;
		this.canEdit = true;
		this.isRunning = false;
		launcherWrapper = new LauncherWrapper(new VanillaWrap(), LauncherWindow.instance);
	}

	public String getURL() {
		return url;
	}
	
	public String getVersion() {
		return version;
	}
	
	public String getDownloadStatus() {
		return isDownloaded ? "Ready to launch!" : "Not yet downloaded.";
	}
	
	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

	public LauncherWrapper getLauncherWrapper() {
		return launcherWrapper;
	}

	public void setLauncherWrapper(LauncherWrapper launcherWrapper) {
		this.launcherWrapper = launcherWrapper;
	}

	@Override
	public String toString() {
		return String.format("%s [%s]", version, getDownloadStatus());
	}
	
	public String toVerboseString() {
		return "VersionObject [url=" + url + ", version=" + version + ", isDownloaded=" + isDownloaded + ", canEdit=" + canEdit + "]";
	}
}
