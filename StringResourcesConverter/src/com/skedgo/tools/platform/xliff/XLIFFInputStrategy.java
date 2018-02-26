package com.skedgo.tools.platform.xliff;

import java.io.InputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import com.skedgo.tools.InputCreatorListener;
import com.skedgo.tools.InputStringsStrategy;
import com.skedgo.tools.model.StringDefinition;
import com.skedgo.tools.model.StringsStructure;
import com.sun.istack.internal.NotNull;

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

	
	public void createInputValues(InputStream input, InputCreatorListener listener, String fileSourceType) throws Exception {
		StringsStructure output = new StringsStructure();

		SAXParserFactory spf = SAXParserFactory.newInstance();
		spf.setNamespaceAware(true);
		SAXParser saxParser = spf.newSAXParser();
		XMLReader xmlReader = saxParser.getXMLReader();
		xmlReader.setContentHandler(new SAXLocalNameCount(output, listener, fileSourceType));
		xmlReader.parse(new InputSource(input));

	}
	
	@Override
	public void createInputValues(InputStream input, InputCreatorListener listener) throws Exception {
		createInputValues(input, listener, null);
	}
	

	class SAXLocalNameCount extends DefaultHandler {

		private StringsStructure structure;
		private InputCreatorListener listener;

		private boolean bSource;
		private boolean bNote;
		private boolean bTarget;
		private boolean bAltTrans;
		
		private boolean skipSource;

		private String source;
		private String note;
		private String target;
		
		private String file;

		public SAXLocalNameCount(@NotNull StringsStructure structure, @NotNull InputCreatorListener listener, String fileSourceType) {
			this.structure = structure;
			this.structure.setBaseStructure(new StringsStructure());
			this.listener = listener;
			this.file = fileSourceType;
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
				skipSource = !(file == null || atts.getValue("original").endsWith(file));
			}

			if (qName.equalsIgnoreCase("trans-unit")) {
				source = null;
				note = null;
				target = null;
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

			if (!skipSource && qName.equalsIgnoreCase("trans-unit")) {
				if (note != null) {
					structure.getComments().put(structure.getDefinitions().size(), note);
					structure.getBaseStructure().getComments().put(structure.getDefinitions().size(), note);
				}
				if(target == null  || target.isEmpty()){
					structure.getDefinitions().put(structure.getDefinitions().size(), new StringDefinition(source, source));
					
				}else{
					structure.getDefinitions().put(structure.getDefinitions().size(), new StringDefinition(source, target));
					structure.getBaseStructure().getDefinitions().put(structure.getDefinitions().size(), new StringDefinition(source, source));
				}
			}
			

			bSource = false;
			bNote = false;
			bTarget = false;	
			bAltTrans = false;
			
			
		}

		public void characters(char ch[], int start, int length) throws SAXException {

			String characters = new String(ch, start, length);
			
			if (bSource) {
				if(source == null) source = "";
				source += characters;
				
			}
			if (bNote) {
				if(note == null) note = "";
				note += characters;
				
			}
			if (bTarget) {
				
				if(!bAltTrans)	{
					if(target == null) target = "";
					else target += " ";
					
					target += characters.trim();	
				}				
			}

		}

	}

}
