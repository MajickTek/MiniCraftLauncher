package com.mt.minilauncher.launchwrap;

import java.nio.file.Paths;

import javax.swing.tree.DefaultMutableTreeNode;

import com.mt.minilauncher.Initializer;
import com.mt.minilauncher.LauncherWindow;
import com.mt.minilauncher.objects.VersionObject;
import com.mt.minilauncher.util.Util;

public class LauncherWrapper {
	IWrap wrapper;
	LauncherWindow window;
	
	
	public LauncherWrapper(IWrap wrapper, LauncherWindow window) {
		this.wrapper = wrapper;
		this.window = window;
	}
	
	public void launch() {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) window.getTree().getLastSelectedPathComponent();
		if (node == null)
			return;
		if (node.isLeaf() && !node.toString().equals("empty")) {
			VersionObject vo = (VersionObject) node.getUserObject();
			if (vo.isDownloaded) {
				System.out.println("Launching with no mods.");
				String jarPath = Paths.get(Initializer.jarPath.toString(), vo.version + ".jar").toString();
				wrapper.launchJar(jarPath, vo.version, window);
			} else {
				Util.downloadJar(vo, window);
			}
		}
	}
	public IWrap getWrapper() {
		return wrapper;
	}
	public void setWrapper(IWrap wrapper) {
		this.wrapper = wrapper;
	}
	public LauncherWindow getWindow() {
		return window;
	}
	public void setWindow(LauncherWindow window) {
		this.window = window;
	}
	
	
}
