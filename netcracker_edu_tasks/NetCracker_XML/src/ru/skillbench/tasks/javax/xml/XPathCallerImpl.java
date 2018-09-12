package ru.skillbench.tasks.javax.xml;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XPathCallerImpl implements XPathCaller {
	
	private Element[] toArray(NodeList nl) {
		Element[] result = new Element[nl.getLength()];
		for (int i = 0; i < nl.getLength(); i++) {
			result[i] = (Element) nl.item(i);
		}
		return result;
	}

	@Override
	public Element[] getEmployees(Document src, String deptno, String docType) {
		XPath xPath =  XPathFactory.newInstance().newXPath();
		String expression = "//employee[@deptno='" + deptno + "']";
		NodeList nodeList = null;
		try {
			nodeList = (NodeList) xPath.compile(expression).evaluate(src, XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this.toArray(nodeList);
	}

	@Override
	public String getHighestPayed(Document src, String docType) {
		XPath xPath =  XPathFactory.newInstance().newXPath();
		String expression = null;
		expression = "//employee/ename/text()[not(../../sal/text()<//employee/sal/text())]";
		try {
			return (String) xPath.compile(expression).evaluate(src, XPathConstants.STRING);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getHighestPayed(Document src, String deptno, String docType) {
		XPath xPath =  XPathFactory.newInstance().newXPath();
		String expression = null;
		expression = "//employee[@deptno='" + deptno + "']/ename/text()[not(../../sal/text()<//employee[@deptno='" + deptno + "']/sal/text())]";
		try {
			return (String) xPath.compile(expression).evaluate(src, XPathConstants.STRING);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Element[] getTopManagement(Document src, String docType) {
		XPath xPath =  XPathFactory.newInstance().newXPath();
		String expression = null;
		if (docType == "emp") {
			expression = "//employee[not(@mgr)]";
		} else {
			expression = "./employee";
		}
		NodeList nodeList = null;
		try {
			nodeList = (NodeList) xPath.compile(expression).evaluate(src, XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this.toArray(nodeList);
	}

	@Override
	public Element[] getOrdinaryEmployees(Document src, String docType) {
		XPath xPath =  XPathFactory.newInstance().newXPath();
		String expression = null;
		if (docType == "emp") {
			expression = "//employee[not(@empno=//employee/@mgr)]";
		} else {
			expression = "//employee[not(employee)]";
		}
		NodeList nodeList = null;
		try {
			nodeList = (NodeList) xPath.compile(expression).evaluate(src, XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this.toArray(nodeList);
	}

	@Override
	public Element[] getCoworkers(Document src, String empno, String docType) {
		XPath xPath =  XPathFactory.newInstance().newXPath();
		String expression = null;
		if (docType == "emp") {
			expression = "//employee[@mgr=//employee[@empno='" + empno + "']/@mgr][not(@empno='" + empno + "')]";
		} else {
			expression = "//employee[(preceding-sibling::employee | following-sibling::employee)/@empno='" + empno + "']";
		}
		NodeList nodeList = null;
		try {
			nodeList = (NodeList) xPath.compile(expression).evaluate(src, XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this.toArray(nodeList);
	}

}
