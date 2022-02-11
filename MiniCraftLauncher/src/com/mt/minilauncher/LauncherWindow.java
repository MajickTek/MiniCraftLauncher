package com.mt.minilauncher;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.UIManager;

import com.mt.minilauncher.downloader.Downloader;

import javax.swing.JMenuBar;
import javax.swing.JPopupMenu;
import javax.swing.JList;
import java.awt.BorderLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.JScrollPane;
import javax.swing.JMenu;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class LauncherWindow {

	private JFrame frmLauncher;
	private JList<VersionObject> list;
public static LauncherWindow instance;
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
					Debug.callCrashDialog("ERROR","The app failed to launch for some reason.\nCheck the console output.",Debug.ERR);
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LauncherWindow() {
		try { // enable native UI
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e){
            Debug.callCrashDialog("ERROR", "Couldn't access native UI class for some reason.\nCheck the console output.", Debug.ERR);
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
		frmLauncher = new JFrame();
		frmLauncher.setTitle("Launcher");
		frmLauncher.setBounds(100, 100, 800, 600);
		frmLauncher.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmLauncher.setIconImage(new ImageIcon(LauncherWindow.class.getResource("/logo.png")).getImage());
		JMenuBar menuBar = new JMenuBar();
		frmLauncher.setJMenuBar(menuBar);
		
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		
		JMenuItem exitMenuItem = new JMenuItem("Exit");
		exitMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		fileMenu.add(exitMenuItem);
		
		JMenu editMenu = new JMenu("Edit");
		menuBar.add(editMenu);
		
		JMenu channelMenu = new JMenu("Select Channel");
		channelMenu.setName("editMenu.channelMenu");
		editMenu.add(channelMenu);
		
		JMenu miniplusmenu = new JMenu("Minicraft+");
		channelMenu.add(miniplusmenu);
		
		JMenuItem releasesMenuItem = new JMenuItem("Release");
		releasesMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getList().setModel(Util.buildIndex(true, false));
				getList().updateUI();
				updateUI();
			}
		});
		miniplusmenu.add(releasesMenuItem);
		
		JMenuItem devMenuItem = new JMenuItem("Pre-Release/dev");
		devMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getList().setModel(Util.buildIndex(false, false));
				getList().updateUI();
				updateUI();
			}
		});
		miniplusmenu.add(devMenuItem);
		
		JMenuItem modsMenuItem = new JMenuItem("Mods");
		modsMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Debug.callCrashDialog("Warning!", "The mods in this section are not fully supported.\nThey may contain hardcoded save/config paths or may not work at all.\nThey are here for funsies.", Debug.WARN);
				getList().setModel(Util.buildIndex(false, true));
				getList().updateUI();
				updateUI();
			}
		});
		channelMenu.add(modsMenuItem);
		
		
		
		JMenuItem insertVersionMenuItem = new JMenuItem("Insert Version Profile");
		insertVersionMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int baseSize = getList().getModel().getSize();
				getList().setModel(Util.addToJList(getList().getModel(), new VersionObject(), baseSize));
				getList().updateUI();
				getList().setSelectedIndex(baseSize);
				getList().updateUI();
				EditUtil.editInfo(getList().getSelectedValue());
				getList().updateUI();
			}
		});
		
		JMenuItem cleanIndexButton = new JMenuItem("Clean Index");
		cleanIndexButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Util.purgeDirectory(Initializer.indexPath.toFile());
				list.setModel(new DefaultListModel<VersionObject>());
				updateUI();
			}
		});
		editMenu.add(cleanIndexButton);
		editMenu.add(insertVersionMenuItem);
		
		JMenuItem refreshUIMenuItem = new JMenuItem("Refresh UI");
		refreshUIMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateUI();
			}
		});
		editMenu.add(refreshUIMenuItem);
		
		JMenuItem cleanFolderMenuItem = new JMenuItem("Clean Folders");
		cleanFolderMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Initializer.cleanFolders();
			}
		});
		editMenu.add(cleanFolderMenuItem);
		
		JScrollPane scrollPane = new JScrollPane();
		frmLauncher.getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		list = new JList<>();
		list.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_DELETE) {
					clean();
				} else if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					edit();
				}
			}
		});
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2) {
					VersionObject selectedVersion = getList().getSelectedValue();
					if(selectedVersion != null) {
						if(selectedVersion.isDownloaded) {
							String jarPath = Paths.get(Initializer.jarPath.toString(), selectedVersion.version + ".jar").toString();
							try {
								Util.launchJar(jarPath, selectedVersion.version, instance);
							} catch (IOException e1) {
								Debug.callCrashDialog("ERROR", "Something went wrong launching the jar.\nPlease check the console output.", Debug.ERR);
								e1.printStackTrace();
							}
						} else {

//							DownloadDialog dd = new DownloadDialog();
//							dd.download(selectedVersion.getURL(), Paths.get(Initializer.jarPath.toString(), selectedVersion.version + ".jar").toString());
//							dd.close();
							Downloader downloader = new Downloader(selectedVersion.getURL(), Paths.get(Initializer.jarPath.toString(), selectedVersion.version + ".jar").toString());
							downloader.download();
							selectedVersion.isDownloaded = true;
							list.updateUI();
						}
					}
				}
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				if(SwingUtilities.isRightMouseButton(e)) {
						list.setSelectedIndex(list.locationToIndex(e.getPoint()));
						
						JPopupMenu menu = new JPopupMenu();
						JMenuItem editMenu = new JMenuItem("Edit");
						JMenuItem cleanMenu = new JMenuItem("Clean");
						
						editMenu.addActionListener(a -> edit());
						
						cleanMenu.addActionListener(a -> clean());
						
						menu.add(editMenu);
						menu.add(cleanMenu);
						menu.show(list, e.getPoint().x, e.getPoint().y);
				}
			}
		});
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setSelectedIndex(0);
		scrollPane.setViewportView(list);
		
		list.setModel(Util.buildIndex(true, false));
		list.updateUI();
		updateUI();
	}

	public void clean() {
		Initializer.cleanVersion(list.getSelectedValue().version);
		forceReDownload(list.getSelectedIndex());
		list.updateUI();
		updateUI();
	}

	public void edit() {
		list.setModel(Util.addToJList(list.getModel(), EditUtil.editInfo(list.getSelectedValue()), list.getSelectedIndex()));
		list.updateUI();
	}

	public JList<VersionObject> getList() {
		return list;
	}
	
	public void updateUI() {
		DefaultListModel<VersionObject> dlm = (DefaultListModel<VersionObject>) getList().getModel();
		String basePath = Initializer.jarPath.toString();
		
		for(int i = 0; i < dlm.getSize(); i++) {
			String base = Paths.get(basePath, dlm.getElementAt(i).version + ".jar").toString();
			File file = new File(base);
			if(file.exists()) {
				dlm.get(i).isDownloaded = true;
			}
		}
		getList().setModel(dlm);
		getList().updateUI();
	}
	
	public void forceReDownload() {
		DefaultListModel<VersionObject> dlm = (DefaultListModel<VersionObject>) getList().getModel();
		
		for(int i = 0; i < dlm.getSize(); i++) {
				dlm.get(i).isDownloaded = false;
		}
		getList().setModel(dlm);
		getList().updateUI();
	}
	
	public void forceReDownload(int index) {
		DefaultListModel<VersionObject> dlm = (DefaultListModel<VersionObject>) getList().getModel();
		
		dlm.get(index).isDownloaded = false;
		getList().setModel(dlm);
		getList().updateUI();
	}
	
	
	public JFrame getFrmLauncher() {
		return frmLauncher;
	}
}
