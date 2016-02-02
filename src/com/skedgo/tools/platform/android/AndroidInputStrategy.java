package com.skedgo.tools.platform.android;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.skedgo.tools.InputStringsStrategy;
import com.skedgo.tools.model.StringDefinition;
import com.skedgo.tools.model.StringsStructure;

public class AndroidInputStrategy implements InputStringsStrategy {

	@Override
	public StringsStructure getInputValues(InputStream input) throws Exception {
		StringsStructure output = new StringsStructure();

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(input);
		
		Node root = doc.getDocumentElement();

		NodeList nodeList = root.getChildNodes();

		for (int i = 0; i < nodeList.getLength(); i++) {

			Node node = nodeList.item(i);

			if (node.getNodeType() == Node.ELEMENT_NODE) {

				Element element = (Element) node;

				output.getDefinitions().put(output.getDefinitions().size(),
						new StringDefinition(element.getAttribute("name"), cleanValue(element.getTextContent())));
				
			} else if (node.getNodeType() == Node.COMMENT_NODE) {
				Comment element = (Comment) node;
				output.getComments().put(output.getDefinitions().size(),  element.getTextContent());
			}
		}

		return output;
	}
	
	protected String cleanValue(String value) {
		// remove android escape char
		return value.replace("\\'", "'");		
	}

}
