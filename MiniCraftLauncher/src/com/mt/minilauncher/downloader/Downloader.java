package com.mt.minilauncher.downloader;

import javax.swing.JTextArea;

import com.littlebigberry.httpfiledownloader.FileDownloader;
import com.littlebigberry.httpfiledownloader.FileDownloaderDelegate;

public class Downloader implements FileDownloaderDelegate{

	String url, localLocation;
	JTextArea jta;
	
	public Downloader(String url, String localLocation) {
		this.url = url;
		this.localLocation = localLocation;
		jta = null;
	}

	public Downloader(String url, String localLocation, JTextArea jta) {
		this.url = url;
		this.localLocation = localLocation;
		this.jta = jta;
	}
	
	public void download() {
		FileDownloader fileDownloader = new FileDownloader(this);
		fileDownloader.setUrl(url);
		fileDownloader.setLocalLocation(localLocation);
		fileDownloader.beginDownload();
	}
	
	@Override
	public void didStartDownload(FileDownloader fileDownloader) {
		if(jta != null) {
			jta.setText("Starting download...");
		}
	}

	@Override
	public void didProgressDownload(FileDownloader fileDownloader) {
		if(jta != null) {
			jta.setText("Downloading: " + fileDownloader.getPercentComplete());
		}
	}

	@Override
	public void didFinishDownload(FileDownloader fileDownloader) {
		if(jta != null) {
			jta.setText("Ready to play!");
		}
	}

	@Override
	public void didFailDownload(FileDownloader fileDownloader) {
		System.err.println("Error");
	}
}
