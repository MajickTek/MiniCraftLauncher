package com.mt.minilauncher;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.graphics.Point;
import org.eclipse.wb.swt.SWTResourceManager;
import swing2swt.layout.BorderLayout;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ProgressBar;

public class LauncherWindowSWT {

	protected Shell shlLauncher;
	public Debug debug;
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
		debug = new Debug(shlLauncher);
		shlLauncher.setImage(SWTResourceManager.getImage(getClass(), "/minicraftplus.png"));
		shlLauncher.setMinimumSize(new Point(800, 600));
		shlLauncher.setSize(800, 600);
		shlLauncher.setText("Launcher");
		shlLauncher.setLayout(new BorderLayout(0, 0));
		
		Menu menu = new Menu(shlLauncher, SWT.BAR);
		shlLauncher.setMenuBar(menu);
		
		Tree tree = new Tree(shlLauncher, SWT.BORDER);
		tree.setLayoutData(BorderLayout.CENTER);
		
		ToolBar toolBar = new ToolBar(shlLauncher, SWT.FLAT | SWT.RIGHT);
		toolBar.setLayoutData(BorderLayout.SOUTH);
		
		ProgressBar progressBar = new ProgressBar(shlLauncher, SWT.NONE);
		progressBar.setLayoutData(BorderLayout.NORTH);
		
	}
}
