package com.mt.minilauncher;

import java.io.File;
import java.io.IOException;

import javax.xml.*;
import javax.xml.parsers.*;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class TestXML {
	private static final String FILENAME = "/reference.xml";
	
	public static void main(String[] args) {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		
		try {
			dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
			
			DocumentBuilder db = dbf.newDocumentBuilder();
			
			Document doc = db.parse(new File(TestXML.class.getResource(FILENAME).toURI()));
			
			doc.getDocumentElement().normalize();
			
			System.out.println(doc.getDocumentElement().getNodeName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
