package com.skedgo.tools.model;

import java.util.HashMap;
import java.util.Map;

/**
 * StringStructure: data structure to define in a generic way string definitions
 * and comments. Each string definition has an associated position for final
 * string resource creation purposes. In the same spirit, comments has a
 * position set as well. Comments position should be defined before than the
 * string definition line. For example: 
 * // string comment 1 
 * "string name 1" = "string value 1"; 
 * // string comment 2
 * "string name 2" = "string value 2";
 * "string comment 1" will have the same position as "string name 1" definition.
 * Anyway, "position" will be created in InputStringStrategy implementation and
 * used in OutputStringStrategy.
 */
public class StringsStructure {

	// String position - StringDefinition
	private Map<Integer, StringDefinition> definitions;

	// Comment position - value
	private Map<Integer, String> comments;

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

}
