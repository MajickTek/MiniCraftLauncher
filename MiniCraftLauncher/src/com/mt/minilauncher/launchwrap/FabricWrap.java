package com.mt.minilauncher.launchwrap;

import java.nio.file.Paths;

import com.mt.minilauncher.Initializer;
import com.mt.minilauncher.LauncherWindow;


public class FabricWrap implements IWrap{

	@Override
	public void launchJar(String path, String version, LauncherWindow window) {
//		String vPath = Paths.get(Initializer.savesDir.toString(), version).toString();
//		String lPath = Initializer.launcherPath.toString();
//		Thread gameThread = new Thread() {
//			@Override
//			public void run() {
//				System.setProperty("fabric.skipMcProvider", "true");
//				System.setProperty("fabric.gameJarPath", path);
//				KnotClient.main(new String[] {"--gameDir", lPath, "--savedir", vPath});
//			}
//		};
//		gameThread.start();
		
	}

}
