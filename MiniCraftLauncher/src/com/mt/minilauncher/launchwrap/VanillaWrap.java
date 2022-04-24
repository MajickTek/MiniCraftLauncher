package com.mt.minilauncher.launchwrap;

import java.io.IOException;
import java.nio.file.Paths;

import com.mt.minilauncher.Initializer;

public class VanillaWrap implements IWrap{

	@Override
	public void launchJar(String path, String version) {
		String vPath = Paths.get(Initializer.savesDir.toString(), version).toString();
		try {
			Process ps = Runtime.getRuntime().exec(new String[] {"java", "-jar", path, "--savedir", vPath});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
