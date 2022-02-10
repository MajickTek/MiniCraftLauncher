package com.mt.minilauncher;

import java.io.File;
import javax.xml.*;
import javax.xml.parsers.*;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class TestXML {
	private static final String FILENAME = "/reference.xml";
	
	public static void main(String[] args) {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		
		try {
			dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
			
			DocumentBuilder db = dbf.newDocumentBuilder();
			
			Document doc = db.parse(new File(TestXML.class.getResource(FILENAME).toURI()));
			
			doc.getDocumentElement().normalize();
			
			System.out.println("root node - " + doc.getDocumentElement().getNodeName());
			
			NodeList gameList = doc.getElementsByTagName("game");
			
			for(int tmp = 0; tmp < gameList.getLength(); tmp++) {
				Node node = gameList.item(tmp);
				if(node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
					String name = element.getAttribute("name");
					System.out.println("name: " + name + "\nVersions:");
					
					NodeList versionList = element.getElementsByTagName("version");
					for(int tmp2 = 0; tmp2 < versionList.getLength(); tmp2++) {
						Node n = versionList.item(tmp2);
						Element nel = (Element) n;
						System.out.println("Number: " + nel.getAttribute("number") + " URL: " + nel.getTextContent());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
