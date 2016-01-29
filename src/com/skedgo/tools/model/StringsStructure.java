package com.skedgo.tools.model;

import java.util.HashMap;
import java.util.Map;

public class StringsStructure {
	
	// String name - value
	private Map<Integer,StringDefinition> definitions;
	
	// Comment position - value
	private Map<Integer,String> comments;
	
	public StringsStructure(){
		definitions = new HashMap<Integer,StringDefinition>();
		comments = new HashMap<Integer, String>();
	}
	
	public Map<Integer,StringDefinition> getDefinitions() {
		return definitions;
	}
	public void setDefinitions(Map<Integer,StringDefinition> definitions) {
		this.definitions = definitions;
	}
	public Map<Integer,String> getComments() {
		return comments;
	}
	public void setComments(Map<Integer,String> comments) {
		this.comments = comments;
	}		
	

}
