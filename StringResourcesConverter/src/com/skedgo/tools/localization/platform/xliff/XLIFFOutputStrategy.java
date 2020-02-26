package com.skedgo.tools.localization.platform.xliff;

import com.skedgo.tools.localization.OutputStringsStrategy;
import com.skedgo.tools.localization.model.StringDefinition;
import com.skedgo.tools.localization.model.StringsStructure;

import java.util.Date;
import java.util.Map;
import java.util.Set;

public class XLIFFOutputStrategy implements OutputStringsStrategy {

	private static XLIFFOutputStrategy instance;

	private XLIFFOutputStrategy() {
	}

	public static XLIFFOutputStrategy getInstance() {
		if (instance == null) {
			instance = new XLIFFOutputStrategy();
		}

		return instance;
	}

	@Override
	public String generateOutput(StringsStructure input) {

		StringsStructure baseLocalization = input.getBaseStructure();

		String srcLang = "";
		String targetLang = input.getTargetLanguage();

		if (baseLocalization != null) {
			srcLang = baseLocalization.getTargetLanguage();
		}

		StringBuffer buf = new StringBuffer("<?xml version='1.0' encoding='utf-8'?>\n"
				+ "<xliff xmlns=\"urn:oasis:names:tc:xliff:document:1.2\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" version=\"1.2\" xsi:schemaLocation=\"urn:oasis:names:tc:xliff:document:1.2 http://docs.oasis-open.org/xliff/v1.2/os/xliff-core-1.2-strict.xsd\">\n");

		buf.append("<file datatype=\"plaintext\" original=\"input strategy\" source-language=\"" + srcLang
				+ "\" target-language=\"" + targetLang + "\">\n");

		buf.append("\t<body>\n");

		Map<Integer, StringDefinition> definitions = input.getDefinitions();
		Map<Integer, String> comments = input.getComments();

		Set<Integer> idList = definitions.keySet();

		for (Integer i : idList) {

			StringDefinition definition = definitions.get(i);

			String nameId = definition.getName();
			String target = definition.getValue();
			String source = input.getLocalizationSource(definition.getName());

			buf.append("\t\t<trans-unit id=\"" + nameId + "\">\n");

			if (source != null) {
				buf.append("\t\t\t<source>" + source + "</source>\n");
			}
			buf.append("\t\t\t<target>" + target + "</target>\n");

			if (comments.containsKey(i)) {
				buf.append("\t\t\t<note>" + comments.get(i) + "</note>\n");
			}

			buf.append("\t\t</trans-unit>\n");

		}

		buf.append("\t</body>\n").append("\n</file>\n").append("\n</xliff>\n")
				.append("<!-- This is an auto generated file (" + new Date(System.currentTimeMillis()) + ") -->");

		return buf.toString();
	}

	@Override
	public StringsStructure preprocessInputNames(StringsStructure input) {

		return input;
	}

}
