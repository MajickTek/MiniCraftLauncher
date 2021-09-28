package com.mt.minilauncher;

import java.io.Serializable;

public class VersionObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String url;
	String version;
	boolean isDownloaded;
	
	boolean canEdit;
	
	public VersionObject(String url, String version) {
		this.url = parseURL(url, version);
		this.version = version;
		isDownloaded = false;
		canEdit = false;
	}
	
	public VersionObject(String url, String version, boolean canEdit) {
		this.url = parseURL(url, version);
		this.version = version;
		isDownloaded = false;
		this.canEdit = canEdit;
	}
	
	public VersionObject() {
		this.url = "UNKNOWN";
		this.version = "UNKNOWN";
		isDownloaded = false;
		canEdit = true;
	}
	
	private String parseURL(String str, String vs) {
		String base = str.replaceAll("\\$version", vs);
		return base;
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
	
	@Override
	public String toString() {
		return String.format("%s [%s]", version, getDownloadStatus());
	}
	
	public String toVerboseString() {
		return "VersionObject [url=" + url + ", version=" + version + ", isDownloaded=" + isDownloaded + "]";
	}
}
