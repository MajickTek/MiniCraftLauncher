package com.mt.minilauncher.util;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map.Entry;
import java.util.Properties;

import javax.swing.DefaultListModel;
import javax.swing.ListModel;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.mt.minilauncher.Debug;
import com.mt.minilauncher.Initializer;
import com.mt.minilauncher.LauncherWindow;
import com.mt.minilauncher.VersionObject;
import com.mt.minilauncher.downloader.Downloader;

public class Util {
	
	
	
	public static DefaultListModel<VersionObject> buildIndex(boolean isRelease, boolean modsFlag) {
		String baseURL = "https://raw.githubusercontent.com/MajickTek/MiniCraftLauncherIndex/main/";
		
		String indexFileName = isRelease ? "release.xml" : "dev.xml";
		if(!isRelease && modsFlag) {
			indexFileName = "mods.xml";
		}
		String indexURL = baseURL + indexFileName;
	
		try {
			downloadUsingNIO(indexURL, Paths.get(Initializer.indexPath.toString(), indexFileName).toString());
		} catch (IOException e1) {
			Debug.callCrashDialog("ERROR", "Failed to download index file. Check console for details.", Debug.ERR);
			e1.printStackTrace();
		}
		DefaultListModel<VersionObject> model = new DefaultListModel<>();
		
		VersionObject[] vos;
		try {
			vos = XMLConverter.fromXML(Paths.get(Initializer.indexPath.toString(), indexFileName).toString());
			for(int i = 0; i < vos.length; i++ ) {
				model.add(i, vos[i]);
			}
		} catch (ParserConfigurationException | SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return model;
	}
	
	
	
	public static DefaultListModel<VersionObject> addToJList(ListModel<VersionObject> base, VersionObject vo, int index) {
		DefaultListModel<VersionObject> dlm = (DefaultListModel<VersionObject>) base;
		dlm.add(index, vo);
		
		return dlm;
	}
	
	public static DefaultListModel<VersionObject> removeFromJList(ListModel<VersionObject> base, int index) {
		DefaultListModel<VersionObject> dlm = (DefaultListModel<VersionObject>) base;
		dlm.remove(index);
		
		return dlm;
	}
	
	public static void downloadUsingNIO(String urlStr, String file) throws IOException {
        URL url = new URL(urlStr);
        ReadableByteChannel rbc = Channels.newChannel(url.openStream());
        FileOutputStream fos = new FileOutputStream(file);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        fos.close();
        rbc.close();
    }
	
	public static void launchJar(String path, String version, LauncherWindow lw, boolean hideLauncher) throws IOException {
		String vPath = Paths.get(Initializer.savesDir.toString(), version).toString();
		Process ps = Runtime.getRuntime().exec(new String[] {"java", "-jar", path, "--savedir", vPath});
		if(hideLauncher) {
			lw.getFrmLauncher().setVisible(false);
			try {
				ps.waitFor();
			} catch (InterruptedException e) {
				Debug.callCrashDialog("ERROR", "Something failed while waiting for the game to terminate.\nCheck the console output.", Debug.ERR);
				e.printStackTrace();
			}
			lw.getFrmLauncher().setVisible(true);
		}
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
	
	/**
	 * Parses a given string into a boolean.
	 * <br />
	 * Options (displayed as ternary operators):
	 * <br />
	 * foo ? true : false
	 * <br />
	 * foo ? yes : no
	 * <br />
	 * foo ? 1 : 0
	 * <br />
	 * The function is falsey, meaning if the text does not equal anything from the above examples, it will return false.
	 * @param bool a 1-word (1 token) String matching one of the above examples
	 * @return true or false
	 */
	public static boolean parseBoolean(String bool) {
		switch(bool) {
		case "true":
			return true;
		case "false":
			return false;
		case "yes":
			return true;
		case "no":
			return false;
		case "1":
			return true;
		case "0":
			return false;
		default:
			return false;
		}
	}
}
