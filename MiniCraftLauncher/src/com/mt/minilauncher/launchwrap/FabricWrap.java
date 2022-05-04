package com.mt.minilauncher.launchwrap;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import com.mt.minilauncher.Initializer;
import com.mt.minilauncher.LauncherWindow;


public class FabricWrap implements IWrap{

	@Override
	public void launchJar(String path, String version, LauncherWindow window) {

		String vPath = Paths.get(Initializer.savesDir.toString(), version).toString();
		String lPath = Initializer.launcherPath.toString();
		File f = new File("FabricBootstrap.jar");
		if(!f.exists()) {
			window.getConsole().setText("Failed to use FabricWrap, could not find FabricBootstrap.jar!");
		} else {
			try {
				Process ps = Runtime.getRuntime().exec(new String[] {"java", "-jar", "FabricBootstrap.jar", vPath, path, lPath});
				
				//hide launcher
				window.frmLauncher.setVisible(false);
				try {
					ps.waitFor();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				window.frmLauncher.setVisible(true);
				//end hide launcher
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
