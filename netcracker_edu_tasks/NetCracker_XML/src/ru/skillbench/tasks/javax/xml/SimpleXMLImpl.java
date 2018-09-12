package ru.skillbench.tasks.javax.xml;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.*;
import org.xml.sax.helpers.*;

public class SimpleXMLImpl implements SimpleXML {
	
	String root = null;

	@Override
	public String createXML(String tagName, String textNode) {
		Document doc = null;
		try {
		      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		      DocumentBuilder db = dbf.newDocumentBuilder();
		      doc = db.newDocument();
		} catch (Exception e) {
		      System.out.print("Problem parsing the file: "+e.getMessage());
		}
		Element rootElement = doc.createElement(tagName);
		rootElement.appendChild(doc.createTextNode(textNode));
		doc.appendChild(rootElement);
		
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = null;
		try {
			transformer = transformerFactory.newTransformer();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		}
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		Writer out = new StringWriter();
        try {
			transformer.transform(new DOMSource(doc), new StreamResult(out));
		} catch (TransformerException e) {
			e.printStackTrace();
		}
        return out.toString();
	}

	@Override
	public String parseRootElement(InputStream xmlStream) throws SAXException {
		
		SAXParserFactory spf = SAXParserFactory.newInstance();
	    spf.setNamespaceAware(false);
	    SAXParser saxParser = null;
	    try {
			saxParser = spf.newSAXParser();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	    XMLReader xmlReader = saxParser.getXMLReader();
	    DefaultHandler dh = new DefaultHandler() {
			private boolean flagFirst = true;
			//public String root = null; 
			
			public void startElement(String namespaceURI,
                    String localName,
                    String qName, 
                    Attributes atts)
                    throws SAXException {
				if (flagFirst) {
					SimpleXMLImpl.this.root = qName;
					flagFirst = false;
				}
			}
		};
	    xmlReader.setContentHandler(dh);
	    try {
			xmlReader.parse(new InputSource(xmlStream));
		} catch (IOException e) {
			e.printStackTrace();
		}; 
		return root;
	}

}
