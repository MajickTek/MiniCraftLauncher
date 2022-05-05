package com.mt.minilauncher.launchwrap;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import com.mt.minilauncher.Initializer;
import com.mt.minilauncher.LauncherWindow;
import com.mt.minilauncher.objects.VersionObject;

public class FabricWrap implements IWrap {

	@Override
	public void launchJar(String path, String version, VersionObject vo, LauncherWindow window) {

		Thread gameThread = new Thread() {
			@Override
			public void run() {
				String vPath = Paths.get(Initializer.savesDir.toString(), version).toString();
				String lPath = Initializer.launcherPath.toString();
				File f = new File("FabricBootstrap.jar");
				if (!f.exists()) {
					window.getConsole().setText("Failed to use FabricWrap, could not find FabricBootstrap.jar!");
				} else {
					window.getConsole().setText(
							"Found FabricBootstrap.jar! Attempting to launch Fabric. Assuming the parameters have not changed.");
					try {
						// FabricBootstrap expects 3 arguments: the version folder, the jar file, and
						// the launcher folder
						Process ps = Runtime.getRuntime()
								.exec(new String[] { "java", "-jar", "FabricBootstrap.jar", vPath, path, lPath });
						vo.setRunning(true);
						if (window.getHideLauncherMenuItem().isSelected()) {
							window.frmLauncher.setVisible(false);
						}

						try {
							ps.waitFor();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						vo.setRunning(false);
						if (!window.frmLauncher.isVisible()) {
							window.frmLauncher.setVisible(true);
						}

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};

		if(!vo.isRunning) {
			gameThread.start();
		} else {
			window.getConsole().setText("This version is already running!");
		}

	}

}
