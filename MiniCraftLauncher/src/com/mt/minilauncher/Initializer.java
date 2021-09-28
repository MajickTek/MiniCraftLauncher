package com.mt.minilauncher;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

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
}
