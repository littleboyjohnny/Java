package ru.skillbench.tasks.javax.xml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class Checker {
	
	private static String elementToString(Element el) throws TransformerException {
		StringWriter sw = new StringWriter();
		Transformer tr = TransformerFactory.newInstance().newTransformer();
		tr.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		tr.transform(new DOMSource(el), new StreamResult(sw));
		return sw.toString();
	}

	public static void main(String[] args) throws TransformerFactoryConfigurationError, TransformerException, SAXException, IOException, ParserConfigurationException {
		/*
		SimpleXML s = new SimpleXMLImpl();
		try {
			System.out.println(s.parseRootElement(new FileInputStream("emp.xml")));
			System.out.println("Done");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			System.out.println("Incorrect");
			e.printStackTrace();
		}
		*/
		DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
		domFactory.setNamespaceAware(false); 
		DocumentBuilder builder = domFactory.newDocumentBuilder();
		Document emp = builder.parse("emp.xml");
		Document emphier = builder.parse("emp-hier.xml");
		
		XPathCaller c = new XPathCallerImpl();
		///*
		Element[] res = c.getTopManagement(emphier, "emp-h");
		for (int i = 0; i < res.length; i++) {
			System.out.println(elementToString(res[i]));
		}
		//*/
		//System.out.println(c.getHighestPayed(emp, "30", "emp"));
	}

}
