package com.skedgo.tools.platform.java;

import com.skedgo.tools.OutputStringsStrategy;
import com.skedgo.tools.model.StringDefinition;
import com.skedgo.tools.model.StringsStructure;

import java.util.Date;
import java.util.Map;
import java.util.Set;

public class JavaPropertiesOutputStrategy implements OutputStringsStrategy {

	private static JavaPropertiesOutputStrategy instance;

	private JavaPropertiesOutputStrategy() {
	}

	public static JavaPropertiesOutputStrategy getInstance() {
		if (instance == null) {
			instance = new JavaPropertiesOutputStrategy();
		}

		return instance;
	}

	@Override
	public String generateOutput(StringsStructure input) {

		StringBuffer buf = new StringBuffer();

		Map<Integer, StringDefinition> definitions = input.getDefinitions();
		Map<Integer, String> comments = input.getComments();

		Set<Integer> idList = definitions.keySet();

		for (Integer i : idList) {

			StringDefinition definition = definitions.get(i);

			String name = cleanName(definition.getName());
			String value = definition.getValue();

			if (comments.containsKey(i)) {
				buf.append("#" + comments.get(i).replace("\n", "\n#") + "\n");
			}
			buf.append("" + name + " = " + cleanValue(value) + "\n");

		}

		buf.append("# This is an auto generated file (" + new Date(System.currentTimeMillis()) + ")");

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
		return string.replaceAll("%1\\$@", "PERCAT").replaceAll("%2\\$@", "PERCAT")
				.replaceAll("%@", "PERCAT").replaceAll("%ld", "PERCAT");
	}

	protected String cleanName(String name) {
		// Only spaces for dots
		return basicClean(name.replace(" ", ".").replace("=", "_eq").replace(":", "_col")
				.replace(">","_g").replace("<","_l").replace("%", "_pct").replace("!", "_n").replace("'", ""));
	}

	protected String cleanValue(String value) {
		return createJavaPropertiesPatterns(basicClean(value.replace("'", "''")));
	}

	protected String createJavaPropertiesPatterns(String string) {
		int i = 0;
		while (string.contains("PERCAT")) {
			string = string.replaceFirst("PERCAT", "{" + i++ + "}");
		}
		return string;
	}
}
