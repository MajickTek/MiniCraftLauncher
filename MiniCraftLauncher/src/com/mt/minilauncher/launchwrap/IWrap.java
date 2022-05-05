package com.mt.minilauncher.launchwrap;

import com.mt.minilauncher.LauncherWindow;
import com.mt.minilauncher.objects.VersionObject;

public interface IWrap {
	void launchJar(String path, String version, VersionObject vo, LauncherWindow window);
}
