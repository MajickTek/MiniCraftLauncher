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
			
			NodeList list = doc.getElementsByTagName("version");
			
			for(int tmp = 0; tmp < list.getLength(); tmp++) {
				Node node = list.item(tmp);
				if(node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
					String number = element.getAttribute("number");
					String url = element.getTextContent();
					System.out.println(number + " - " + url);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
