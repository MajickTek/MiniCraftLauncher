package com.mt.minilauncher;

import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JList;
import java.awt.BorderLayout;
import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.JMenu;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LauncherWindow {

	private JFrame frmLauncher;
	private JList<VersionObject> list;

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
		initialize();
		try { // enable native UI
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e){
            Debug.callCrashDialog("ERROR", "Couldn't access native UI class for some reason.\nCheck the console output.", Debug.ERR);
            e.printStackTrace();
        }
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmLauncher = new JFrame();
		frmLauncher.setTitle("Launcher");
		frmLauncher.setBounds(100, 100, 800, 600);
		frmLauncher.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmLauncher.setIconImage(new ImageIcon(LauncherWindow.class.getResource("/cube.png")).getImage());
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
		
		JScrollPane scrollPane = new JScrollPane();
		frmLauncher.getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		list = new JList<>();
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2) {
					VersionObject selectedVersion = getList().getSelectedValue();
					if(selectedVersion != null) {
						if(selectedVersion.isDownloaded) {
							String jarPath = Paths.get(Util.launcherPath.toString(), "versions", selectedVersion.version + ".jar").toString();
							try {
								Util.launchJar(jarPath, selectedVersion.version);
							} catch (IOException e1) {
								Debug.callCrashDialog("ERROR", "Something went wrong launching the jar.\nPlease check the console output.", Debug.ERR);
								e1.printStackTrace();
							}
						} else {
							try {
								Util.downloadUsingNIO(selectedVersion.getURL(), Paths.get(Util.launcherPath.toString(), "versions", selectedVersion.version + ".jar").toString());
								selectedVersion.isDownloaded = true;
								list.updateUI();
							} catch (IOException e1) {
								Debug.callCrashDialog("ERROR", "Failed downloading the jar.\nCheck console output.", Debug.ERR);
								e1.printStackTrace();
							}
						}
					}
				}
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				if(SwingUtilities.isRightMouseButton(e)) {
					if(list.getSelectedValue() != null) {
						Debug.callCrashDialog("Test", list.getSelectedValue().toVerboseString(), Debug.INF);
					}
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

	public JList<VersionObject> getList() {
		return list;
	}
	
	public void updateUI() {
		DefaultListModel<VersionObject> dlm = (DefaultListModel<VersionObject>) getList().getModel();
		String basePath = Paths.get(Util.launcherPath.toString(),"versions").toString();
		
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
	
	
}
