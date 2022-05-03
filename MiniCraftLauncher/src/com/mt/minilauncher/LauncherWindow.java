package com.mt.minilauncher;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.mt.minilauncher.launchwrap.FabricWrap;
import com.mt.minilauncher.launchwrap.LauncherWrapper;
import com.mt.minilauncher.launchwrap.VanillaWrap;
import com.mt.minilauncher.objects.VersionObject;
import com.mt.minilauncher.util.EditUtil;
import com.mt.minilauncher.util.Util;
import com.mt.minilauncher.util.XMLConverter;
import com.mt.minilauncher.windows.AboutPanel;
import com.mt.minilauncher.windows.ChannelSelector;
import com.mt.minilauncher.windows.SystemInfo;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.awt.event.ActionEvent;
import javax.swing.JToolBar;
import javax.swing.JButton;
import java.awt.Toolkit;

public class LauncherWindow {

	public static LauncherWindow instance;
	public JFrame frmLauncher;
	private JCheckBoxMenuItem hideLauncherDuringPlayCheckBox;
	private JTree tree;
	private JTextArea console;
	private LauncherWrapper launcherWrapper;
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
		launcherWrapper = new LauncherWrapper(new VanillaWrap(), instance);//VanillaWrap is default
		frmLauncher = new JFrame();
		frmLauncher.setTitle("Launcher");
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
				cs.getOkButton().addActionListener(l -> {
					try {
						Path filePath = Paths.get(Initializer.indexPath.toString(),
								String.format("index%d.xml", cs.getList().getSelectedIndex()));
						Util.downloadUsingNIO(cs.getList().getSelectedValue().channelFile, filePath.toString());
						DefaultTreeModel dtm = new DefaultTreeModel(XMLConverter.fromXML(filePath.toString()));
						tree.setModel(dtm);
						tree.updateUI();
						updateUI();
					} catch (IOException | ParserConfigurationException | SAXException e1) {
						e1.printStackTrace();
					}
					cs.dispose();
				});
				cs.getCancelButton().addActionListener(l -> {
					cs.dispose();
				});
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
				Util.purgeDirectoryButKeepSubDirectories(Initializer.launcherPath.toFile());
			}
		});
		editMenu.add(cleanFoldersMenuItem);

		JMenu optionsMenu = new JMenu("Options");
		menuBar.add(optionsMenu);
		
		ButtonGroup launcherGroup = new ButtonGroup();
		
		JMenu selectLWMenu = new JMenu("Select LauncherWrapper");
		optionsMenu.add(selectLWMenu);
		
		JRadioButtonMenuItem vanillaLauncherMenuItem = new JRadioButtonMenuItem("Vanilla / No Mods");
		selectLWMenu.add(vanillaLauncherMenuItem);
		vanillaLauncherMenuItem.setSelected(true);
		vanillaLauncherMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				launcherWrapper.setWrapper(new VanillaWrap());
			}
		});
		launcherGroup.add(vanillaLauncherMenuItem);
		
		JRadioButtonMenuItem fabricLauncherMenuItem = new JRadioButtonMenuItem("Fabric");
		selectLWMenu.add(fabricLauncherMenuItem);
		fabricLauncherMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				launcherWrapper.setWrapper(new FabricWrap());
			}
		});
		launcherGroup.add(fabricLauncherMenuItem);
		
		hideLauncherDuringPlayCheckBox = new JCheckBoxMenuItem("Hide Launcher During Play");
		hideLauncherDuringPlayCheckBox.setSelected(true);
		//optionsMenu.add(hideLauncherDuringPlayCheckBox);

		JMenu helpMenu = new JMenu("Help");
		menuBar.add(helpMenu);

		JMenuItem aboutMenuItem = new JMenuItem("About");
		aboutMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Debug.callCrashDialog("About", new AboutPanel(), Debug.TST);
			}
		});
		helpMenu.add(aboutMenuItem);

		JMenuItem referenceMenuItem = new JMenuItem("Reference");
		referenceMenuItem.addActionListener(new ActionListener() {
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
		helpMenu.add(referenceMenuItem);

		JMenuItem systemInfoMenuItem = new JMenuItem("System Info");
		systemInfoMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SystemInfo si = new SystemInfo();
				si.setVisible(true);
			}
		});
		helpMenu.add(systemInfoMenuItem);

		console = new JTextArea();
		console.setEditable(false);
		console.setBackground(Color.BLACK);
		console.setForeground(Color.GREEN);
		frmLauncher.getContentPane().add(console, BorderLayout.NORTH);

		JScrollPane scrollPane = new JScrollPane();
		frmLauncher.getContentPane().add(scrollPane, BorderLayout.CENTER);

		tree = new JTree(new DefaultMutableTreeNode("empty"));

		tree.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {//double click
					launcherWrapper.launch();
				}

				// right click
				if (SwingUtilities.isRightMouseButton(e)) {
					int row = tree.getClosestRowForLocation(e.getX(), e.getY());
					tree.setSelectionRow(row);
					tree.updateUI();
					DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
					if (node == null)
						return;

					if (node.isLeaf() && !node.toString().equals("empty")) {
						VersionObject vo = (VersionObject) node.getUserObject();

						JPopupMenu menu = new JPopupMenu();
						JMenuItem editMenu = new JMenuItem("Edit");
						JMenuItem cleanMenu = new JMenuItem("Clean");
						JMenuItem saveFolderMenu = new JMenuItem("Open Save Folder");
						JMenuItem modFolderMenu = new JMenuItem("Open Mods Folder");
						JMenuItem getMD5Menu = new JMenuItem("Get MD5 Hash");
						

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
							File folder = Paths.get(Initializer.launcherPath.toString(), "mods").toFile();
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
						
						getMD5Menu.addActionListener(a -> {
							String checksum = "";
							try {
								if(vo.isDownloaded) {
									//TODO: Decouple from GameProvider
									checksum = com.mt.minigameprovider.services.GetMD5FromJar.getMD5Checksum(Paths.get(Initializer.jarPath.toString(), vo.version + ".jar").toString());
								} else {
									checksum = "";
								}
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							
							if(!checksum.isEmpty()) {
								Debug.callCrashDialog("MD5 Checksum", new javax.swing.JTextField(checksum), Debug.INF);
							}
						});
						menu.add(editMenu);
						menu.add(cleanMenu);
						menu.add(saveFolderMenu);
						menu.add(modFolderMenu);
						menu.add(getMD5Menu);
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
		updateUI();
	}

	public JCheckBoxMenuItem getHideLauncherDuringPlayCheckBox() {
		return hideLauncherDuringPlayCheckBox;
	}

	public JTree getTree() {
		return tree;
	}

	public JTextArea getConsole() {
		return console;
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
			if (dtm.isLeaf(child)) {
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
	
	

}
