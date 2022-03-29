package com.mt.minilauncher;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import com.mt.minilauncher.util.Util;

public class Initializer {
	public static Properties options = new Properties();
	public static String separator = System.getProperty("file.separator");
	public static Path launcherPath = Paths.get(System.getProperty("user.home"), "Documents", "MajickTek", "minilauncher");
	public static Path savesDir = Paths.get(launcherPath.toString(), "saves");
	public static Path jarPath = Paths.get(launcherPath.toString(), "versions");
	public static Path indexPath = Paths.get(launcherPath.toString(), "index");
	public static Path configPath = Paths.get(launcherPath.toString(), "config");
	public static File optionsFile = new File(Paths.get(configPath.toString(), "options.txt").toUri());
	
	public static void touchFoldersAndFiles() {
		File f = launcherPath.toFile();
		if(f.mkdirs()) {
			System.out.println("Created launcher path: " + f.toString());
		} else {
			System.out.println("Launcher path already created: " + f.toString());
		}
		
		f = savesDir.toFile();
		if(f.mkdirs()) {
			System.out.println("Created saves directory: " + f.toString());
		} else {
			System.out.println("saves directory already created: " + f.toString());
		}
		
		f = jarPath.toFile();
		if(f.mkdirs()) {
			System.out.println("Created jar path: " + f.toString());
		} else {
			System.out.println("jar path already created: " + f.toString());
		}
		
		f = indexPath.toFile();
		if(f.mkdirs()) {
			System.out.println("Created index path: " + f.toString());
		} else {
			System.out.println("index path already created: " + f.toString());
		}
		
		f = configPath.toFile();
		if(f.mkdirs()) {
			System.out.println("Created config path: " + f.toString());
		} else {
			System.out.println("config path already created: " + f.toString());
		}
		
		f = optionsFile;
		try {
			if(f.createNewFile()) {
				initOptionsFile();
				System.out.println("Created options.txt: " + f.toString());
			} else {
				options.load(new FileInputStream(optionsFile));
				System.out.println("options.txt already created: " + f.toString() + " |Or may have failed to be created.");
			}
		} catch (IOException e) {
			Debug.callCrashDialog("ERROR", "There was an error creating the options.txt config file. Look at the console for further details.", Debug.ERR);
			e.printStackTrace();
		}
		
		
	}
	
	private static void initOptionsFile() throws IOException {
		PrintWriter pw = new PrintWriter(optionsFile);
		pw.append("window.hideDuringPlay=true\n");
		pw.close();
		options.load(new FileInputStream(optionsFile));
	}

	public static void cleanFolders() {
		int x = Debug.callConfirmDialog("Warning!", "This will clean all of the launcher's folders.\nYou will lose all game data and all downloaded jars.");
		
		if(x == Debug.OK) {
			Util.purgeDirectory(launcherPath.toFile());
			touchFoldersAndFiles();
			LauncherWindow.instance.forceReDownload();
		} else {
			return;
		}
	}
	
	public static void cleanVersion(String version) {
		int x = Debug.callConfirmDialog("Warning!", "This will clean all of the data associated with this version.\nThis includes save files and downloaded jars.");

		if(x == Debug.OK) {
			String jar = version + ".jar";
			File f = Paths.get(jarPath.toString(), jar).toFile();
			if(f.exists()) {
				f.delete();
			}
			
			File f2 = Paths.get(savesDir.toString(), version).toFile();
			if(f2.exists()) {
				cleanFolder(f2);
			}
		} else {
			return;
		}
		
	}
	
	public static void cleanFolder(Path p) {
		Util.purgeDirectory(p.toFile());
	}
	
	public static void cleanFolder(File f) {
		Util.purgeDirectory(f);
	}
	
}
