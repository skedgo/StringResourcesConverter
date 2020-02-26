package com.skedgo.tools.localization;

import java.io.InputStream;

import com.sun.istack.internal.NotNull;

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
	 * @throws Exception
	 */
	void createInputValues(@NotNull InputStream input, @NotNull InputCreatorListener listener) throws Exception;
}
