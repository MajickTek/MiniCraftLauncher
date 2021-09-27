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

public class Util {
	public static Path launcherPath = Paths.get(System.getProperty("user.home"), "Documents", "MajickTek", "minilauncher");
	
	
	public static DefaultListModel<VersionObject> buildIndex(boolean isRelease, boolean modsFlag) {
		String baseURL = "https://github.com/MajickTek/MiniCraftLauncherIndex/raw/main/";
		
		String indexFileName = isRelease ? "release.index" : "dev.index";
		if(!isRelease && modsFlag) {
			indexFileName = "mods.index";
		}
		String indexURL = baseURL + indexFileName;
		
		String targetPath = launcherPath.toString();
		
		File f = new File(targetPath);
		f.mkdirs();
		
		f = Paths.get(targetPath, "index").toFile();
		f.mkdirs();
		
		f = Paths.get(targetPath, "versions").toFile();
		f.mkdirs();
		
		try {
			downloadUsingNIO(indexURL, Paths.get(targetPath, "index", indexFileName).toString());
		} catch (IOException e) {
			Debug.callCrashDialog("ERROR", "There was a problem downloading the files.\nCheck the console output.", Debug.ERR);
			e.printStackTrace();
		}
		
		Properties props = new OrderedProperties();
		FileInputStream fis;
		try {
			fis = new FileInputStream(Paths.get(targetPath, "index", indexFileName).toString());
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
	
	public static void downloadUsingNIO(String urlStr, String file) throws IOException {
        URL url = new URL(urlStr);
        ReadableByteChannel rbc = Channels.newChannel(url.openStream());
        FileOutputStream fos = new FileOutputStream(file);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        fos.close();
        rbc.close();
    }
	
	public static void launchJar(String path, String version) throws IOException {
		String gameDir = Paths.get(launcherPath.toString(), "saves", version).toString();
		
		File f = new File(gameDir);
		f.mkdirs();
		
		Runtime.getRuntime().exec(new String[] {"java", "-jar", path, "--savedir", gameDir});
	}
}
