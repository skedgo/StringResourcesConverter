package com.skedgo.tools.localization.platform.js;

import com.google.gson.JsonObject;
import com.skedgo.tools.localization.OutputStringsStrategy;
import com.skedgo.tools.localization.model.StringDefinition;
import com.skedgo.tools.localization.model.StringsStructure;

import java.util.*;

public class PolyglotJsPropertiesOutputStrategy implements OutputStringsStrategy {

	private static PolyglotJsPropertiesOutputStrategy instance;

	private PolyglotJsPropertiesOutputStrategy() {
	}

	public static PolyglotJsPropertiesOutputStrategy getInstance() {
		if (instance == null) {
			instance = new PolyglotJsPropertiesOutputStrategy();
		}

		return instance;
	}

	@Override
	public String generateOutput(StringsStructure input) {

		JsonObject outputJson = new JsonObject();

		Map<Integer, StringDefinition> definitions = input.getDefinitions();
//		Map<Integer, String> comments = input.getComments();

		Set<Integer> idSet = definitions.keySet();
		// To preserve translations order.
		// Seems to be sure to just iterate with an int index from 1 to definitions.size(), which is more efficient.
		List<Integer> idList = new ArrayList<>(idSet);
		Collections.sort(idList);
		for (Integer i : idList) {

			StringDefinition definition = definitions.get(i);

			String name = cleanName(definition.getName());
			String value = definition.getValue();

//			if (comments.containsKey(i)) {
//				buf.append("#" + comments.get(i).replace("\n", "\n#") + "\n");
//			}

			outputJson.addProperty(name, cleanValue(value));
		}

//		buf.append("# This is an auto generated file (" + new Date(System.currentTimeMillis()) + ")");

		return outputJson.toString();
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
		// Replace not escaped spaces for dots (spaces not preceded by \).
		String scaped = name.replaceAll("(?<!\\\\) ", ".").replace("=", "_eq").replace(":", "_col")
				.replace(">", "_g").replace("<", "_l").replace("%@", "X").replace("%", "X").replace("!", "_n").replace("'", "");
		if (Character.isDigit(scaped.charAt(0)))
			scaped = "_" + scaped;
		return basicClean(scaped);
	}

	protected String cleanValue(String value) {
		return createJavaPropertiesPatterns(basicClean(value.replace("'", "''")));
	}

	protected String createJavaPropertiesPatterns(String string) {
		int i = 0;
		while (string.contains("PERCAT")) {
			string = string.replaceFirst("PERCAT", "%{" + i++ + "}");
		}
		return string;
	}

	public static void setInstance(PolyglotJsPropertiesOutputStrategy instance) {
		PolyglotJsPropertiesOutputStrategy.instance = instance;
	}
}
