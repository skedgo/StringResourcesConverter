package com.skedgo.tools.localization.platform.android;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.skedgo.tools.localization.InputCreatorListener;
import com.skedgo.tools.localization.InputStringsStrategy;
import com.skedgo.tools.localization.model.StringDefinition;
import com.skedgo.tools.localization.model.StringsStructure;

public class AndroidInputStrategy implements InputStringsStrategy {

	private static AndroidInputStrategy instance;

	private AndroidInputStrategy() {
	}

	public static AndroidInputStrategy getInstance() {
		if (instance == null) {
			instance = new AndroidInputStrategy();
		}

		return instance;
	}

	@Override
	public void createInputValues(InputStream input, InputCreatorListener listener) throws Exception {
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
				output.getComments().put(output.getDefinitions().size(), element.getTextContent());
			}
		}

		listener.didFinishInputCreation(output);
	}

	protected String cleanValue(String value) {
		// remove android escape char
		return value.replace("\\'", "'");
	}

}
