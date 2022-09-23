package com.mt.minilauncher.util;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
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

public class Util {
	
	/**
	 * Downloads a file from a URL using a java.nio stream
	 * @param urlStr the url from which to download
	 * @param file the local filename and path after downloading
	 * @throws IOException if the file cannot be written
	 */
	public static void downloadUsingNIO(String urlStr, String file) throws IOException {
        URL url = new URL(urlStr);
        ReadableByteChannel rbc = Channels.newChannel(url.openStream());
        FileOutputStream fos = new FileOutputStream(file);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        fos.close();
        rbc.close();
    }
	
	/**
	 * Method to download a jar file using com.mt.minilauncher.Downloader using information from a VersionObject
	 * @param vo the object from which the url and filename are gathered
	 * @param window the window instance used as a context (so the progressbar and tree can be refreshed)
	 */
	public static void downloadJar(VersionObject vo, LauncherWindow window) {
		String path = Paths.get(Initializer.jarPath.toString(), vo.version + ".jar").toString();
		System.out.println(String.format("Downloading: [URL:%s, path: %s]", vo.getURL(), path));


		Downloader downloader = new Downloader();
		downloader.setUrl(vo.url);
		downloader.setLocalLocation(path);
		
		downloader.setDownloadProgressCallback((f) -> {
			//TODO
//			window.getProgressBar().setValue((int) Double.parseDouble(f.getPercentComplete().replace('%', ' ').strip()));
//			window.getProgressBarLabel().setText("Downloading: " + f.getPercentComplete());

		});
		downloader.setDownloadFinishedCallback((f) -> {
			//TODO
//			window.getProgressBarLabel().setText("Ready to Play! [" + vo.version + "]");
//			vo.isDownloaded = true;
//			window.getTree().updateUI();
		});
		downloader.download();
	}
	
	/**
	 * Recursively deletes (empties) a given directory
	 * @param dir a file or directory to delete
	 */
	public static void purgeDirectory(File dir) {
	    for (File file: dir.listFiles()) {
	        if (file.isDirectory())
	            purgeDirectory(file);
	        file.delete();
	    }
	}
	
	/**
	 * Same as purgeDirectory() but preserves the subdirectories
	 * @param dir
	 */
	public static void purgeDirectoryButKeepSubDirectories(File dir) {
	    for (File file: dir.listFiles()) {
	        if (!file.isDirectory())
	            file.delete();
	    }
	}
	
	/**
	 * Lists all of the class resources starting from the root relative to this Util class.
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	public static void listResources() throws URISyntaxException, IOException {
		URL url = Util.class.getResource("/");
	    Path path = Paths.get(url.toURI());
	    Files.walk(path, 5).forEach(p -> System.out.printf("- %s%n", p.toString()));
	}
	
	/**
	 * Opens a file through the operating system. Depending on implementation, this may also open a URL in the default browser.
	 * <br />
	 * One usage example is to open a folder using Windows Explorer
	 * @param f the file/directory/url to open
	 * @throws IOException file not found
	 */
	public static void openNative(File f) throws IOException {
		if(Desktop.isDesktopSupported()) {
			Desktop.getDesktop().open(f);
		}
	}
	
	/**
	 * like openNative(), but is specifically meant to open the system's web browser.
	 * @param url A web URL such as "https://www.google.com". A filepath can not be entered here.
	 * @throws MalformedURLException Incorrect url format
	 * @throws IOException Java cannot find/launch the system's default browser
	 * @throws URISyntaxException String cannot be parsed as a URL
	 */
	public static void browseNative(String url) throws MalformedURLException, IOException, URISyntaxException{
		if(Desktop.isDesktopSupported()) {
			Desktop.getDesktop().browse(new URL(url).toURI());
		}
	}
	
}
