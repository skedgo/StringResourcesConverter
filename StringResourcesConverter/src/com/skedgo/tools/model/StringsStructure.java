package com.skedgo.tools.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * StringStructure: data structure to define in a generic way string definitions
 * and comments. Each string definition has an associated position for final
 * string resource creation purposes. In the same spirit, comments has a
 * position set as well. Comments position should be defined before than the
 * string definition line. For example: // string comment 1 "string name 1" =
 * "string value 1"; // string comment 2 "string name 2" = "string value 2";
 * "string comment 1" will have the same position as "string name 1" definition.
 * Anyway, "position" will be created in InputStringStrategy implementation and
 * used in OutputStringStrategy.
 */
public class StringsStructure {

	// String position - StringDefinition
	private Map<Integer, StringDefinition> definitions;
	private Map<String, String> baseValues;

	// Comment position - value
	private Map<Integer, String> comments;

	// Target Language: some resources defines the language in its structure
	private String targetLanguage;

	// Source structure: reference to the base strings structure, for example EN
	// (optional)
	private StringsStructure baseStructure;

	public StringsStructure() {
		definitions = new HashMap<Integer, StringDefinition>();
		comments = new HashMap<Integer, String>();
	}

	public Map<Integer, StringDefinition> getDefinitions() {
		return definitions;
	}

	public void setDefinitions(Map<Integer, StringDefinition> definitions) {
		this.definitions = definitions;
	}

	public Map<Integer, String> getComments() {
		return comments;
	}

	public void setComments(Map<Integer, String> comments) {
		this.comments = comments;
	}

	public String getTargetLanguage() {
		return targetLanguage;
	}

	public void setTargetLanguage(String targetLanguage) {
		this.targetLanguage = targetLanguage;
	}

	public StringsStructure getBaseStructure() {
		return baseStructure;
	}

	public void setBaseStructure(StringsStructure baseStructure) {
		this.baseStructure = baseStructure;

		baseValues = new HashMap<>();

		// init Map
		Set<Integer> idList = definitions.keySet();
		for (Integer i : idList) {
			StringDefinition definition = definitions.get(i);
			baseValues.put(definition.getName(), "");
		}

		Set<Integer> baseIdList = baseStructure.getDefinitions().keySet();
		for (Integer i : baseIdList) {
			StringDefinition definition = baseStructure.getDefinitions().get(i);
			if (baseValues.containsKey(definition.getName())) {
				baseValues.put(definition.getName(), definition.getValue());
			}

		}

	}

	/**
	 * Having base localization info, we can get the base string value
	 * 
	 * @param name
	 * @return
	 */
	public String getLocalizationSource(String name) {
		if (baseValues == null)
			return null;
		
		return baseValues.get(name);
	}
}
