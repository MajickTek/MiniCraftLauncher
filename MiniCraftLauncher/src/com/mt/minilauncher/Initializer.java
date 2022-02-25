package com.mt.minilauncher;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.mt.minilauncher.util.Util;

public class Initializer {
	public static Path launcherPath = Paths.get(System.getProperty("user.home"), "Documents", "MajickTek", "minilauncher");
	public static Path savesDir = Paths.get(launcherPath.toString(), "saves");
	public static Path jarPath = Paths.get(launcherPath.toString(), "versions");
	public static Path indexPath = Paths.get(launcherPath.toString(), "index");
	
	
	public static void touchFoldersAndFiles() {
		File f = launcherPath.toFile();
		f.mkdirs();
		
		f = savesDir.toFile();
		f.mkdirs();
		
		f = jarPath.toFile();
		f.mkdirs();
		
		f = indexPath.toFile();
		f.mkdirs();
		
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
