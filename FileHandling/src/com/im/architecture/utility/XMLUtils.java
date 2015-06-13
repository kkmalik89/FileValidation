package com.im.architecture.utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class XMLUtils {
public Document getDocument(String docString)  {
	try{
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
		factory.setIgnoringComments(true);
		factory.setIgnoringElementContentWhitespace(true);			
		factory.setValidating(true);
		DocumentBuilder builder = factory.newDocumentBuilder();
		return builder.parse(new InputSource(docString));			
	}
	catch(Exception ex){
		ex.printStackTrace();
		throw new RuntimeException("Unable to parse the XML. Please provide a valid XML.");
	}
}
}
