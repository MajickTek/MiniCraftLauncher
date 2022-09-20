package com.mt.minilauncher;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.graphics.Point;
import org.eclipse.wb.swt.SWTResourceManager;

public class LauncherWindowSWT {

	protected Shell shlLauncher;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			LauncherWindowSWT window = new LauncherWindowSWT();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlLauncher.open();
		shlLauncher.layout();
		while (!shlLauncher.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlLauncher = new Shell();
		shlLauncher.setImage(SWTResourceManager.getImage(getClass(), "minicraftplus.png"));
		shlLauncher.setMinimumSize(new Point(800, 600));
		shlLauncher.setSize(450, 300);
		shlLauncher.setText("Launcher");

	}

}
