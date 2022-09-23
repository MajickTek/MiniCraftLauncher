package com.mt.minilauncher.launchwrap;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.nio.file.Paths;

import com.mt.minilauncher.Initializer;
import com.mt.minilauncher.LauncherWindow;
import com.mt.minilauncher.objects.VersionObject;

public class VanillaWrap implements IWrap{

	@Override
	public void launchJar(String path, String version, VersionObject vo, LauncherWindow window) {

		Thread gameThread = new Thread() {
			@Override
			public void run() {
				String vPath = Paths.get(Initializer.savesDir.toString(), version).toString();
				try {
					ProcessBuilder pb = new ProcessBuilder().command("java", "-jar", path, "--savedir", vPath);
					pb.redirectErrorStream(true);
					Process ps = pb.start();
					
					
					vo.setRunning(true);
//	TODO:				if(window.getHideLauncherMenuItem().isSelected()) {
//						window.frmLauncher.setVisible(false);
//					}
					
					String line;
					BufferedReader in = new BufferedReader(new InputStreamReader(ps.getInputStream()));
					while ((line = in.readLine()) != null) {
					    System.out.println(line);
					}
					in.close();
					
					try {
						ps.waitFor();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					vo.setRunning(false);
//	TODO:				if(!window.frmLauncher.isVisible()) {
//						window.frmLauncher.setVisible(true);
//					}
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		
		if(!vo.isRunning) {
			gameThread.start();
		} else {
			System.out.println("User attempted to launch a version that is already running: " + vo.version);
		}
		
		
		
	}

	@Override
	public String getWrapperName() {
		return "VanillaWrap";
	}

}
