package com.mt.minilauncher.downloader;

import com.littlebigberry.httpfiledownloader.FileDownloader;

public interface DownloaderCallback {
	public void call(FileDownloader fileDownloader);
}
