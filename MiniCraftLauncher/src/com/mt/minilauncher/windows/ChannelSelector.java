package com.mt.minilauncher.windows;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.mt.minilauncher.ChannelObject;
import com.mt.minilauncher.Initializer;
import com.mt.minilauncher.VersionObject;
import com.mt.minilauncher.downloader.Downloader;
import com.mt.minilauncher.util.OrderedProperties;
import com.mt.minilauncher.util.Util;
import com.mt.minilauncher.util.XMLConverter;

import javax.swing.JScrollPane;
import javax.swing.JList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ChannelSelector extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JList<ChannelObject> list;
	private JButton okButton;
	private JButton cancelButton;
	private JButton refreshListBtn;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ChannelSelector dialog = new ChannelSelector();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ChannelSelector() {
		setTitle("Channel Selector");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JScrollPane scrollPane = new JScrollPane();
			contentPanel.add(scrollPane, BorderLayout.CENTER);
			{
				list = new JList<ChannelObject>();
				scrollPane.setViewportView(list);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				refreshListBtn = new JButton("Refresh List");
				refreshListBtn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						list.updateUI();
					}
				});
				buttonPane.add(refreshListBtn);
			}
			{
				okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		init();
	}

	private void init() {
		try {
			Util.downloadUsingNIO("https://github.com/MajickTek/MiniCraftLauncherIndex/raw/main/index.txt", Paths.get(Initializer.indexPath.toString(), "index.txt").toString());
			buildList();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	private void buildList() {
		Path indexPath = Paths.get(Initializer.indexPath.toString(), "index.txt");
        DefaultListModel<ChannelObject> model = new DefaultListModel<>();
		
        OrderedProperties op = new OrderedProperties();
        try {
			op.load(new FileInputStream(indexPath.toString()));
			op.entrySet().forEach(l -> {
				model.add(Integer.parseInt(l.getKey().toString()), new ChannelObject(l.getValue().toString()));
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		list.setModel(model);
		list.updateUI();
	}

	public JList<ChannelObject> getList() {
		return list;
	}
	public JButton getOkButton() {
		return okButton;
	}
	public JButton getCancelButton() {
		return cancelButton;
	}
}
