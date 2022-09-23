package com.mt.minilauncher;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.graphics.Point;
import org.eclipse.wb.swt.SWTResourceManager;

import com.mt.minilauncher.util.Util;

import swing2swt.layout.BorderLayout;
import org.eclipse.swt.widgets.Menu;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

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
		Initializer.touchFoldersAndFiles();
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
		
		MenuItem fileMenu = new MenuItem(menu, SWT.CASCADE);
		fileMenu.setText("File");
		
		Menu fileMenuContainer = new Menu(fileMenu);
		fileMenu.setMenu(fileMenuContainer);
		
		MenuItem openLauncherFolderMenuItem = new MenuItem(fileMenuContainer, SWT.NONE);
		openLauncherFolderMenuItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					Util.openNative(Initializer.launcherPath.toFile());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		openLauncherFolderMenuItem.setText("Open Launcher Folder");
		
		MenuItem exitMenuItem = new MenuItem(fileMenuContainer, SWT.NONE);
		exitMenuItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.exit(0);
			}
		});
		exitMenuItem.setText("Exit");
		
		MenuItem editMenu = new MenuItem(menu, SWT.CASCADE);
		editMenu.setText("Edit");
		
		Menu editMenuContainer = new Menu(editMenu);
		editMenu.setMenu(editMenuContainer);
		
		MenuItem optionsMenu = new MenuItem(menu, SWT.CASCADE);
		optionsMenu.setText("Options");
		
		Menu optionsMenuContainer = new Menu(optionsMenu);
		optionsMenu.setMenu(optionsMenuContainer);
		
		MenuItem helpMenu = new MenuItem(menu, SWT.CASCADE);
		helpMenu.setText("Help");
		
		Menu helpMenuContainer = new Menu(helpMenu);
		helpMenu.setMenu(helpMenuContainer);
		
		MenuItem aboutMenuItem = new MenuItem(helpMenuContainer, SWT.NONE);
		aboutMenuItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Debug.callCrashDialog("Test", "hello", Debug.INF);
			}
		});
		aboutMenuItem.setText("About");
		
		MenuItem wikiMenuItem = new MenuItem(helpMenuContainer, SWT.NONE);
		wikiMenuItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					Util.browseNative("https://github.com/MajickTek/MiniCraftLauncher/wiki");
				} catch (IOException | URISyntaxException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		wikiMenuItem.setText("Wiki");
		
		Tree tree = new Tree(shlLauncher, SWT.BORDER);
		tree.setLayoutData(BorderLayout.CENTER);
		
		ToolBar toolBar = new ToolBar(shlLauncher, SWT.FLAT | SWT.RIGHT);
		toolBar.setLayoutData(BorderLayout.SOUTH);
		
		ToolItem tltmRefresh = new ToolItem(toolBar, SWT.NONE);
		tltmRefresh.setText("Refresh");
		
		ToolItem tltmToggleConsole = new ToolItem(toolBar, SWT.CHECK);
		tltmToggleConsole.setText("Toggle Console");
		
		ProgressBar progressBar = new ProgressBar(shlLauncher, SWT.NONE);
		progressBar.setLayoutData(BorderLayout.NORTH);
		
	}
}
