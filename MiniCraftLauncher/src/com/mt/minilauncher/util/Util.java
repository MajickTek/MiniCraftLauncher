package com.mt.minilauncher.util;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.swing.JFrame;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import com.mt.minilauncher.Debug;
import com.mt.minilauncher.Initializer;
import com.mt.minilauncher.LauncherWindow;
import com.mt.minilauncher.downloader.Downloader;
import com.mt.minilauncher.objects.VersionObject;

public class Util {
	
	public static void downloadUsingNIO(String urlStr, String file) throws IOException {
        URL url = new URL(urlStr);
        ReadableByteChannel rbc = Channels.newChannel(url.openStream());
        FileOutputStream fos = new FileOutputStream(file);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        fos.close();
        rbc.close();
    }
	
	public static void downloadJar(VersionObject vo, LauncherWindow window) {
		String path = Paths.get(Initializer.jarPath.toString(), vo.version + ".jar").toString();
		System.out.println(String.format("Downloading: [URL:%s, path: %s]", vo.getURL(), path));
		Downloader downloader = new Downloader(vo.getURL(),
				path, window.getConsole(),
				() -> {// callback function which runs when download is finished (at 100% and hasn't
						// failed)
					vo.isDownloaded = true;
					window.getTree().updateUI();
				});
		downloader.download();
	}
	
	public static void launchJar(String path, String version, JFrame frame, boolean hideLauncher) throws IOException {
		String vPath = Paths.get(Initializer.savesDir.toString(), version).toString();
		Process ps = Runtime.getRuntime().exec(new String[] {"java", "-jar", path, "--savedir", vPath});
		if(hideLauncher) {
			frame.setVisible(false);
			try {
				ps.waitFor();
			} catch (InterruptedException e) {
				Debug.callCrashDialog("ERROR", "Something failed while waiting for the game to terminate.\nCheck the console output.", Debug.ERR);
				e.printStackTrace();
			}
			frame.setVisible(true);
		}
	}
	
	public static void launchJarSimple(JTree tree, LauncherWindow window) {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
		if (node == null)
			return;
		if (node.isLeaf() && !node.toString().equals("empty")) {
			VersionObject vo = (VersionObject) node.getUserObject();
			if (vo.isDownloaded) {
				System.out.println("Launching with no mods.");
				String jarPath = Paths.get(Initializer.jarPath.toString(), vo.version + ".jar").toString();
				try {
					launchJar(jarPath, vo.version, window.frmLauncher, true);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else {
				downloadJar(vo, window);
			}
		}
	}
	
	public static void purgeDirectory(File dir) {
	    for (File file: dir.listFiles()) {
	        if (file.isDirectory())
	            purgeDirectory(file);
	        file.delete();
	    }
	}
	
	public static void purgeDirectoryButKeepSubDirectories(File dir) {
	    for (File file: dir.listFiles()) {
	        if (!file.isDirectory())
	            file.delete();
	    }
	}
	
	public static void listResources() throws URISyntaxException, IOException {
		URL url = Util.class.getResource("/");
	    Path path = Paths.get(url.toURI());
	    Files.walk(path, 5).forEach(p -> System.out.printf("- %s%n", p.toString()));
	}
	
	public static void openNative(File f) throws IOException {
		if(Desktop.isDesktopSupported()) {
			Desktop.getDesktop().open(f);
		}
	}
	
	/**
	 * Parses a given string into a boolean.
	 * <br />
	 * Options (displayed as ternary operators):
	 * <br />
	 * foo ? true : false
	 * <br />
	 * foo ? yes : no
	 * <br />
	 * foo ? 1 : 0
	 * <br />
	 * The function is falsey, meaning if the text does not equal anything from the above examples, it will return false.
	 * @param bool a 1-word (1 token) String matching one of the above examples
	 * @return true or false
	 */
	public static boolean parseBoolean(String bool) {
		switch(bool) {
		case "true":
			return true;
		case "false":
			return false;
		case "yes":
			return true;
		case "no":
			return false;
		case "1":
			return true;
		case "0":
			return false;
		default:
			return false;
		}
	}
}
