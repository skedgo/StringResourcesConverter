package com.skedgo.tools.localization.platform.android;

import java.util.Date;
import java.util.Map;
import java.util.Set;

import com.skedgo.tools.localization.OutputStringsStrategy;
import com.skedgo.tools.localization.model.StringDefinition;
import com.skedgo.tools.localization.model.StringsStructure;

public class AndroidOutputStrategy implements OutputStringsStrategy {

	private static AndroidOutputStrategy instance;

	private AndroidOutputStrategy() {
	}

	public static AndroidOutputStrategy getInstance() {
		if (instance == null) {
			instance = new AndroidOutputStrategy();
		}

		return instance;
	}

	@Override
	public String generateOutput(StringsStructure input) {

		StringBuffer buf = new StringBuffer("<?xml version='1.0' encoding='UTF-8'?>\n<resources>\n");

		Map<Integer, StringDefinition> definitions = input.getDefinitions();
		Map<Integer, String> comments = input.getComments();

		Set<Integer> idList = definitions.keySet();

		for (Integer i : idList) {

			StringDefinition definition = definitions.get(i);

			String name = cleanName(definition.getName());
			String value = definition.getValue();

			if (!buf.toString().contains("\t<string name=\"" + name + "\">")) { // avoid
																				// duplicates

				if (comments.containsKey(i)) {
					buf.append("\t<!--" + comments.get(i) + "-->\n");
				}
				buf.append("\t<string name=\"" + name + "\">" + cleanValue(value) + "</string>\n");
			}

		}

		buf.append("\n</resources>\n")
				.append("<!-- This is an auto generated file (" + new Date(System.currentTimeMillis()) + ") -->");

		return buf.toString();
	}

	@Override
	public StringsStructure preprocessInputNames(StringsStructure input) {

		Map<Integer, StringDefinition> definitions = input.getDefinitions();

		for (int i = 0; i < definitions.size(); i++) {
			StringDefinition definition = definitions.get(i);
			definition.setName(cleanName(definition.getName()));
		}

		return input;
	}

	protected String basicClean(String string) {
		// TODO proper %d$@ handling
		return string.replaceAll("&", "&amp;").replaceAll("%1\\$@", "PERCAT").replaceAll("%2\\$@", "PERCAT")
				.replaceAll("%3\\$@", "PERCAT").replaceAll("%4\\$@", "PERCAT")
				.replaceAll("%@", "PERCAT").replaceAll("%ld", "PERCAT");
	}

	protected String cleanName(String name) {

		name = basicClean(name);

		// these are mainly iOS strings rules to adapt.
		name = name.replace(" ", "_").replace("\"", "").replace(".", "_DOT").replace("!", "_EXCLAM").replace("%s", "nps")
				.replace("?", "_QUESTION").replace("\'", "_APOST").replace("’", "_APOST").replace("/", "_SLASH").replace(",", "_COMA")
				.replace("(", "_START_PARENT").replace(")", "_END_PARENT").replace("{", "_START_QBRAQUET")
				.replace("}", "_END_QBRAQUET").replace("&amp;", "_AMPERSAND").replace("-", "_MINUS")
				.replace("<", "_LESST").replace(">", "_MORET").replace("@", "_AT").replace("=", "_EQUAL")
				.replace("%", "_PERC").replace("₂", "_2").replace("PERCAT", "_pattern").replace(":", "_2POINTS")
				.replace("\\n", "_").replace("…", "_DOT_DOT_DOT").replace("*", "ASTERISK");

		if (Character.isDigit(name.charAt(0))) {
			name = "_" + name;
		}

		name = name.toLowerCase();

		return name;

	}

	protected String cleanValue(String value) {

		// add escape char
		String cleanedValue = createAndroidPatterns(basicClean(value).replace("'", "\\'"));

		// add quotes if needed
		if (cleanedValue.startsWith(" ") || cleanedValue.endsWith(" ")) {
			cleanedValue = "\"" + cleanedValue + "\"";
		}

		return cleanedValue;
	}

	protected String createAndroidPatterns(String string) {
		int i = 1;
		while (string.contains("PERCAT")) {
			string = string.replaceFirst("PERCAT", "%" + i++ + "\\$s");
		}
		return string;
	}

}
