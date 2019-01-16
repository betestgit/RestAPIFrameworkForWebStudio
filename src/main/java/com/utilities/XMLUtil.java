package com.utilities;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.codec.binary.Base64;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XMLUtil {

	

	public static void setValue(String inputFile, String rootLocation, String xpath1)
			throws SAXException, IOException, ParserConfigurationException, XPathExpressionException,
			TransformerFactoryConfigurationError, TransformerException {

		Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(inputFile));
		// locate the node(s)
		XPath xpath = XPathFactory.newInstance().newXPath();
		NodeList nodes = (NodeList) xpath.evaluate(xpath1, doc, XPathConstants.NODESET);

		// 3- Make the change on the selected nodes
		for (int idx = 0; idx < nodes.getLength(); idx++) {
			Node value = nodes.item(idx).getAttributes().getNamedItem("value");
			// String val = value.getNodeValue();
			value.setNodeValue(rootLocation);
		}
		// save the result
		Transformer xformer = TransformerFactory.newInstance().newTransformer();
		xformer.transform(new DOMSource(doc), new StreamResult(new File(inputFile)));
	}

	public static void addNodeToXML(String inputFile)
			throws SAXException, IOException, ParserConfigurationException, XPathExpressionException,
			TransformerFactoryConfigurationError, TransformerException {
		Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(inputFile));
		// locate the node(s)
		XPath xpath = XPathFactory.newInstance().newXPath();
		NodeList nodes = (NodeList) xpath.evaluate("//*[contains(@name,'ws.notify.prop.mail.smtps.auth')]", doc,
				XPathConstants.NODESET);

		// Text a = doc.createTextNode("value");
		Element p = doc.createElement("property");
		p.setAttribute("name", "ws.notify.prop.mail.smtp.starttls.enable");
		p.setAttribute("value", "true");

		nodes.item(0).getParentNode().insertBefore(p, nodes.item(0));

		// save the result
		Transformer xformer = TransformerFactory.newInstance().newTransformer();
		xformer.transform(new DOMSource(doc), new StreamResult(new File(inputFile)));

	}

}
