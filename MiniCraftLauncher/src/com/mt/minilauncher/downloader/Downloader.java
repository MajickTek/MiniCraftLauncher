package com.mt.minilauncher.downloader;

import com.littlebigberry.httpfiledownloader.FileDownloader;
import com.littlebigberry.httpfiledownloader.FileDownloaderDelegate;

public class Downloader implements FileDownloaderDelegate{

	String url, localLocation;

	DownloaderCallback downloadStartCallback, downloadFinishedCallback, downloadProgressCallback, downloadFailCallback;
	
	FileDownloader fileDownloader;
	
	public Downloader() {
		this.fileDownloader = new FileDownloader(this);
		this.url = "";
		this.localLocation = "";
		
		
		this.downloadStartCallback = null;
		this.downloadFinishedCallback = null;
		this.downloadProgressCallback = null;
		this.downloadFailCallback = null;
	}
	
	
	public void download() {
		if((!url.isBlank() && !url.isEmpty()) && (!localLocation.isBlank() && !localLocation.isEmpty())) {
			fileDownloader.setUrl(url);
			fileDownloader.setLocalLocation(localLocation);
			fileDownloader.beginDownload();
		}
	}
	
	@Override
	public void didStartDownload(FileDownloader fileDownloader) {
		
		if(downloadStartCallback != null) {
			downloadStartCallback.call(fileDownloader);
		}
	}

	@Override
	public void didProgressDownload(FileDownloader fileDownloader) {
		
		if(downloadProgressCallback != null) {
			downloadProgressCallback.call(fileDownloader);
		}
	}

	@Override
	public void didFinishDownload(FileDownloader fileDownloader) {
		
		if(downloadFinishedCallback != null) {
			downloadFinishedCallback.call(fileDownloader);
		}
	}

	@Override
	public void didFailDownload(FileDownloader fileDownloader) {
		System.err.println("Error");
		if(downloadFailCallback != null) {
			downloadFailCallback.call(fileDownloader);
		}
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public String getLocalLocation() {
		return localLocation;
	}


	public void setLocalLocation(String localLocation) {
		this.localLocation = localLocation;
	}


	public DownloaderCallback getDownloadFinishedCallback() {
		return downloadFinishedCallback;
	}


	public void setDownloadFinishedCallback(DownloaderCallback downloadFinishedCallback) {
		this.downloadFinishedCallback = downloadFinishedCallback;
	}


	public DownloaderCallback getDownloadProgressCallback() {
		return downloadProgressCallback;
	}


	public void setDownloadProgressCallback(DownloaderCallback downloadProgressCallback) {
		this.downloadProgressCallback = downloadProgressCallback;
	}


	public DownloaderCallback getDownloadStartCallback() {
		return downloadStartCallback;
	}


	public void setDownloadStartCallback(DownloaderCallback downloadStartCallback) {
		this.downloadStartCallback = downloadStartCallback;
	}


	public DownloaderCallback getDownloadFailCallback() {
		return downloadFailCallback;
	}


	public void setDownloadFailCallback(DownloaderCallback downloadFailCallback) {
		this.downloadFailCallback = downloadFailCallback;
	}


	public FileDownloader getFileDownloader() {
		return fileDownloader;
	}


	public void setFileDownloader(FileDownloader fileDownloader) {
		this.fileDownloader = fileDownloader;
	}
	
	
}
