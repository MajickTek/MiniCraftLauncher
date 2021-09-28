package com.mt.minilauncher;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map.Entry;
import java.util.Properties;

import javax.swing.DefaultListModel;
import javax.swing.ListModel;

public class Util {
	
	
	
	public static DefaultListModel<VersionObject> buildIndex(boolean isRelease, boolean modsFlag) {
		String baseURL = "https://github.com/MajickTek/MiniCraftLauncherIndex/raw/main/";
		
		String indexFileName = isRelease ? "release.index" : "dev.index";
		if(!isRelease && modsFlag) {
			indexFileName = "mods.index";
		}
		String indexURL = baseURL + indexFileName;
		
		
		
		try {
			downloadUsingNIO(indexURL, Paths.get(Initializer.indexPath.toString(), indexFileName).toString());
		} catch (IOException e) {
			Debug.callCrashDialog("ERROR", "There was a problem downloading the files.\nCheck the console output.", Debug.ERR);
			e.printStackTrace();
		}
		
		Properties props = new OrderedProperties();
		FileInputStream fis;
		try {
			fis = new FileInputStream(Paths.get(Initializer.indexPath.toString(), indexFileName).toString());
			props.load(fis);
			fis.close();
		} catch (IOException e) {
			Debug.callCrashDialog("ERROR", "There was a problem loading the files.\nCheck the console output.", Debug.ERR);
			e.printStackTrace();
		}
		
		DefaultListModel<VersionObject> model = new DefaultListModel<>();
		
		
		for(Entry<Object, Object> pairs: props.entrySet()) {
			String[] str = pairs.getValue().toString().split(",");
			String version = str[0];
			String url = str[1];
			model.add(Integer.parseInt(pairs.getKey().toString()), new VersionObject(url, version));
		}
		
		return model;
	}
	
	public static DefaultListModel<VersionObject> addToJList(ListModel<VersionObject> base, VersionObject vo, int index) {
		DefaultListModel<VersionObject> dlm = (DefaultListModel<VersionObject>) base;
		dlm.add(index, vo);
		
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
	
	public static void launchJar(String path, String version, LauncherWindow lw) throws IOException {
		String vPath = Paths.get(Initializer.savesDir.toString(), version).toString();
		Process ps = Runtime.getRuntime().exec(new String[] {"java", "-jar", path, "--savedir", vPath});
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
