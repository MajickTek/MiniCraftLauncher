package com.mt.minilauncher.util;

import java.io.File;
import java.io.IOException;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.mt.minilauncher.objects.VersionObject;

public class XMLConverter {
	
	public static DefaultMutableTreeNode fromXML(String filePath) throws ParserConfigurationException, SAXException, IOException {
		DefaultMutableTreeNode rootTreeNode = new DefaultMutableTreeNode("Games");
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		
		dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
		DocumentBuilder db = dbf.newDocumentBuilder();
		
		Document doc;
		
		doc = db.parse(new File(filePath));
		
		
		doc.getDocumentElement().normalize();
		
		NodeList gameList = doc.getElementsByTagName("game");
		
		for (int i = 0; i < gameList.getLength(); i++) {
			Node gameNode = gameList.item(i);
			if(gameNode.getNodeType() == Node.ELEMENT_NODE) {
				Element gameElementNode = (Element) gameNode;
				String gameName = gameElementNode.getAttribute("name");
				DefaultMutableTreeNode gameTreeNode = new DefaultMutableTreeNode(gameName);
				rootTreeNode.add(gameTreeNode);
				NodeList versionList = gameElementNode.getElementsByTagName("version");
				for (int j = 0; j < versionList.getLength(); j++) {
					Node versionNode = versionList.item(j);
					if(versionNode.getNodeType() == Node.ELEMENT_NODE) {
						Element versionElementNode = (Element) versionNode;
						String versionNumber = versionElementNode.getAttribute("number");
						String versionDownloadURL = (versionElementNode.getTextContent().startsWith("http://") || versionElementNode.getTextContent().startsWith("https://")) ? versionElementNode.getTextContent() : "";
						VersionObject vo = new VersionObject(versionDownloadURL, versionNumber);
						DefaultMutableTreeNode versionTreeNode = new DefaultMutableTreeNode(vo.toString());
						versionTreeNode.setUserObject(vo);
						gameTreeNode.add(versionTreeNode);
					}
				}
			}
		}
		
		return rootTreeNode;
	}
}
