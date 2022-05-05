package com.mt.minilauncher.windows;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.mt.minilauncher.Initializer;
import com.mt.minilauncher.objects.ChannelObject;
import com.mt.minilauncher.util.OrderedProperties;
import com.mt.minilauncher.util.Util;
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

//        OrderedProperties op = new OrderedProperties();
//        try {
//			op.load(new FileInputStream(indexPath.toString()));
//			op.entrySet().forEach(l -> {
//				model.add(Integer.parseInt(l.getKey().toString()), new ChannelObject(l.getValue().toString()));
//			});
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(indexPath.toFile());
		
		doc.getDocumentElement().normalize();
		ArrayList<ChannelObject> tempList = new ArrayList<>();
		
		NodeList indexList = doc.getElementsByTagName("index");
		for(int i = 0; i < indexList.getLength(); i++) {
			Node indexNode = indexList.item(i);
			if(indexNode.getNodeType() == Node.ELEMENT_NODE) {
				Element indexElement = (Element) indexNode;
				String indexName = indexElement.getAttribute("name");
				NodeList urlList = indexElement.getElementsByTagName("url");
				Node urlNode = urlList.item(0);
				if(urlNode.getNodeType() == Node.ELEMENT_NODE) {
					Element urlElement = (Element) urlNode;
					String target = urlElement.getAttribute("target");
					String url = (urlElement.getTextContent().startsWith("http://") || urlElement.getTextContent().startsWith("https://")) ? urlElement.getTextContent() : "";
					tempList.add(new ChannelObject(url, indexName));
				}
			}
		}
		
		ChannelObject[] objects = new ChannelObject[tempList.size()];
		objects = tempList.toArray(objects);
		
		for(int i = 0; i < objects.length; i++) {
			model.add(i, objects[i]);
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
