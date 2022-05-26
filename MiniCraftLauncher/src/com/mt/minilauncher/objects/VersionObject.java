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
	
	public String url, version, changelogURL, description;
	public boolean isDownloaded;
	
	public boolean canEdit;
	
	public boolean isRunning;

	
	
	public LauncherWrapper launcherWrapper;
	
	public VersionObject() {
		this.url = "";
		this.version = "";
		this.isDownloaded = false;
		this.canEdit = true;
		this.isRunning = false;
		this.changelogURL = "";
		this.description = "";
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isDownloaded() {
		return isDownloaded;
	}

	public void setDownloaded(boolean isDownloaded) {
		this.isDownloaded = isDownloaded;
	}

	public boolean isCanEdit() {
		return canEdit;
	}

	public void setCanEdit(boolean canEdit) {
		this.canEdit = canEdit;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getChangelogURL() {
		return changelogURL;
	}

	public void setChangelogURL(String changelogURL) {
		this.changelogURL = changelogURL;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return String.format("%s [%s]", version, getDownloadStatus());
	}
	
}
