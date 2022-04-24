package com.mt.minilauncher.launchwrap;

import com.mt.minilauncher.LauncherWindow;

public class ASMWrap implements IWrap{

	@Override
	public void launchJar(String path, String version, LauncherWindow window) {
		System.err.println(String.format("[%s] Not yet implemented.", this.getClass().getSimpleName()));
	}

}
