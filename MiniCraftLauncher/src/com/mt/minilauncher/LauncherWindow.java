package com.mt.minilauncher;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import com.mt.minilauncher.launchwrap.FabricWrap;
import com.mt.minilauncher.launchwrap.VanillaWrap;
import com.mt.minilauncher.objects.EmptyObject;
import com.mt.minilauncher.objects.VersionObject;
import com.mt.minilauncher.util.EditUtil;
import com.mt.minilauncher.util.GetMD5FromJar;
import com.mt.minilauncher.util.Util;
import com.mt.minilauncher.windows.AboutPanel;
import com.mt.minilauncher.windows.ChangelogViewer;
import com.mt.minilauncher.windows.ChannelSelector;
import com.mt.minilauncher.windows.Console;

import de.skuzzle.semantic.Version;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JToolBar;
import javax.swing.JButton;
import java.awt.Toolkit;
import javax.swing.JToggleButton;

public class LauncherWindow {

	public static Version version = Version.parseVersion("1.6.3-2");
	
	public static LauncherWindow instance;
	public JFrame frmLauncher;
	private JTree tree;
	private JTextArea progressBar;
	private JCheckBoxMenuItem hideLauncherMenuItem;
	private Console console;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LauncherWindow window = new LauncherWindow();
					window.frmLauncher.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LauncherWindow() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		instance = this;
		Initializer.touchFoldersAndFiles();
		console = new Console();
		System.setOut(console.getCon());
		System.setErr(console.getCon());
		frmLauncher = new JFrame();
		frmLauncher.setTitle(String.format("Launcher [%s]", version));
		frmLauncher.setBounds(100, 100, 800, 600);
		frmLauncher.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmLauncher.setIconImage(Toolkit.getDefaultToolkit().getImage(LauncherWindow.class.getResource("/minicraftplus.png")));
		JMenuBar menuBar = new JMenuBar();
		frmLauncher.setJMenuBar(menuBar);

		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);

		JMenuItem launcherFolderMenuItem = new JMenuItem("Open Launcher Folder");
		launcherFolderMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Util.openNative(Initializer.launcherPath.toFile());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		fileMenu.add(launcherFolderMenuItem);

		JMenuItem exitMenuItem = new JMenuItem("Exit");
		exitMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		fileMenu.add(exitMenuItem);

		JMenu editMenu = new JMenu("Edit");
		menuBar.add(editMenu);

		JMenuItem channelMenuItem = new JMenuItem("Select Channel");
		channelMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ChannelSelector cs = new ChannelSelector();
				cs.setVisible(true);
			}
		});
		editMenu.add(channelMenuItem);

		JMenuItem cleanIndexMenuItem = new JMenuItem("Clean Index");
		cleanIndexMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Util.purgeDirectory(Initializer.indexPath.toFile());
			}
		});
		editMenu.add(cleanIndexMenuItem);

		JMenuItem cleanFoldersMenuItem = new JMenuItem("Clean Folders");
		cleanFoldersMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int x = Debug.callConfirmDialog("Warning!", "This will clean all of the launcher's folders.\nYou will lose all game data and all downloaded jars.");
				
				if(x == Debug.OK) {
					Util.purgeDirectory(Initializer.launcherPath.toFile());
					Initializer.touchFoldersAndFiles();
					updateUI();
				} else {
					return;
				}
				
			}
		});
		editMenu.add(cleanFoldersMenuItem);

		JMenu optionsMenu = new JMenu("Options");
		menuBar.add(optionsMenu);
		
		hideLauncherMenuItem = new JCheckBoxMenuItem("Hide Launcher");
		
		hideLauncherMenuItem.setSelected(true);
		optionsMenu.add(hideLauncherMenuItem);
		
		JMenu helpMenu = new JMenu("Help");
		menuBar.add(helpMenu);

		JMenuItem aboutMenuItem = new JMenuItem("About");
		aboutMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Debug.callCrashDialog("About", new AboutPanel(), Debug.TST);
			}
		});
		helpMenu.add(aboutMenuItem);

		JMenuItem wikiMenuItem = new JMenuItem("Wiki");
		wikiMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(Desktop.isDesktopSupported()) {
					try {
						Desktop.getDesktop().browse(new URL("https://github.com/MajickTek/MiniCraftLauncher/wiki").toURI());
					} catch (IOException | URISyntaxException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		helpMenu.add(wikiMenuItem);
		

		progressBar = new JTextArea();
		progressBar.setEditable(false);
		progressBar.setBackground(Color.BLACK);
		progressBar.setForeground(Color.GREEN);
		frmLauncher.getContentPane().add(progressBar, BorderLayout.NORTH);

		JScrollPane scrollPane = new JScrollPane();
		frmLauncher.getContentPane().add(scrollPane, BorderLayout.CENTER);

		DefaultMutableTreeNode empty = new DefaultMutableTreeNode("empty");
		empty.setUserObject(new EmptyObject());
		
		tree = new JTree(empty);
		
		tree.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {//double click
					int row = tree.getClosestRowForLocation(e.getX(), e.getY());
					tree.setSelectionRow(row);
					tree.updateUI();
					DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
					if (node == null)
						return;
					
					if(node.getUserObject() instanceof EmptyObject) {
						ChannelSelector cs = new ChannelSelector();
						cs.setVisible(true);
					} else {
						if(node.isLeaf() && (node.getUserObject() instanceof VersionObject)) {
							VersionObject vo = (VersionObject) node.getUserObject();
							vo.getLauncherWrapper().launch();
						}
						
					}
					
				}

				// right click
				if (SwingUtilities.isRightMouseButton(e)) {
					int row = tree.getClosestRowForLocation(e.getX(), e.getY());
					tree.setSelectionRow(row);
					tree.updateUI();
					DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
					if (node == null)
						return;

					
					if (node.isLeaf() && (!(node.getUserObject() instanceof EmptyObject) && node.getUserObject() instanceof VersionObject)) {
						VersionObject vo = (VersionObject) node.getUserObject();

						JPopupMenu menu = new JPopupMenu();
						JMenuItem editMenu = new JMenuItem("Edit");
						JMenuItem cleanMenu = new JMenuItem("Clean");
						JMenuItem saveFolderMenu = new JMenuItem("Open Save Folder");
						JMenuItem modFolderMenu = new JMenuItem("Open Mods Folder");
						JMenuItem MD5Menu = new JMenuItem("Get MD5 Hash");
						MD5Menu.setEnabled(vo.isDownloaded);

						editMenu.addActionListener(a -> {
							node.setUserObject(EditUtil.editInfo(vo));
							updateUI();
						});

						cleanMenu.addActionListener(a -> {
							Initializer.cleanVersion(vo.version);
							updateUI();
						});

						File jarPath = Paths.get(Initializer.savesDir.toString(), vo.version).toFile();
						saveFolderMenu.addActionListener(a -> {
							if (jarPath.exists()) {
								try {
									Util.openNative(jarPath);
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							} else {
								try {
									Util.openNative(Initializer.savesDir.toFile());// This should always be there
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
						});
						
						
						modFolderMenu.addActionListener(a -> {
							File folder = Paths.get(Initializer.savesDir.toString(), vo.version, "playminicraft", "mods").toFile();
							if(folder.exists()) {
								try {
									Util.openNative(folder);
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							} else {
								folder.mkdirs();
								try {
									Util.openNative(folder);
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
						});
						
						MD5Menu.addActionListener(a -> {
							if(vo.isDownloaded) {
								try {
									JTextField textField = new JTextField(GetMD5FromJar.getMD5Checksum(Paths.get(Initializer.jarPath.toString(), vo.version + ".jar").toString()));
									textField.setEditable(false);
									Debug.callCrashDialog("MD5 Hash", textField, Debug.INF);
								} catch (Exception e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
						});
						
						ButtonGroup runWithGroup = new ButtonGroup();
						//TODO: Make wrapper selection more dynamic
						JMenu runWithMenu = new JMenu("Run With");
						JRadioButtonMenuItem runVanilla = new JRadioButtonMenuItem("Vanilla / No Mods");
						runVanilla.setSelected((vo.launcherWrapper.getWrapper() instanceof VanillaWrap)? true: false);
						runVanilla.addActionListener(l -> {
							vo.getLauncherWrapper().setWrapper(new VanillaWrap());
						});
						JRadioButtonMenuItem runFabric = new JRadioButtonMenuItem("Fabric");
						runFabric.setSelected((vo.launcherWrapper.getWrapper() instanceof FabricWrap)? true: false);
						runFabric.addActionListener(l -> {
							vo.getLauncherWrapper().setWrapper(new FabricWrap());
						});
						runWithGroup.add(runVanilla);
						runWithGroup.add(runFabric);
						runWithMenu.add(runVanilla);
						runWithMenu.add(runFabric);
						
						JMenuItem changelogMenu = new JMenuItem("View Changelog");
						changelogMenu.addActionListener(a -> {
							ChangelogViewer cv = new ChangelogViewer();
							
							
							Path changelogPath = Paths.get(Initializer.jarPath.toString(), vo.version + "-changelog.txt");
							
							try {
								if(!changelogPath.toFile().exists()) {
									if(!(vo.changelogURL.isBlank() || vo.changelogURL.isEmpty())) {
										Util.downloadUsingNIO(vo.changelogURL, changelogPath.toString());
									}
								}
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							if(changelogPath.toFile().exists()) {
								try {
									List<String> lines = Files.readAllLines(changelogPath);
									lines.forEach(l -> {
										cv.getChangelog().append(String.format("%s%n", l));
									});
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							} else {
								cv.getChangelog().append("No changelog was found!\n");
							}
							
							cv.getDescription().setText(vo.description);
							cv.scrollTop();
							cv.setVisible(true);
						});
						
						menu.add(changelogMenu);
						menu.add(runWithMenu);
						menu.add(editMenu);
						menu.add(cleanMenu);
						menu.add(saveFolderMenu);
						menu.add(modFolderMenu);
						menu.add(MD5Menu);
						menu.show(tree, e.getPoint().x, e.getPoint().y);
					}

				}
			}

		});
		scrollPane.setViewportView(tree);
		
		JToolBar toolBar = new JToolBar();
		frmLauncher.getContentPane().add(toolBar, BorderLayout.SOUTH);
		
		JButton refreshButton = new JButton("Refresh");
		refreshButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateUI();
			}
		});
		toolBar.add(refreshButton);
		
		JToggleButton toggleConsoleButton = new JToggleButton("Toggle Console");
		toggleConsoleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				console.setVisible(!console.isVisible());
			}
		});
		toolBar.add(toggleConsoleButton);
		updateUI();
	}

	public JTree getTree() {
		return tree;
	}

	public JTextArea getProgressBar() {
		return progressBar;
	}

	public void updateUI() {
		DefaultTreeModel dtm = (DefaultTreeModel) tree.getModel();
		walk(dtm, (DefaultMutableTreeNode) dtm.getRoot());
		tree.setModel(dtm);
		tree.updateUI();
	}

	private void walk(DefaultTreeModel dtm, DefaultMutableTreeNode root) {
		int cc;
		cc = dtm.getChildCount(root);
		String basePath = Initializer.jarPath.toString();
		for (int i = 0; i < cc; i++) {
			DefaultMutableTreeNode child = (DefaultMutableTreeNode) dtm.getChild(root, i);
			if (dtm.isLeaf(child) && (child.getUserObject() instanceof VersionObject)) {
				VersionObject vo = (VersionObject) child.getUserObject();
				String base = Paths.get(basePath, vo.version + ".jar").toString();
				File file = new File(base);
				if (file.exists()) {
					vo.isDownloaded = true;
				} else {
					vo.isDownloaded = false;
				}
			} else {
				walk(dtm, child);
			}
		}
	}
	
	

	public JCheckBoxMenuItem getHideLauncherMenuItem() {
		return hideLauncherMenuItem;
	}
}
