package com.skedgo.tools;

import com.skedgo.tools.model.StringsStructure;

public interface OutputStrategy {

	String generateOutput(StringsStructure input);

	/**
	 * Transform string names in output format string names. 
	 * Example iOS "My string name" to android format "my_string_name". 
	 * 
	 * @param input
	 */
	StringsStructure preprocessInputNames(StringsStructure input);

}
