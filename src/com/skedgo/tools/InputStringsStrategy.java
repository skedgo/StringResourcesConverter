package com.skedgo.tools;

import java.io.InputStream;

import com.skedgo.tools.model.StringsStructure;

/**
 * InputStringsStrategy defines the input string resource processing algorithm
 * methods.
 *
 */
public interface InputStringsStrategy {

	/**
	 * Transform input source into a generic object StringStructure
	 * 
	 * @param input: platform specific input stream
	 * @return StringsStructure
	 * @throws Exception
	 */
	StringsStructure getInputValues(InputStream input) throws Exception;
}
