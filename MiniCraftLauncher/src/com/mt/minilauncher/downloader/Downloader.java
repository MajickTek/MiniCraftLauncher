package com.mt.minilauncher.downloader;

import com.littlebigberry.httpfiledownloader.FileDownloader;
import com.littlebigberry.httpfiledownloader.FileDownloaderDelegate;

public class Downloader implements FileDownloaderDelegate{

	String url, localLocation, percentage;
	
	public Downloader(String url, String localLocation) {
		this.url = url;
		this.localLocation = localLocation;
		percentage = "0";
	}

	public void download() {
		FileDownloader fileDownloader = new FileDownloader(this);
		fileDownloader.setUrl(url);
		fileDownloader.setLocalLocation(localLocation);
		fileDownloader.beginDownload();
	}
	
	@Override
	public void didStartDownload(FileDownloader fileDownloader) {
		System.out.println("Begin");
	}

	@Override
	public void didProgressDownload(FileDownloader fileDownloader) {
		percentage = fileDownloader.getPercentComplete();
	}

	@Override
	public void didFinishDownload(FileDownloader fileDownloader) {
		System.out.println("End");
	}

	@Override
	public void didFailDownload(FileDownloader fileDownloader) {
		System.err.println("Error");
	}

	public String getPercentage() {
		return percentage;
	}
}
