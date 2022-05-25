package com.mt.minilauncher.launchwrap;

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
					Process ps = Runtime.getRuntime().exec(new String[] {"java", "-jar", path, "--savedir", vPath});
					vo.setRunning(true);
					if(window.getHideLauncherMenuItem().isSelected()) {
						window.frmLauncher.setVisible(false);
					}
					
					try {
						ps.waitFor();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					vo.setRunning(false);
					if(!window.frmLauncher.isVisible()) {
						window.frmLauncher.setVisible(true);
					}
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		
		if(!vo.isRunning) {
			gameThread.start();
		} else {
			window.getProgressBar().setText("This version is already running!");
		}
		
		
		
	}

}
