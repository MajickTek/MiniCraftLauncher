package com.mt.minilauncher.windows;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.mt.mclupdater.AssetObject;
import com.mt.mclupdater.ReleaseObject;
import com.mt.mclupdater.util.GithubReleaseParser;
import com.mt.minilauncher.Initializer;
import com.mt.minilauncher.LauncherWindow;
import com.mt.minilauncher.objects.ChannelObject;
import com.mt.minilauncher.objects.VersionObject;
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
		okButton.addActionListener(l -> {
			try {
				if(this.getList().getSelectedValue().isLive()) {
					DefaultMutableTreeNode root = new DefaultMutableTreeNode("MiniCraft+");
					DefaultTreeModel dtm = new DefaultTreeModel(root);
					DefaultMutableTreeNode releaseNode = new DefaultMutableTreeNode("Releases");
					DefaultMutableTreeNode preReleaseNode = new DefaultMutableTreeNode("Pre-Releases");
					
					ArrayList<ReleaseObject> releaseTree = GithubReleaseParser.parseReleases(this.getList().getSelectedValue().liveUsername, this.getList().getSelectedValue().liveRepoName);
					releaseTree.stream().filter(r -> (r.isPrerelease() == false)).forEach(ro -> {
						VersionObject tmp = new VersionObject();
						AssetObject jar = ro.getAsset("minicraft.jar");
						AssetObject changelog = ro.getAsset("changelog.txt");
						if(jar != null && changelog != null) {
							tmp.url = jar.getUrl();
							tmp.changelogURL = changelog.getUrl();
						}
						tmp.description = ro.getDescription();
						tmp.version = ro.getTagName();
						tmp.canEdit = false;
						DefaultMutableTreeNode tmpNode = new DefaultMutableTreeNode();
						tmpNode.setUserObject(tmp);
						releaseNode.add(tmpNode);
					});
					
					releaseTree.stream().filter(r -> (r.isPrerelease() == true)).forEach(ro -> {
						VersionObject tmp = new VersionObject();
						AssetObject jar = ro.getAsset("minicraft.jar");
						AssetObject changelog = ro.getAsset("changelog.txt");
						if(jar != null && changelog != null) {
							tmp.url = jar.getUrl();
							tmp.changelogURL = changelog.getUrl();
						}
						tmp.description = ro.getDescription();
						tmp.version = ro.getTagName();
						tmp.canEdit = false;
						DefaultMutableTreeNode tmpNode = new DefaultMutableTreeNode();
						tmpNode.setUserObject(tmp);
						preReleaseNode.add(tmpNode);
					});
					root.add(releaseNode);
					root.add(preReleaseNode);
					
					
					LauncherWindow.instance.getTree().setModel(dtm);
					LauncherWindow.instance.getTree().updateUI();
					LauncherWindow.instance.updateUI();
				} else {
					Path filePath = Paths.get(Initializer.indexPath.toString(), this.getList().getSelectedValue().target);
					Util.downloadUsingNIO(this.getList().getSelectedValue().channelFile, filePath.toString());
					DefaultTreeModel dtm = new DefaultTreeModel(XMLConverter.fromXML(filePath.toString()));
					LauncherWindow.instance.getTree().setModel(dtm);
					LauncherWindow.instance.getTree().updateUI();
					LauncherWindow.instance.updateUI();
				}
				
			} catch (IOException | ParserConfigurationException | SAXException e1) {
				e1.printStackTrace();
			}
			this.dispose();
		});
		cancelButton.addActionListener(l -> {
			this.dispose();
		});
		init();
	}

	private void init() {
		try {
			Util.downloadUsingNIO("https://github.com/MajickTek/MiniCraftLauncherIndex/raw/main/index.xml",
					Paths.get(Initializer.indexPath.toString(), "index.xml").toString());
			buildList();
		} catch (IOException | ParserConfigurationException | SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void buildList() throws ParserConfigurationException, SAXException, IOException {
		Path indexPath = Paths.get(Initializer.indexPath.toString(), "index.xml");
		DefaultListModel<ChannelObject> model = new DefaultListModel<>();

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(indexPath.toFile());

		doc.getDocumentElement().normalize();
		ArrayList<ChannelObject> tempList = new ArrayList<>();

		NodeList indexList = doc.getElementsByTagName("index");
		for (int i = 0; i < indexList.getLength(); i++) {
			Node indexNode = indexList.item(i);
			if (indexNode.getNodeType() == Node.ELEMENT_NODE) {
				Element indexElement = (Element) indexNode;
				String indexName = indexElement.getAttribute("name");
				NodeList urlList = indexElement.getElementsByTagName("url");
				Node urlNode = urlList.item(0);
				if (urlNode.getNodeType() == Node.ELEMENT_NODE) {
					Element urlElement = (Element) urlNode;
					String target = urlElement.getAttribute("target");
					String url = (urlElement.getTextContent().startsWith("http://")
							|| urlElement.getTextContent().startsWith("https://")) ? urlElement.getTextContent() : "";
					ChannelObject co = new ChannelObject();
					co.channelFile = url;
					co.channelName = indexName;
					co.target = target;
					tempList.add(co);
				}
			}
		}

		ChannelObject[] objects = new ChannelObject[tempList.size()];
		objects = tempList.toArray(objects);

		for (int i = 0; i < objects.length; i++) {
			model.add(i, objects[i]);
		}
		ChannelObject liveObject = new ChannelObject();
		liveObject.setLive(true);
		
		liveObject.setLiveUsername("MinicraftPlus");
		liveObject.setLiveRepoName("minicraft-plus-revived");
		model.add(0, liveObject);//Pushed to the beginning of the list
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
