package com.mt.minilauncher.launchwrap;

import java.nio.file.Paths;

import javax.swing.tree.DefaultMutableTreeNode;

import org.eclipse.swt.widgets.TreeItem;

import com.mt.minilauncher.Initializer;
import com.mt.minilauncher.LauncherWindow;
import com.mt.minilauncher.objects.EmptyObject;
import com.mt.minilauncher.objects.VersionObject;
import com.mt.minilauncher.util.Util;

public class LauncherWrapper {
	IWrap wrapper;
	LauncherWindow window;
	String prompt = String.format("[%s] ", LauncherWrapper.class.getSimpleName());
	
	
	public LauncherWrapper(IWrap wrapper, LauncherWindow window) {
		this.wrapper = wrapper;
		this.window = window;
		
	}
	
	public void launch() {
		System.out.println(prompt + "Beginning launch process.");
		
		TreeItem selectedItem = window.getTree().getSelection()[0];
		if(selectedItem == null) {return;}
		if(selectedItem.getData("VersionObject") instanceof VersionObject) {
			VersionObject vo = (VersionObject) selectedItem.getData("VersionObject");
			if(vo.isDownloaded) {
				System.out.println(String.format("%sLaunching game using the [%s] system.", prompt, wrapper.getClass().getSimpleName()));
				String jarPath = Paths.get(Initializer.jarPath.toString(), vo.version + ".jar").toString();
				wrapper.launchJar(jarPath, vo.version, vo, window);
			} else {
				System.out.println(prompt + "Beginning download process.");
				Util.downloadJar(vo, window);
				System.out.println(prompt + "Download complete.");
			}
		}
		
		System.out.println(prompt + "Leaving launch process.");
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
