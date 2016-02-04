package com.skedgo.tools;

import com.skedgo.tools.model.StringsStructure;

/**
 * OutputStrategy defines the output string resource processing algorithm
 * methods.
 *
 */
public interface OutputStringsStrategy {

	/**
	 * Transform the generic object StringStructure into the platform specific
	 * string resource
	 * 
	 * @param input
	 * @return Platform specific string definitions
	 */
	String generateOutput(StringsStructure input);

	/**
	 * Transform string names in output format string names. Example: iOS
	 * "My string name" to android format "my_string_name".
	 * 
	 * @param StringsStructure
	 *            input
	 */
	StringsStructure preprocessInputNames(StringsStructure input);

}
