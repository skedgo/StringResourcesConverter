package com.skedgo.tools;

import com.skedgo.tools.model.StringsStructure;

public interface InputStringsStrategy {

	/**
	 * Decompose input source 
	 * @param input
	 * @return StringsStructure
	 * @throws Exception 
	 */
	StringsStructure getInputValues(String input) throws Exception; 
}
