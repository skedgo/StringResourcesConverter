package com.skedgo.tools;

import java.io.InputStream;

import com.skedgo.tools.model.StringsStructure;

public interface InputStringsStrategy {

	/**
	 * Decompose input source 
	 * @param input
	 * @return StringsStructure
	 * @throws Exception 
	 */
	StringsStructure getInputValues(InputStream input) throws Exception; 
}
