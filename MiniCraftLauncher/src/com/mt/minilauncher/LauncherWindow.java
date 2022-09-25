package com.mt.minilauncher;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.graphics.Point;
import org.eclipse.wb.swt.SWTResourceManager;
import org.xml.sax.SAXException;

import com.mt.minilauncher.objects.ChannelObject;
import com.mt.minilauncher.objects.GenericLabelProvider;
import com.mt.minilauncher.objects.VersionObject;
import com.mt.minilauncher.util.Util;

import de.skuzzle.semantic.Version;
import swing2swt.layout.BorderLayout;
import org.eclipse.swt.widgets.Menu;

import java.io.IOException;
import java.net.URISyntaxException;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.dialogs.ListDialog;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

public class LauncherWindow {

	protected Shell shlLauncher;
	private Tree tree;
	private ProgressBar progressBar;

	public static LauncherWindow instance;

	public static Version version = Version.parseVersion("1.6.4");

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			LauncherWindow window = new LauncherWindow();
			instance = window;
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
		shlLauncher.setImage(SWTResourceManager.getImage(getClass(), "/minicraftplus.png"));
		shlLauncher.setMinimumSize(new Point(800, 600));
		shlLauncher.setSize(800, 600);
		shlLauncher.setText(String.format("Launcher [%s]", version));
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
					e1.printStackTrace();
					Util.openErrorDialog(shlLauncher, "The launcher folder could not be opened.", e1);
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

		MenuItem selectChannelMenuItem = new MenuItem(editMenuContainer, SWT.NONE);
		selectChannelMenuItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ListDialog ld = new ListDialog(getShell());
				ld.setContentProvider(ArrayContentProvider.getInstance());
				ld.setLabelProvider(GenericLabelProvider.getInstance());

				ld.setTitle("Channel Selector");
				ld.setMessage("Please select a channel to download versions from:");
				try {
					ld.setInput(Util.buildChannelList());
				} catch (ParserConfigurationException | SAXException | IOException e1) {
					e1.printStackTrace();
					Util.openErrorDialog(shlLauncher, "There was an error loading and/or parsing the list of channels.", e1);
				}

				ld.open();

				try {
					for (TreeItem t : LauncherWindow.instance.getTree().getItems()) {
						t.dispose();
					}

					if(ld.getResult() != null && ld.getResult().length > 0) {
						Util.populateTree((ChannelObject) ld.getResult()[0]);
					}
				} catch (IOException e1) {
					e1.printStackTrace();
					Util.openErrorDialog(shlLauncher, "There was an error populating the tree with game versions.", e1);
				}
			}

		});
		selectChannelMenuItem.setText("Select Channel");

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
				// TODO
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
					e1.printStackTrace();
					Util.openErrorDialog(shlLauncher, "There was an error locating a browser or navigating to the URL.", e1);
				}
			}
		});
		wikiMenuItem.setText("Wiki");

		tree = new Tree(shlLauncher, SWT.BORDER);
		tree.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				if(e.button == 1) {//left click
					TreeItem selectedItem = tree.getItem(new Point(e.x, e.y));
					
					//Make sure the VersionObject exists! TYPE SAFETY!
					VersionObject vo = (selectedItem != null && selectedItem.getData("VersionObject") != null)
							&& selectedItem.getData("VersionObject") instanceof VersionObject
									? (VersionObject) selectedItem.getData("VersionObject")
									: null;

					if(vo != null) {
						vo.getLauncherWrapper().launch();
					}
				}
			}

			@Override
			public void mouseDown(MouseEvent e) {
				if (e.button == 3) {// right click
					TreeItem selectedItem = tree.getItem(new Point(e.x, e.y));
					//TODO: popup menu
				}
			}
		});
		tree.setLayoutData(BorderLayout.CENTER);

		ToolBar toolBar = new ToolBar(shlLauncher, SWT.FLAT | SWT.RIGHT);
		toolBar.setLayoutData(BorderLayout.SOUTH);

		ToolItem tltmRefresh = new ToolItem(toolBar, SWT.NONE);
		tltmRefresh.setText("Refresh");

		ToolItem tltmToggleConsole = new ToolItem(toolBar, SWT.CHECK);
		tltmToggleConsole.setText("Toggle Console");

		progressBar = new ProgressBar(shlLauncher, SWT.NONE);
		progressBar.setLayoutData(BorderLayout.NORTH);

	}

	public Shell getShell() {
		return shlLauncher;
	}

	public Tree getTree() {
		return tree;
	}

	public ProgressBar getProgressBar() {
		return progressBar;
	}

}