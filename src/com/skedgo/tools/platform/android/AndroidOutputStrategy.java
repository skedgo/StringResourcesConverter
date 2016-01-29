package com.skedgo.tools.platform.android;

import java.util.Map;
import java.util.Set;

import com.skedgo.tools.OutputStrategy;
import com.skedgo.tools.model.StringDefinition;
import com.skedgo.tools.model.StringsStructure;

public class AndroidOutputStrategy implements OutputStrategy {

	@Override
	public String generateOutput(StringsStructure input) {

		StringBuffer buf = new StringBuffer("<?xml version='1.0' encoding='UTF-8'?>\n<resources>\n");

		Map<Integer, StringDefinition> definitions = input.getDefinitions();
		Map<Integer, String> comments = input.getComments();
		
		Set<Integer> idList = definitions.keySet();

		for (Integer i: idList) {

			StringDefinition definition = definitions.get(i);

			String name = cleanName(definition.getName());
			String value = definition.getValue();			
			
			if(!buf.toString().contains("\t<string name=\"" + name + "\">")){ // avoid duplicates				
				
				if (comments.containsKey(i)) {
					buf.append("\t<!--" + comments.get(i) + "-->\n");
				}
				buf.append("\t<string name=\"" + name + "\">" + cleanValue(value) + "</string>\n");
			}		

		}

		buf.append("\n</resources>\n").append("<!-- This is an auto generated file -->");

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
		return string.replaceAll("&", "&amp;").replaceAll("%1\\$@", "PERCAT").replaceAll("%2\\$@", "PERCAT")
				.replaceAll("%@", "PERCAT").replaceAll("%ld", "PERCAT");
	}

	protected String cleanName(String name) {

		name = basicClean(name);

		name = name.replace(" ", "_").replace(".", "_DOT").replace("!", "_EXCLAM").replace("%s", "nps")
				.replace("?", "_QUESTION").replace("\'", "_APOST").replace("/", "_SLASH").replace(",", "_COMA")
				.replace("(", "_START_PARENT").replace(")", "_END_PARENT").replace("{", "_START_QBRAQUET")
				.replace("}", "_END_QBRAQUET").replace("&amp;", "_AMPERSAND").replace("-", "_MINUS")
				.replace("<", "_LESST").replace(">", "_MORET").replace("@", "_AT").replace("=", "_EQUAL")
				.replace("%", "_PERC").replace("₂", "_2").replace("PERCAT", "_pattern");

		if (Character.isDigit(name.charAt(0))) {
			name = "_" + name;
		}

		name = name.toLowerCase();

		return name;

	}

	protected String cleanValue(String value) {
		String cleanedValue = createAndroidPatterns(basicClean(value).replace("'", "\\'"));

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
