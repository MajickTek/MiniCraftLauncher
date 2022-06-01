package com.mt.minilauncher.util;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import com.mt.minilauncher.Initializer;
import com.mt.minilauncher.LauncherWindow;
import com.mt.minilauncher.downloader.Downloader;
import com.mt.minilauncher.objects.VersionObject;
import com.mt.minilauncher.windows.DownloadDialog;

public class Util {
	
	public static void downloadUsingNIO(String urlStr, String file) throws IOException {
        URL url = new URL(urlStr);
        ReadableByteChannel rbc = Channels.newChannel(url.openStream());
        FileOutputStream fos = new FileOutputStream(file);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        fos.close();
        rbc.close();
    }
	
	public static void downloadJar(VersionObject vo, LauncherWindow window) {
		String path = Paths.get(Initializer.jarPath.toString(), vo.version + ".jar").toString();
		System.out.println(String.format("Downloading: [URL:%s, path: %s]", vo.getURL(), path));
		DownloadDialog dd = new DownloadDialog();
		dd.setVisible(true);
		Downloader downloader = new Downloader();
		downloader.setUrl(vo.url);
		downloader.setLocalLocation(path);
		downloader.setJTextArea(window.getProgressBar());
		downloader.setDownloadProgressCallback((f) -> {
			dd.getProgressBar().setValue((int) Double.parseDouble(f.getPercentComplete().replace('%', ' ').strip()));
			dd.getLabel().setText("Downloading: " + f.getPercentComplete());
			dd.setTitle("Downloading: " + f.getPercentComplete());
		});
		downloader.setDownloadFinishedCallback((f) -> {
			dd.dispose();
			vo.isDownloaded = true;
			window.getTree().updateUI();
		});
		downloader.download();
	}
	
	
	public static void purgeDirectory(File dir) {
	    for (File file: dir.listFiles()) {
	        if (file.isDirectory())
	            purgeDirectory(file);
	        file.delete();
	    }
	}
	
	public static void purgeDirectoryButKeepSubDirectories(File dir) {
	    for (File file: dir.listFiles()) {
	        if (!file.isDirectory())
	            file.delete();
	    }
	}
	
	public static void listResources() throws URISyntaxException, IOException {
		URL url = Util.class.getResource("/");
	    Path path = Paths.get(url.toURI());
	    Files.walk(path, 5).forEach(p -> System.out.printf("- %s%n", p.toString()));
	}
	
	public static void openNative(File f) throws IOException {
		if(Desktop.isDesktopSupported()) {
			Desktop.getDesktop().open(f);
		}
	}
	
}
