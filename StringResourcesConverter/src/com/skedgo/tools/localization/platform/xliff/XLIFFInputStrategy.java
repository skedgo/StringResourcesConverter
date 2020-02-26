package com.skedgo.tools.localization.platform.xliff;

import com.skedgo.tools.localization.InputCreatorListener;
import com.skedgo.tools.localization.InputStringsStrategy;
import com.skedgo.tools.localization.model.StringDefinition;
import com.skedgo.tools.localization.model.StringsStructure;
import com.sun.istack.internal.NotNull;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.InputStream;

public class XLIFFInputStrategy implements InputStringsStrategy {

	private static XLIFFInputStrategy instance;

	private XLIFFInputStrategy() {
	}

	public static XLIFFInputStrategy getInstance() {
		if (instance == null) {
			instance = new XLIFFInputStrategy();
		}

		return instance;
	}

	@Override
	public void createInputValues(InputStream input, InputCreatorListener listener) throws Exception {
		StringsStructure output = new StringsStructure();

		SAXParserFactory spf = SAXParserFactory.newInstance();
		spf.setNamespaceAware(true);
		SAXParser saxParser = spf.newSAXParser();
		XMLReader xmlReader = saxParser.getXMLReader();
		xmlReader.setContentHandler(new SAXLocalNameCount(output, listener));
		xmlReader.parse(new InputSource(input));

	}

	class SAXLocalNameCount extends DefaultHandler {

		private StringsStructure structure;
		private InputCreatorListener listener;

		private boolean bSource;
		private boolean bNote;
		private boolean bTarget;
		private boolean bAltTrans;

		private String id;
		private String source;
		private String note;
		private String target;

		public SAXLocalNameCount(@NotNull StringsStructure structure, @NotNull InputCreatorListener listener) {
			this.structure = structure;
			this.structure.setBaseStructure(new StringsStructure());
			this.listener = listener;
		}

		public void startDocument() throws SAXException {

		}

		public void endDocument() throws SAXException {
			listener.didFinishInputCreation(structure);
		}

		public void startElement(String namespaceURI, String localName, String qName, Attributes atts)
				throws SAXException {

			if (qName.equals("file")) {
				structure.setTargetLanguage(atts.getValue("target-language"));
			}

			if (qName.equalsIgnoreCase("trans-unit")) {
				source = null;
				note = null;
				target = null;
				id = atts.getValue("id");
			}

			if (qName.equalsIgnoreCase("source")) {
				bSource = true;
			}
			if (qName.equalsIgnoreCase("note")) {
				bNote = true;
			}
			if (qName.equalsIgnoreCase("target")) {
				bTarget = true;
			}
			
			if (qName.equalsIgnoreCase("alt-trans")) {
				bAltTrans = true;
			}

		}

		@Override
		public void endElement(String uri, String localName, String qName) throws SAXException {

			if (qName.equalsIgnoreCase("trans-unit")) {
				if (note != null) {
					structure.getComments().put(structure.getDefinitions().size(), note);
					structure.getBaseStructure().getComments().put(structure.getDefinitions().size(), note);
				}
				if(target == null  || target.isEmpty()){
					structure.getDefinitions().put(structure.getDefinitions().size(), new StringDefinition(id, source));
					
				}else{
					structure.getDefinitions().put(structure.getDefinitions().size(), new StringDefinition(id, target));
					structure.getBaseStructure().getDefinitions().put(structure.getDefinitions().size(), new StringDefinition(id, source));
				}
			}
			
			if (qName.equalsIgnoreCase("alt-trans")) {
				bAltTrans = false;
			}

		}

		public void characters(char ch[], int start, int length) throws SAXException {

			if (bSource) {
				source = new String(ch, start, length);
				bSource = false;
			}
			if (bNote) {
				note = new String(ch, start, length);
				bNote = false;
			}
			if (bTarget) {
				if(!bAltTrans)	target = new String(ch, start, length);
				bTarget = false;
			}

		}

	}

}
