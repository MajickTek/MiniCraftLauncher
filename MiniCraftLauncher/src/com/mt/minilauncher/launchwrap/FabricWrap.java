package com.mt.minilauncher.launchwrap;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;

import org.eclipse.jface.dialogs.MessageDialog;

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
				File versionFolder = new File(vPath);
				versionFolder.mkdirs();
				
				
				//String lPath = Initializer.launcherPath.toString();
				String lPath = Paths.get(vPath.toString(), "playminicraft").toString();
				File launcherFolder = new File(lPath.toString());
				launcherFolder.mkdirs();
				
				File f = new File("FabricBootstrap.jar");
				if (!f.exists()) {
					String error = "Failed to use FabricWrap, could not find FabricBootstrap.jar!";
					System.err.println(error);
					
					MessageDialog.openError(LauncherWindow.getShell(), "Fabric Loader", error);
				} else {
					System.out.println("Found FabricBootstrap.jar! Attempting to launch Fabric. Assuming the parameters have not changed.");
					try {
						// FabricBootstrap expects 3 arguments: the version folder, the jar file, and
						// the launcher folder
						
						ProcessBuilder pb = new ProcessBuilder().command("java", "-jar", "FabricBootstrap.jar", vPath, path, lPath);
						pb.redirectErrorStream(true);
						Process process = pb.start();
						

						vo.setRunning(true);
//		TODO:				if (window.getHideLauncherMenuItem().isSelected()) {
//							window.frmLauncher.setVisible(false);
//						}

						String line;
						BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
						while ((line = in.readLine()) != null) {
						    System.out.println(line);
						}
						in.close();
						
						try {
							process.waitFor();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						vo.setRunning(false);
//		TODO:				if (!window.frmLauncher.isVisible()) {
//							window.frmLauncher.setVisible(true);
//						}

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
			System.out.println("User attempted to launch a version that is already running: " + vo.version);
		}

	}

	@Override
	public String getWrapperName() {
		return "FabricWrap";
	}

}
